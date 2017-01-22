package com.joinbright.newenergy.realtimecalc.service.parser

private[parser] sealed class Expr extends Serializable {}
private[parser] case class Number(value: Double) extends Expr
private[parser] case class Variable(name: String) extends Expr
private[parser] case class BinaryOp(operator: String, left: Expr, right: Expr) extends Expr