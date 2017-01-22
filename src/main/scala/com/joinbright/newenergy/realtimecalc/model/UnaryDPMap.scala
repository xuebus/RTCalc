package com.joinbright.newenergy.realtimecalc.model

import com.joinbright.newenergy.realtimecalc.service.parser.PointParser

/**测点与表达式映射
 * @author 柴诗雨
 */
class UnaryDPMap private (private[model] val dpMap: Map[String, String]) extends Serializable {

	/**
	 * 根据测点获取表达式
	 * @param point	测点号
	 * @return	表达式
	 */
	def get(point: String) =
		dpMap.get(point)
}

object UnaryDPMap {

	/**
	 * 根据一元表达式列表获取实例
	 * @param exprs		一元表达式列表
	 * @return 此类的实例
	 */
	def getInstance(exprs: Seq[String]) = {
		import scala.collection.mutable.HashMap
		val dpMap = new HashMap[String, String]

		def setExpr(expr: String) {
			val point = PointParser.getPoints(expr)._1
			dpMap += point -> expr
		}

		exprs.foreach(setExpr(_))
		new UnaryDPMap(dpMap.toMap)
	}
}