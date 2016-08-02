// Generated from Expression.g4 by ANTLR 4.4

package com.mathapollo.equation.codegen;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ExpressionParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ExpressionVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ExpressionParser#comp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComp(@NotNull ExpressionParser.CompContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionParser#op0}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOp0(@NotNull ExpressionParser.Op0Context ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionParser#op2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOp2(@NotNull ExpressionParser.Op2Context ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionParser#op1}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOp1(@NotNull ExpressionParser.Op1Context ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionParser#neg}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNeg(@NotNull ExpressionParser.NegContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionParser#op3}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOp3(@NotNull ExpressionParser.Op3Context ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(@NotNull ExpressionParser.ExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionParser#math}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMath(@NotNull ExpressionParser.MathContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionParser#form1}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForm1(@NotNull ExpressionParser.Form1Context ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionParser#pointform}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPointform(@NotNull ExpressionParser.PointformContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionParser#form0}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForm0(@NotNull ExpressionParser.Form0Context ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionParser#form3}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForm3(@NotNull ExpressionParser.Form3Context ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionParser#form2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForm2(@NotNull ExpressionParser.Form2Context ctx);
}