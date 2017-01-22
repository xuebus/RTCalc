package com.joinbright.newenergy.realtimecalc.service.parser

/**
 * 数据处理表达式
 * @author 柴诗雨
 */
case class DPExpr(lower: Double, opL: String, expr: Expr, opR: String, upper: Double) extends Serializable {
}