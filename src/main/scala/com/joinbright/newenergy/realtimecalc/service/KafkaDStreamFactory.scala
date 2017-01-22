package com.joinbright.newenergy.realtimecalc.service

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.KafkaUtils

import com.joinbright.newenergy.realtimecalc.util.Config

class KafkaDStreamFactory extends DStreamFactory {

	type record = ConsumerRecord[String, String]

	val topics = Config.get("topics").split(",")
	// Kafka
	val kafkaParams = Map[String, Object](
		"bootstrap.servers" -> Config.get("bootstrap.servers"),
		"key.deserializer" -> classOf[StringDeserializer],
		"value.deserializer" -> classOf[StringDeserializer],
		"group.id" -> Config.get("group.id"),
		"auto.offset.reset" -> "latest",
		"enable.auto.commit" -> (false: java.lang.Boolean))

	def create(ssc: StreamingContext) = KafkaUtils.createDirectStream(ssc,
		PreferConsistent,
		Subscribe[String, String](topics, kafkaParams))

}