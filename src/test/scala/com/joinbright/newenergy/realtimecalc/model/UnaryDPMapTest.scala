package com.joinbright.newenergy.realtimecalc.model

import org.junit._, Assert._

class UnaryDPMapTest {

	@Test def testGetInstance = {
		val exprs = Seq("3.5<=050<=4.2", "3.3<=033<=5.5")
		val map = UnaryDPMap.getInstance(exprs)
		assertEquals(Map("050" -> "3.5<=050<=4.2", "033" -> "3.3<=033<=5.5"), map.dpMap)
	}
}