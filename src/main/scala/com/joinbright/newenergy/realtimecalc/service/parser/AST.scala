package com.joinbright.newenergy.realtimecalc.service.parser

/**
 * 抽象语法树
 * @author 柴诗雨
 */
object AST {
	
	/**
	 * 解析Expr类型的表达式，并且返回计算结果
	 * @param exp	要计算的Expr类型的表达式
	 */
	def evaluate(exp: Expr): Double = {
		exp match {
			case Number(x) => x
			case BinaryOp("+", x1, x2) => (evaluate(x1) + evaluate(x2))
			case BinaryOp("-", x1, x2) => (evaluate(x1) - evaluate(x2))
			case BinaryOp("*", x1, x2) => (evaluate(x1) * evaluate(x2))
			case BinaryOp("/", x1, x2) => (evaluate(x1) / evaluate(x2))
			case _ => throw new IllegalArgumentException("Operator must be one of '+', '-', '*', '/'")
		}
	}
}