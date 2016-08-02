package com.mathapollo.expression;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Before;
import org.junit.Test;

import com.mathapollo.equation.codegen.ExpressionLexer;
import com.mathapollo.equation.codegen.ExpressionParser;

public class ExpressionTest {

	/*
	 * True Positive: 
	 * 
	 * Point1: AB(2,2)
	 * Point2: B(2,y)
	 * 
	 * Line1: x+y+1=0 
	 * Line2: a*x+y+1=0 
	 * Line3: y=x+1
	 * Line4: y=3*x+1
	 * 
	 * Circle1: x^2+y^2=1 
	 * 
	 * Ellipse1: x^2+y^2+x=1
	 * 
	 * False Negative:
	 * 
	 * testFN1: and
	 * testFN2: A(2,3) and (5,4)
	 * 
	 * 
	 * Not support:
	 * 
	 * Line2_TODO: ax+y+1=0
	 * Line4_TODO: y=3x+1
	 * 
	 */
		
	@Test
	public void Point1() {
		
		String txt = "AB(2,2)";
		
		ANTLRInputStream input = new ANTLRInputStream(txt);

		ExpressionLexer lexer = new ExpressionLexer(input);

		CommonTokenStream tokens = new CommonTokenStream(lexer);

		ExpressionParser parser = new ExpressionParser(tokens);
		ParseTree tree = parser.math(); // begin parsing at rule 'r'
		System.out.println(tree.toStringTree(parser)); // print LISP-style tree
	}
	
	@Test
	public void Point2() {
		
		String txt = "B(2,y)";
		
		ANTLRInputStream input = new ANTLRInputStream(txt);

		ExpressionLexer lexer = new ExpressionLexer(input);

		CommonTokenStream tokens = new CommonTokenStream(lexer);

		ExpressionParser parser = new ExpressionParser(tokens);
		ParseTree tree = parser.math(); // begin parsing at rule 'r'
		System.out.println(tree.toStringTree(parser)); // print LISP-style tree
	}
	
	@Test
	public void Line1() {
		
		String txt = "x+y+1=0";
		
		ANTLRInputStream input = new ANTLRInputStream(txt);

		ExpressionLexer lexer = new ExpressionLexer(input);

		CommonTokenStream tokens = new CommonTokenStream(lexer);

		ExpressionParser parser = new ExpressionParser(tokens);
		ParseTree tree = parser.math(); // begin parsing at rule 'r'
		System.out.println(tree.toStringTree(parser)); // print LISP-style tree
	}
	
	@Test
	public void Line2() {
		
		String txt = "a*x+y+1=0";
		
		ANTLRInputStream input = new ANTLRInputStream(txt);

		ExpressionLexer lexer = new ExpressionLexer(input);

		CommonTokenStream tokens = new CommonTokenStream(lexer);

		ExpressionParser parser = new ExpressionParser(tokens);
		ParseTree tree = parser.math(); // begin parsing at rule 'r'
		System.out.println(tree.toStringTree(parser)); // print LISP-style tree
	}
	
	@Test
	public void Line3() {
		
		String txt = "y=x+1";
		
		ANTLRInputStream input = new ANTLRInputStream(txt);

		ExpressionLexer lexer = new ExpressionLexer(input);

		CommonTokenStream tokens = new CommonTokenStream(lexer);

		ExpressionParser parser = new ExpressionParser(tokens);
		ParseTree tree = parser.math(); // begin parsing at rule 'r'
		System.out.println(tree.toStringTree(parser)); // print LISP-style tree
	}
	
	@Test
	public void Line4() {
		
		String txt = "y=3x+1"; //"y=3*x+1"; // "y=3x+1"
		
		ANTLRInputStream input = new ANTLRInputStream(txt);

		ExpressionLexer lexer = new ExpressionLexer(input);

		CommonTokenStream tokens = new CommonTokenStream(lexer);

		ExpressionParser parser = new ExpressionParser(tokens);
		ParseTree tree = parser.math(); // begin parsing at rule 'r'
		System.out.println(tree.toStringTree(parser)); // print LISP-style tree
	}
	
	@Test
	public void Circle1() {
		
		String txt = "x^2+y^2=1";
		
		ANTLRInputStream input = new ANTLRInputStream(txt);

		ExpressionLexer lexer = new ExpressionLexer(input);

		CommonTokenStream tokens = new CommonTokenStream(lexer);

		ExpressionParser parser = new ExpressionParser(tokens);
		ParseTree tree = parser.math(); // begin parsing at rule 'r'
		System.out.println(tree.toStringTree(parser)); // print LISP-style tree
	}
	
	@Test
	public void Ellipse1() {
		
		String txt = "x^2+y^2+x=1";
		
		ANTLRInputStream input = new ANTLRInputStream(txt);

		ExpressionLexer lexer = new ExpressionLexer(input);

		CommonTokenStream tokens = new CommonTokenStream(lexer);

		ExpressionParser parser = new ExpressionParser(tokens);
		ParseTree tree = parser.math(); // begin parsing at rule 'r'
		System.out.println(tree.toStringTree(parser)); // print LISP-style tree
	}
	
	@Test
	public void TestFN1() {
		
		String txt = "and";
		
		ANTLRInputStream input = new ANTLRInputStream(txt);

		ExpressionLexer lexer = new ExpressionLexer(input);

		CommonTokenStream tokens = new CommonTokenStream(lexer);

		ExpressionParser parser = new ExpressionParser(tokens);
		ParseTree tree = parser.math(); // begin parsing at rule 'r'
		System.out.println(tree.toStringTree(parser)); // print LISP-style tree
	}
	
	@Test
	public void TestFN2() {
		
		String txt = "an";
		
		ANTLRInputStream input = new ANTLRInputStream(txt);

		ExpressionLexer lexer = new ExpressionLexer(input);

		CommonTokenStream tokens = new CommonTokenStream(lexer);

		ExpressionParser parser = new ExpressionParser(tokens);

		
		ParseTree tree = parser.math(); // begin parsing at rule 'r'
		System.out.println(tree.toStringTree(parser)); // print LISP-style tree
	}
	
	public void TestFN3() {
		
		String txt = ",";
		
		ANTLRInputStream input = new ANTLRInputStream(txt);

		ExpressionLexer lexer = new ExpressionLexer(input);

		CommonTokenStream tokens = new CommonTokenStream(lexer);

		ExpressionParser parser = new ExpressionParser(tokens);

		
		ParseTree tree = parser.math(); // begin parsing at rule 'r'
		System.out.println(tree.toStringTree(parser)); // print LISP-style tree
	}
	
	
	
}
