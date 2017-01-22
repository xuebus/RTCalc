package com.joinbright.newenergy.realtimecalc.service.parser

import scala.util.parsing.combinator.{ JavaTokenParsers, RegexParsers }

/**
 * 测点解析器，解析测点专用
 * @author 柴诗雨
 */
object PointParser extends ExprParser {

	private val point = "[0-9]+".r

	private def pointExpr: Parser[DPExpr] =
		opt(floatingPointNumber ~ opL) ~ expr ~ opR ~ floatingPointNumber ^^ {
			case None ~ e ~ op ~ b =>
				DPExpr(Double.MinValue, "<", e, op, b.toFloat)
			case Some(lower ~ opL) ~ e ~ opR ~ upper =>
				DPExpr(lower.toDouble, opL, e, opR, upper.toDouble)
		}

	override def factor: Parser[Expr] = point ^^ (p => Variable(p)) | "(" ~> expr <~ ")"

	/**
	 * 获取表达式中的测点信息
	 * @param	表达式
	 * @return 两个测点
	 */
	def getPoints(expression: String) = {
		val expr = parseAll(pointExpr, expression).get.expr
		expr match {
			case binaryOp: BinaryOp =>
				(binaryOp.left.asInstanceOf[Variable].name, binaryOp.right.asInstanceOf[Variable].name)
			case variable: Variable =>
				(variable.name, "")
			case _ => throw new ClassCastException()
		}
	}
}