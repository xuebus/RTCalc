package com.joinbright.newenergy.realtimecalc.service.parser

import org.junit._
import org.junit.Assert._

object MockExprParser extends ExprParser {}
class ExprParserTest {


	@Test def testParse() {
		val expression = "3+4"
		assertEquals(BinaryOp("+", Number(3), Number(4)), MockExprParser.parse(expression))
	}
}