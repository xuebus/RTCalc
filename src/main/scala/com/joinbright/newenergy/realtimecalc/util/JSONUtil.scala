package com.joinbright.newenergy.realtimecalc.util

import scala.util.parsing.json.JSON
import org.apache.spark.streaming.dstream.DStream

object JSONUtil {
	def originalToPair(originalDStream: DStream[String]) =
		originalDStream.flatMap(getValueMap)

	def getValueMap(record: String): Map[String, String] = {
		val jValue = (JSON.parseFull(record) match { case Some(map: Map[_, _]) => map; case _ => }).asInstanceOf[Map[String, AnyRef]]
		val value = (jValue("value") match { case v: List[_] => v }).asInstanceOf[List[String]]
		value.map(x => { val p = x.split("="); (p(0), p(1)) }).toMap
	}

	def getJsonMap(jsonText: String) =
		(JSON.parseFull(jsonText) match { case Some(map: Map[_, _]) => map; case _ => }).asInstanceOf[Map[String, AnyRef]]

}