package com.joinbright.newenergy.realtimecalc.service.parser

/**
 * 告警计算器
 * @author 柴诗雨
 */
object AlarmCalculator {

	/**
	 * 计算告警表达式
	 * @param expr	告警表达式
	 * @return 是否在告警范围内
	 */
	def calc(expr: DPExpr): Boolean = {
		val value = AST.evaluate(expr.expr)
		val r1 = expr.opL match {
			case "<" => value > expr.lower
			case "<=" => value >= expr.lower
		}
		val r2 = expr.opR match {
			case ">" => value > expr.upper
			case ">=" => value >= expr.upper
			case "<" => value < expr.upper
			case "<=" => value <= expr.upper
		}
		r1 && r2
	}

	/**
	 * 计算告警表达式（字符串）
	 * @param	expression	告警表达式字符串
	 * @return	是否告警
	 */
	def alarm(expression: String): Boolean = {
		val expr = DPExprParser.parseDPExpr(expression)
		!calc(expr)
	}
}