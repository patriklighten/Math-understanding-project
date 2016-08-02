package com.mathapollo.expression;

import com.mathapollo.equation.codegen.ExpressionBaseVisitor;
import com.mathapollo.equation.codegen.ExpressionParser.CompContext;
import com.mathapollo.equation.codegen.ExpressionParser.Op1Context;
import com.mathapollo.equation.codegen.ExpressionParser.Op3Context;

public class LineExpressionVisitor extends ExpressionBaseVisitor<ExpressionValue>{

	private boolean containQuadraticTerm = false;
	private boolean containCompOp = false;
	private boolean containOp1 = false; 
		
	public boolean IsLine()
	{
		if(!containQuadraticTerm && containCompOp && containOp1)
		{
			return true;
		}
		
		// new code to judge if is line equation 
     
		return false;
	}
			
	@Override
	public ExpressionValue visitOp3(Op3Context ctx) {
		containQuadraticTerm = true;
		return super.visitOp3(ctx);
	}

	@Override
	public ExpressionValue visitComp(CompContext ctx) {
		containCompOp = true;
		return super.visitComp(ctx);
	}

	@Override
	public ExpressionValue visitOp1(Op1Context ctx) {
		containOp1 = true;
		return super.visitOp1(ctx);
	}
}
