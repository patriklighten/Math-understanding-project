package com.mathapollo.expression;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.TerminalNode;

public class ExpressionParseListener implements ParseTreeListener{

	@Override
	public void enterEveryRule(ParserRuleContext arg0) {
		//System.out.println("enterEveryRule");		
		
	}

	@Override
	public void exitEveryRule(ParserRuleContext arg0) {		
		//System.out.println("exitEveryRule");
	}

	@Override
	public void visitErrorNode(ErrorNode arg0) {
		//System.out.println("visitErrorNode");
	}

	@Override
	public void visitTerminal(TerminalNode arg0) {
		//System.out.println("visitTerminal");
	}	
}
