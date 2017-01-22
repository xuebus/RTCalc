package com.joinbright.newenergy.realtimecalc.service

import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.dstream.DStream

trait DStreamFactory {
	type record
	def create(ssc: StreamingContext): DStream[record]
}