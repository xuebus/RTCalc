package com.joinbright.newenergy.realtimecalc.service.parser

import org.junit._, Assert._

class ASTTest {

	@Test def testEvaluate() {
		val exp = BinaryOp("+", Number(3), Number(4))
		assertEquals(7, AST.evaluate(exp), 0)
	}
}