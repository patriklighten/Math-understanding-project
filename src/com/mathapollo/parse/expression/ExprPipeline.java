package com.mathapollo.parse.expression;

import com.mathapollo.io.raw.MathExprProblem;
import com.mathapollo.io.raw.MathProblem;

public class ExprPipeline {
	                                                                                       
	private static ExprPipeline instance = null;
                                                                                     
	private MathExprProblem _currentProblem;
	
	protected ExprPipeline(){

	}

	public static ExprPipeline getInstance(){
		if(instance == null)
		{
			instance = new ExprPipeline();
		}
		return instance;
	}
	
	public MathExprProblem LoadProblem(MathProblem mp)
	{		
		_currentProblem = new MathExprProblem(mp);	
		return _currentProblem;
	}
	
	public boolean IsPartOfMath(String token)
	{
		return _currentProblem.IsMathPart(token);
	}	
}
