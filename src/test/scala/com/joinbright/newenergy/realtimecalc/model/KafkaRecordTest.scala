package com.joinbright.newenergy.realtimecalc.model

import org.junit.Assert._
import org.junit.{ Test, Before }
import com.joinbright.newenergy.realtimecalc.util.JSONFileReader

class KafkaRecordTest {

	private var jsonLines: Iterator[String] = null

	@Before
	def setUp() {
		jsonLines = JSONFileReader.readFile("data.json")
	}

	@Test
	def testCreate {
		val record = KafkaRecord.create(jsonLines.next())
		assertEquals("0001001", record.device)
		assertEquals("20170113152317", record.time)
		assertEquals(24.39, record.points("020"), 0)
	}
}