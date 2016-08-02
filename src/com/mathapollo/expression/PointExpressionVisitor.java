package com.mathapollo.expression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.mathapollo.equation.codegen.ExpressionBaseVisitor;
import com.mathapollo.equation.codegen.ExpressionParser.ExprContext;
import com.mathapollo.equation.codegen.ExpressionParser.Form0Context;
import com.mathapollo.equation.codegen.ExpressionParser.PointformContext;

public class PointExpressionVisitor extends ExpressionBaseVisitor<ExpressionValue> {

	// used to compare floating point numbers
	public static final double SMALL_VALUE = 0.00000000001;

	@Override
	public ExpressionValue visitPointform(PointformContext ctx) {

		ExpressionValue label = null; // label
		
		if(ctx.expr() != null)
		{
		   label = this.visit(ctx.expr());			
		}
		
		ExpressionValue xCoord = this.visit(ctx.form0(0)); // x-coordinate
		ExpressionValue yCoord = this.visit(ctx.form0(1)); // y-coordinate

		ArrayList<ExpressionValue> pairOrTriple = new ArrayList<ExpressionValue>();
		pairOrTriple.add(xCoord);
		pairOrTriple.add(yCoord);
		if(label != null)
		{
			pairOrTriple.add(label);			
		}

		return new ExpressionValue(pairOrTriple);
	}

	@Override
	public ExpressionValue visitExpr(ExprContext ctx) {

		String tt = ctx.getText();
		return new ExpressionValue(tt);
	}

	@Override
	public ExpressionValue visitForm0(Form0Context ctx) {
		String str = ctx.getText();
		try {
			double d = Double.parseDouble(str);
			return new ExpressionValue(d);			
		} catch (NumberFormatException nfe) {
			return new ExpressionValue(str);
		}
	}
}
