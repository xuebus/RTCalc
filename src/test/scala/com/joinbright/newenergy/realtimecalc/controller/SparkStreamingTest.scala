package com.joinbright.newenergy.realtimecalc.controller

import org.apache.spark.SparkConf
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.Seconds
import com.joinbright.newenergy.realtimecalc.service.KafkaDStreamFactory
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.SparkContext
import org.junit._, Assert._
import org.apache.spark.SparkConf
import com.joinbright.newenergy.realtimecalc.service.alarm.AlarmSerive
import org.apache.spark.broadcast.Broadcast
import com.joinbright.newenergy.realtimecalc.service.{ KafkaDStreamFactory, DStreamFactory }
import com.joinbright.newenergy.realtimecalc.model.KafkaRecord
import java.util.concurrent.TimeUnit
import com.joinbright.newenergy.realtimecalc.util.AlarmUtil
import com.joinbright.newenergy.realtimecalc.service.parser.AlarmCalculator
import com.joinbright.newenergy.realtimecalc.service.cleaning.CleaningService
import com.joinbright.newenergy.realtimecalc.service.parser.Cleaner

class SparkStreamingTest {

	private var binaryAlarmExprs: Seq[String] = null
	private var unaryAlarmExprs: Seq[String] = null
	private var cleaningExprs: Seq[String] = null
	private var dStreamFactor: KafkaDStreamFactory = null

	@Before def setUp() {
		/**实际的告警格式应该为 device#point */
		binaryAlarmExprs = Seq("-3<=033+035<=3",
			"-1.03<=033/035<=1.03")
		unaryAlarmExprs = Seq("-1000000<=033<=60")
		/**实际的清洗格式应该为 device#point */
		cleaningExprs = Seq("-1000000<=033<=60", "7<=050<=8","600<=038<=650")
	}

	@Test def testAlarm() {
		val conf = new SparkConf
		conf.setMaster("local[*]").setAppName("spark-streaming")
		val sc = new SparkContext(conf)
		val binaryAlarmMap = sc.broadcast(AlarmSerive.getBinaryAlarmMap(binaryAlarmExprs))
		val unaryAlarmMap = sc.broadcast(AlarmSerive.getUnaryAlarmMap(unaryAlarmExprs))
		val cleaningMap = sc.broadcast(CleaningService.getDataCleaningMap(cleaningExprs))
		val ssc = new StreamingContext(sc, Seconds(2))
		dStreamFactor = new KafkaDStreamFactory

		val data = dStreamFactor.create(ssc).map(record => KafkaRecord.create(record.value()))

		/**双点告警*/
		// data1=((p,d,t),v)
		val data1 = data.flatMap(record => {
			val device = record.device
			val time = record.time
			val points = record.points
			points.map(p => ((p._1, device, time), p._2))
		})

		// data2=((p1,d,t),(p,v,Seq[exp]))
		val data2 = data1.flatMap(m => {
			val expMap = binaryAlarmMap.value.get(m._1._1) match {
				case None => Map[String, Seq[String]]()
				case Some(map: Map[String, Seq[String]]) => map
			}
			expMap.map(em => ((em._1, m._1._2, m._1._3), (m._1._1, m._2, em._2)))
		})
		data2.cache(); data1.cache()
		//data3=((p1,d,t),((p,v,Seq[exp]),v1))
		val data3 = data2.join(data1)
		//data4=((p,v),(p1,v1),d,t,Seq[exp])
		val data4 = data3.map(x => ((x._2._1._1, x._2._1._2), (x._1._1, x._2._2), x._1._2, x._1._3, x._2._1._3))
		//data5=((p,v),(p1,v1),d,t,exp)
		val data5 = data4 flatMap {
			case (pv, pv1, d, t, exprs) =>
				exprs.map(expr => (pv, pv1, d, t, expr))
		}
		val bianryAlarmRDD = data5.filter(x => {
			val exp = AlarmUtil.setBinaryVars(x._5, x._1._1, x._1._2, x._2._1, x._2._2)
			AlarmCalculator.alarm(exp)
		})

		/**单点告警*/
		val data6 = data1 map {
			case ((p, d, t), v) => {
				val expr = unaryAlarmMap.value.get(p) match {
					case None => ""
					case Some(e: String) => e
				}
				((p, v), d, t, expr)
			}
		} filter { x => !x._4.eq("") }

		val unaryAlarmRDD = data6.filter {
			case ((p, v), d, t, expr) => {
				val exp = AlarmUtil.setUnaryVar(expr, p, v)
				AlarmCalculator.alarm(exp)
			}
		}

		/**数据清洗*/
		val data7 = data1 map {
			case ((p, d, t), v) => {
				val expr = cleaningMap.value.get(p) match {
					case None => ""
					case Some(e: String) => e
				}
				((p, v), d, t, expr)
			}
		} 
		
		val cleanedRDD = data7 map{
			case ((p, v), d, t, expr) => {
				if(expr.eq("")) ((p, v), d, t, expr)
				else {
					val exp = AlarmUtil.setUnaryVar(expr, p, v)
					val kv = Cleaner.clean(exp)
					((p, kv._2), d, t, expr)
				}
			}
		}

		/*
		unaryAlarmRDD.print()
		bianryAlarmRDD.print()
		*/
//		data.print()
//		data7.print()
		cleanedRDD.print()

		ssc.start()
		ssc.awaitTermination()
	}

}
