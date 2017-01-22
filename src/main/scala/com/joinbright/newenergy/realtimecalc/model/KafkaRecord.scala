package com.joinbright.newenergy.realtimecalc.model

import com.joinbright.newenergy.realtimecalc.util.JSONUtil
import scala.io.Source

class KafkaRecord private (val device: String, val time: String, val points: Map[String, Double]) extends Serializable {

	override def toString() = "Device: " + device + ", Time: " + time + ", Points: " + points
}

object KafkaRecord {

	def create(jsonRecord: String): KafkaRecord = {
		var device = ""
		var time = ""
		var points = Map("" -> 0.0)
		try {
			val recordMap = JSONUtil.getJsonMap(jsonRecord);
			device = recordMap("DEVICE").toString()
			time = recordMap("DATETIME").toString()
			points = recordMap("POINTS").asInstanceOf[Map[String, Double]]
		} catch {
			case _: Exception => // TODO need to write log file here
		}
		new KafkaRecord(device, time, points)
	}
}