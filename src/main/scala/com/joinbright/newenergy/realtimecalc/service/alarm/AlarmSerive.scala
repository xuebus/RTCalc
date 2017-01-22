package com.joinbright.newenergy.realtimecalc.service.alarm

import com.joinbright.newenergy.realtimecalc.service.parser.PointParser
import com.joinbright.newenergy.realtimecalc.model.BinaryAlarmMap
import java.util.Objects
import com.joinbright.newenergy.realtimecalc.model.UnaryDPMap

/**
 * 告警服务对象
 * @author	柴诗雨
 */
object AlarmSerive {

	/**
	 * 获取双测点告警表达式映射
	 * @param batchCreateExpr	产生告警表达式列表的函数
	 * @return 双测点告警表达式映射
	 */
	def getBinaryAlarmMap(batchCreateExpr: => Seq[String]) = {
		val exprs = batchCreateExpr
		Objects.requireNonNull(exprs, "Expression Seq must not be null!")
		BinaryAlarmMap.getInstance(exprs)
	}
	
	/**
	 * 获取单测点告警表达式映射
	 * @param batchCreateExpr	产生告警表达式列表的函数
	 * @return	单测点告警表达式映射
	 */
	def getUnaryAlarmMap(batchCreateExpr: => Seq[String]) = {
		val exprs = batchCreateExpr
		Objects.requireNonNull(exprs, "Expression Seq must not be null!")
		UnaryDPMap.getInstance(exprs)
	}
	
	
}