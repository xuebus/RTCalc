package com.joinbright.newenergy.realtimecalc.service.parser

import org.junit._, Assert._

class DPExprParserTest {

	@Test def testParseAlarmExprUnary() {
		val exp = "3+4<=10"
		assertEquals(DPExpr(Double.MinValue, "<", BinaryOp("+", Number(3), Number(4)), "<=", 10), DPExprParser.parseDPExpr(exp))
	}

	@Test def testParseAlarmExprBinary() {
		val exp = "5<=3+4<=10"
		assertEquals(DPExpr(5, "<=", BinaryOp("+", Number(3), Number(4)), "<=", 10), DPExprParser.parseDPExpr(exp))
	}
}