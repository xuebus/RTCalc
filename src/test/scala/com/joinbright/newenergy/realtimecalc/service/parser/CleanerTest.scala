package com.joinbright.newenergy.realtimecalc.service.parser

import org.junit._, Assert._

class CleanerTest {

	@Test def testCalc_Bianry_Succeed = {
		val expr = DPExpr(1.5, "<", Number(3.0), "<", 4.5)
		val result = Cleaner.calc(expr)
		assertEquals((0, 3.0), result)
	}

	@Test def testCalc_Binary_Up = {
		val expr = DPExpr(1.5, "<", Number(5.0), "<", 4.5)
		val result = Cleaner.calc(expr)
		assertEquals((1, 4.5), result)
	}

	@Test def testCalc_Binary_Down = {
		val expr = DPExpr(1.5, "<", Number(1.0), "<", 4.5)
		val result = Cleaner.calc(expr)
		assertEquals((-1, 1.5), result)
	}

	@Test def testCalc_Unary_Succeed = {
		val expr = DPExpr(Double.MinValue, "<", Number(4.5), ">=", 4.5)
		val result = Cleaner.calc(expr)
		assertEquals((0, 4.5), result)
	}

	@Test def testCalc_Unary_Down = {
		val expr = DPExpr(Double.MinValue, "<", Number(4.0), ">", 4.5)
		val result = Cleaner.calc(expr)
		assertEquals((-1, 4.5), result)
	}

}