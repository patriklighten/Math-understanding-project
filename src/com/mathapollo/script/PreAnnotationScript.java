package com.mathapollo.script;

import com.mathapollo.io.ProblemModule;
import com.mathapollo.io.raw.MathExprProblem;
import com.mathapollo.io.raw.MathProblem;
import com.mathapollo.io.raw.ProblemIOReader;
import com.mathapollo.parse.expression.ExprPipeline;
import com.mathapollo.parsing.Pipeline;

public class PreAnnotationScript {

	/*
	 * Run this program for all the problems 
	 * 
	 */
	public static void main(String[] args) throws Exception {

		//Raw Problem Data Loading
		MathProblem mp = ProblemIOReader.getInstance().Read("0001");
					
		//Math Expression Parsing Pre-processing		
		MathExprProblem mep = ExprPipeline.getInstance().LoadProblem(mp);
				
		ProblemModule pm = Pipeline.getInstance().Preprocessed(mep);
		
		
					
	}
}
