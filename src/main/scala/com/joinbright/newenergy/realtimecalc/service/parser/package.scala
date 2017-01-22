package com.joinbright.newenergy.realtimecalc.service

package object parser {
	
	/**左操作符*/
	val opR = "(<=)|(>=)|(>)|(<)".r
	/**右操作符*/
	val opL = "(<=)|(<)".r
}