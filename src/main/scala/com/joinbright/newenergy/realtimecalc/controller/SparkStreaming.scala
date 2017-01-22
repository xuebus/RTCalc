package com.joinbright.newenergy.realtimecalc.controller

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{ StreamingContext, Seconds }
import com.joinbright.newenergy.realtimecalc.service.KafkaDStreamFactory
import org.apache.spark.streaming.dstream.DStream
import com.joinbright.newenergy.realtimecalc.model.KafkaRecord
import com.joinbright.newenergy.realtimecalc.service.alarm.AlarmSerive
import org.apache.spark.SparkContext

object SparkStreaming {


	def main(args: Array[String]) {
		val conf = new SparkConf
		conf.setMaster("local[*]").setAppName("spark-streaming")
		val sc = new SparkContext(conf)
		val ssc = new StreamingContext(sc, Seconds(5))
		val factory = new KafkaDStreamFactory
		val originalData = factory.create(ssc).map(record => KafkaRecord.create(record.value()))
		originalData.persist()
		
		ssc.start()
		ssc.awaitTermination()
	}
}