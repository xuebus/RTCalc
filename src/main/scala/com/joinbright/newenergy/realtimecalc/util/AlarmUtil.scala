package com.joinbright.newenergy.realtimecalc.util

object AlarmUtil {
	def setBinaryVars(exp: String, p: String, v: Double, p1: String, v1: Double) =
		exp.replace(p, v.toString()).replace(p1, v1.toString())
		
	def setUnaryVar(exp:String,p:String,v:Double) = 
		exp.replace(p, v.toString())

}

