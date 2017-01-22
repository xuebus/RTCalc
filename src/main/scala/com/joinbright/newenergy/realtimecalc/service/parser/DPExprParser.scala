package com.joinbright.newenergy.realtimecalc.service.parser

import scala.util.parsing.combinator.{ JavaTokenParsers, RegexParsers }

/**
 * 数据处理(Data Processing)规则解析器
 * @author 柴诗雨
 */
object DPExprParser extends ExprParser {

	/**数据处理解析式*/
	private[parser] def dpExpr: Parser[DPExpr] = opt(floatingPointNumber ~ opL) ~ expr ~ opR ~ floatingPointNumber ^^ {
		case None ~ e ~ op ~ b => DPExpr(Double.MinValue, "<", e, op, b.toFloat)
		case Some(lower ~ opL) ~ e ~ opR ~ upper => DPExpr(lower.toDouble, opL, e, opR, upper.toDouble)
	}

	/**
	 * 解析数据处理规则
	 * @param expression	待解析表达式
	 * @return	解析后的数据处理表达式
	 */
	def parseDPExpr(expression: String) =
		parseAll(dpExpr, expression).get
}