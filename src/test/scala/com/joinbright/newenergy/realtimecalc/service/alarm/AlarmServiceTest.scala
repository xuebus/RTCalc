package com.joinbright.newenergy.realtimecalc.service.alarm

import org.junit._, Assert._
import org.easymock.EasyMock._
import scala.collection.mutable.HashMap

class AlarmServiceTest {

	private var expressions: Array[String] = null
	private var hashMap: HashMap[String, Map[String, String]] = null

	@Before def setUp() {
		expressions = Array("3.5<=050+033<=4.5",
			"3.5<=050+012<=4.5",
			"3.5<=050+026<=4.5",
			"3.5<=033+012<=4.5",
			"3.5<=033+026<=4.5",
			"3.5<=012+033<=4.5")

		hashMap = new HashMap[String, Map[String, String]]
		val map50 = Map("033" -> "3.5<=050+033<=4.5", "012" -> "3.5<=050+012<=4.5", "026" -> "3.5<=050+026<=4.5")
		val map33 = Map("012" -> "3.5<=033+012<=4.5", "026" -> "3.5<=033+026<=4.5")
		val map12 = Map("033" -> "3.5<=012+033<=4.5")
		hashMap("050") = map50
		hashMap("033") = map33
		hashMap("012") = map12
	}
	
}