package com.joinbright.newenergy.realtimecalc.service.parser

import java.util.Objects

/**
 * 表达式计算对象
 * @author 柴诗雨
 */
object Calc {

	/**要使用的表达式解析器*/
	private var parser: ExprParser = null;

	/**
	 * 设定要使用的表达式解析器
	 * @param parser	要使用的表达式解析器
	 */
	def setParser(parser: ExprParser) = this.parser = parser

	/**
	 * 计算表达式。传入字符串类型表达式，计算并返回结果
	 * @param	expression	字符串类型表达式
	 * @return 表达式计算结果
	 */
	def calc(expression: String): Double = {
		Objects.requireNonNull(parser, "parser must not be null")
		Objects.requireNonNull(expression, "expression must not be null")
		AST.evaluate(parser.parse(expression))
	}

	/**
	 * 计算表达式。传入Expr类型表达式，计算并返回结果
	 * @param expression	Expr类型表达式
	 * @return 表达式计算结果
	 */
	def calc(expression: Expr): Double = {
		Objects.requireNonNull(expression, "expression must not be null")
		AST.evaluate(expression)
	}
}