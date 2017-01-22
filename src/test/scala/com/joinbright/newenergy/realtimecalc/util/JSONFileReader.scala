package com.joinbright.newenergy.realtimecalc.util

import scala.io.Source

object JSONFileReader {

	private val path = "src/test/resources/"

	private def file(fileName: String) = path + fileName

	def readFile(path: String = file("data.json")) = Source.fromFile(file(path), "UTF-8").getLines
}