package com.joinbright.newenergy.realtimecalc.service.cleaning

import java.util.Objects
import com.joinbright.newenergy.realtimecalc.model.UnaryDPMap

object CleaningService {
	/**
	 * 数据清洗表达式映射
	 * @param batchCreateExpr	产生数据清洗表达式列表的函数
	 * @return	数据清洗表达式映射
	 */
	def getDataCleaningMap(batchCreateExpr: => Seq[String]) = {
		val exprs = batchCreateExpr
		Objects.requireNonNull(exprs, "Expression Seq must not be null!")
		UnaryDPMap.getInstance(exprs)
	}
}