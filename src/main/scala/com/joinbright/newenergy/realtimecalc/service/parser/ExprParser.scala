package com.joinbright.newenergy.realtimecalc.service.parser

import scala.util.parsing.combinator.{ JavaTokenParsers, RegexParsers }

/**
 * 表达式解析器
 * @author 柴诗雨
 */
private[parser] trait ExprParser extends JavaTokenParsers {

	def expr: Parser[Expr] = term ~ opt(("+" | "-") ~ expr) ^^ {
		case t ~ None => t
		case t ~ Some("+" ~ e) => BinaryOp("+", t, e)
		case t ~ Some("-" ~ e) => BinaryOp("-", t, e)
	}

	def term: Parser[Expr] = (factor ~ opt(("*" | "/") ~ factor)) ^^ {
		case a ~ None => a
		case a ~ Some("*" ~ b) => BinaryOp("*", a, b)
		case a ~ Some("/" ~ b) => BinaryOp("/", a, b)
	}

	def factor: Parser[Expr] = floatingPointNumber ^^ (n => Number(n.toFloat)) | "(" ~> expr <~ ")"

	/**
	 * 解析表达式
	 * @param	expression 字符串表达式
	 * @return	Expr表达式
	 */
	def parse(expression: String) =
		parseAll(expr, expression).get
}