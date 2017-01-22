package com.joinbright.newenergy.realtimecalc.model

import scala.collection.mutable.{ HashMap, ListBuffer }
import com.joinbright.newenergy.realtimecalc.service.parser.PointParser
import java.util.Objects

/**
 * 双测点告警信息映射。
 * 内部结构为Map[String,Map[String,Seq[String]]],
 * 外层Map的键为要触发告警的测点p，值为一个内层Map
 * 内层Map的键为与外成Map的键共同构成告警条件的另一个测点p1
 * 内层Map的值为p与p1两个测点共同构成的告警信息的告警表达式列表
 * @author 柴诗雨
 */
class BinaryAlarmMap private (private[model] val alarmMap: Map[String, Map[String, Seq[String]]]) extends Serializable {

	/**
	 * 根据测点获取告警信息映射
	 * @param point	测点
	 * @return 传入的测点的告警信息映射
	 */
	def get(point: String) =
		alarmMap.get(point)
}

object BinaryAlarmMap {
	/**
	 * 根据二元告警表达式表达式列表获取实例
	 * @param exprs		二元报警表达式列表
	 * @return	此类的实例
	 */
	def getInstance(exprs: Seq[String]) = {
		Objects.requireNonNull(exprs, "exprs must be not null!")
		val alarmMap = new HashMap[String, HashMap[String, ListBuffer[String]]]

		def setExpr(expr: String) = {
			val points = PointParser.getPoints(expr)
			alarmMap.get(points._1) match {
				case None => {
					val map = HashMap[String, ListBuffer[String]]()
					val list = ListBuffer[String]()
					list += expr
					map += points._2 -> list
					alarmMap += points._1 -> map
				}
				case Some(map: HashMap[String, ListBuffer[String]]) => {
					map.get(points._2) match {
						case None => {
							val list = ListBuffer[String]()
							list += expr
							map += points._2 -> list
						}
						case Some(list: ListBuffer[String]) => list += expr
					}
				}
			}
		}
		exprs.foreach(setExpr(_))
		val map = alarmMap.map(kv => (kv._1, kv._2.map(mp => (mp._1, mp._2.toSeq)).toMap)).toMap
		new BinaryAlarmMap(map)
	}
}