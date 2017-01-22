package com.joinbright.newenergy.realtimecalc.service.parser

object Cleaner {

	/**
	 * 计算数据清洗表达式
	 * @param expr	数据清洗表达式
	 * @return 是否在范围内与应有的数值的二元组。
	 * 二元组中第一个元素代表待检测测点是否在规定范围内，
	 * 在规定范围内为0,大于规定范围为1,小于规定范围为-1。
	 * 二元组中第二个元素为需要存入数据库中的测点值，
	 * 如果在规定范围内则为原值，如果超出上下限则取上下限的值
	 */
	def calc(expr: DPExpr): (Int, Double) = {
		val value = AST.evaluate(expr.expr)
		var result: (Int, Double) = 0 -> value
		val r1 = expr.opL match {
			case "<" => value > expr.lower
			case "<=" => value >= expr.lower
		}
		if (!r1) result = (-1, expr.lower)

		expr.opR match {
			case ">" => if (value <= expr.upper) result = (-1, expr.upper)
			case ">=" => if (value < expr.upper) result = (-1, expr.upper)
			case "<" => if (value >= expr.upper) result = (1, expr.upper)
			case "<=" => if (value > expr.upper) result = (1, expr.upper)
		}
		result
	}

	/**
	 *  根据数据清洗表达式进行数据清洗
	 * @param expression	数据清洗表达式
	 * @return 是否在范围内与应有的数值的二元组。
	 * 二元组中第一个元素代表待检测测点是否在规定范围内，
	 * 在规定范围内为0,大于规定范围为1,小于规定范围为-1。
	 * 二元组中第二个元素为需要存入数据库中的测点值，
	 * 如果在规定范围内则为原值，如果超出上下限则取上下限的值
	 */
	def clean(expression: String) = {
		val expr = DPExprParser.parseDPExpr(expression)
		calc(expr)
	}

}