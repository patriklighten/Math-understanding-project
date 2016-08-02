// Generated from Expression.g4 by ANTLR 4.4

package com.mathapollo.equation.codegen;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ExpressionParser}.
 */
public interface ExpressionListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ExpressionParser#comp}.
	 * @param ctx the parse tree
	 */
	void enterComp(@NotNull ExpressionParser.CompContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionParser#comp}.
	 * @param ctx the parse tree
	 */
	void exitComp(@NotNull ExpressionParser.CompContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionParser#op0}.
	 * @param ctx the parse tree
	 */
	void enterOp0(@NotNull ExpressionParser.Op0Context ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionParser#op0}.
	 * @param ctx the parse tree
	 */
	void exitOp0(@NotNull ExpressionParser.Op0Context ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionParser#op2}.
	 * @param ctx the parse tree
	 */
	void enterOp2(@NotNull ExpressionParser.Op2Context ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionParser#op2}.
	 * @param ctx the parse tree
	 */
	void exitOp2(@NotNull ExpressionParser.Op2Context ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionParser#op1}.
	 * @param ctx the parse tree
	 */
	void enterOp1(@NotNull ExpressionParser.Op1Context ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionParser#op1}.
	 * @param ctx the parse tree
	 */
	void exitOp1(@NotNull ExpressionParser.Op1Context ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionParser#neg}.
	 * @param ctx the parse tree
	 */
	void enterNeg(@NotNull ExpressionParser.NegContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionParser#neg}.
	 * @param ctx the parse tree
	 */
	void exitNeg(@NotNull ExpressionParser.NegContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionParser#op3}.
	 * @param ctx the parse tree
	 */
	void enterOp3(@NotNull ExpressionParser.Op3Context ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionParser#op3}.
	 * @param ctx the parse tree
	 */
	void exitOp3(@NotNull ExpressionParser.Op3Context ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(@NotNull ExpressionParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(@NotNull ExpressionParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionParser#math}.
	 * @param ctx the parse tree
	 */
	void enterMath(@NotNull ExpressionParser.MathContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionParser#math}.
	 * @param ctx the parse tree
	 */
	void exitMath(@NotNull ExpressionParser.MathContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionParser#form1}.
	 * @param ctx the parse tree
	 */
	void enterForm1(@NotNull ExpressionParser.Form1Context ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionParser#form1}.
	 * @param ctx the parse tree
	 */
	void exitForm1(@NotNull ExpressionParser.Form1Context ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionParser#pointform}.
	 * @param ctx the parse tree
	 */
	void enterPointform(@NotNull ExpressionParser.PointformContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionParser#pointform}.
	 * @param ctx the parse tree
	 */
	void exitPointform(@NotNull ExpressionParser.PointformContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionParser#form0}.
	 * @param ctx the parse tree
	 */
	void enterForm0(@NotNull ExpressionParser.Form0Context ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionParser#form0}.
	 * @param ctx the parse tree
	 */
	void exitForm0(@NotNull ExpressionParser.Form0Context ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionParser#form3}.
	 * @param ctx the parse tree
	 */
	void enterForm3(@NotNull ExpressionParser.Form3Context ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionParser#form3}.
	 * @param ctx the parse tree
	 */
	void exitForm3(@NotNull ExpressionParser.Form3Context ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionParser#form2}.
	 * @param ctx the parse tree
	 */
	void enterForm2(@NotNull ExpressionParser.Form2Context ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionParser#form2}.
	 * @param ctx the parse tree
	 */
	void exitForm2(@NotNull ExpressionParser.Form2Context ctx);
}