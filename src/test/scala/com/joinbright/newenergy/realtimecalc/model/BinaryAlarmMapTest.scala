package com.joinbright.newenergy.realtimecalc.model

import org.junit._, Assert._

class BinaryAlarmMapTest {

	val exprs = Array("3.5<=050+033<=4.5",
		"3.5<=050/033<=4.5",
		"3.5<=050+026<=4.5",
		"3.5<=033+012<=4.5")

	@Test def testSetExpr() {
		import scala.collection.mutable.{ HashMap, ListBuffer }
		val baMap = BinaryAlarmMap.getInstance(exprs)
		val expected = new HashMap[String, HashMap[String, ListBuffer[String]]]()
		val mp050 = new HashMap[String, ListBuffer[String]]()
		val mp033 = new HashMap[String, ListBuffer[String]]()
		val list050_033 = new ListBuffer[String]()
		val list050_026 = new ListBuffer[String]()
		val list033_012 = new ListBuffer[String]()
		list050_033 += "3.5<=050+033<=4.5"
		list050_033 += "3.5<=050/033<=4.5"
		list050_026 += "3.5<=050+026<=4.5"
		list033_012 += "3.5<=033+012<=4.5"
		mp050 += "033" -> list050_033
		mp050 += "026" -> list050_026
		mp033 += "012" -> list033_012
		expected += "050" -> mp050
		expected += "033" -> mp033
		assertEquals(expected, baMap.alarmMap)
	}
}