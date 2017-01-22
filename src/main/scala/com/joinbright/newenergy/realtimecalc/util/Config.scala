package com.joinbright.newenergy.realtimecalc.util

import java.util.Properties

object Config {
	val properties = new Properties();
	val inStream = this.getClass.getClassLoader.getResourceAsStream("config.properties")
	properties.load(inStream)


	def get(key: String) = properties.getProperty(key)
}