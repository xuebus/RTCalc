package com.joinbright.newenergy.realtimecalc.service.parser

import org.junit._, Assert._

class PointParserTest {
	
	@Test def testGetPoints {
		val exp = "1.5<=050+033<=88.88"
		val pair = PointParser.getPoints(exp)
		assertEquals("050", pair._1)
		assertEquals("033", pair._2)
	}
}