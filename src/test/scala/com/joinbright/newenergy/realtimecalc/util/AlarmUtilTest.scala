package com.joinbright.newenergy.realtimecalc.util

import org.junit._, Assert._

class AlarmUtilTest {

	@Test def testSetVariable() {
		val exp = "3.5<=050+033<=4.5"
		val result = AlarmUtil.setBinaryVars(exp, "050", 1.2, "033", 2.1)
		assertEquals("3.5<=1.2+2.1<=4.5", result)
	}
}