package com.joinbright.newenergy.realtimecalc.service

import java.util.Objects

trait ExprManager {
	
	
	private var exprFactory:ExprFactory =null
	
	def setExprFactory(factory:ExprFactory) =
		this.exprFactory = factory
		
	def getExprMap = {
		Objects.requireNonNull(exprFactory, "ExprFactory must not be null")
	}
	
	private[service] def generateExprMap(expressions:Seq[String]) = {
		
	}
}