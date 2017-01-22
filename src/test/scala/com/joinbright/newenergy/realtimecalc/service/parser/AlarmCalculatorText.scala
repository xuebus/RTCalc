package com.joinbright.newenergy.realtimecalc.service.parser

import org.junit._, Assert._

class AlarmCalculatorText {

	@Test def testCalcTrue() {
		val exp = DPExpr(5, "<=", BinaryOp("+", Number(3), Number(4)), "<=", 10)
		assertEquals(true, AlarmCalculator.calc(exp))
	}

	@Test def testCalcFalse() {
		val exp = DPExpr(5, "<=", BinaryOp("+", Number(3), Number(4)), "<=", 6)
		assertEquals(false, AlarmCalculator.calc(exp))
	}

	@Test def testAlarmTrue() {
		val exp = "10<=3+4<=20"
		assertEquals(true, AlarmCalculator.alarm(exp))
	}

	@Test def testAlarmFalse() {
		val exp = "5<3+4<10"
		assertEquals(false, AlarmCalculator.alarm(exp))
	}
	
	@Test def testUnaryAlarmTrue() {
		val exp = "2<5<10"
		assertEquals(false, AlarmCalculator.alarm(exp))
	}
	
	@Test def testUnaryAlarmFalse() {
		val exp = "2<5<3"
		assertEquals(true, AlarmCalculator.alarm(exp))
	}
	
}