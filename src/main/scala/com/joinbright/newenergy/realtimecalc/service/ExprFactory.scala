package com.joinbright.newenergy.realtimecalc.service

trait ExprFactory {
	def batchCreate(): Seq[String];
}