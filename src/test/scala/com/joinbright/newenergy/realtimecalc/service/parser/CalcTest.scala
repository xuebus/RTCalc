package com.joinbright.newenergy.realtimecalc.service.parser

import org.junit._, Assert._

class CalcTest {
	
	Calc.setParser(MockExprParser)

	@Test def testCalcPlus() {
		val expression = "3+4"
		assertEquals(7.0, Calc.calc(expression), 0)
	}

	@Test def testCalcMinus() {
		val expression = "3-4"
		assertEquals(-1.0, Calc.calc(expression), 0)
	}

	@Test def testCalcMultiply() {
		val expression = "3*4"
		assertEquals(12.0, Calc.calc(expression), 0)
	}

	@Test def testCalcDivide() {
		val expression = "3/4"
		assertEquals(0.75, Calc.calc(expression), 0)
	}
}