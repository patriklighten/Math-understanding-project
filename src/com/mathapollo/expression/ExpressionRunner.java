package com.mathapollo.expression;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import com.mathapollo.equation.codegen.*;

public class ExpressionRunner 
{
  public static void main( String[] args) throws Exception 
  {
	  //String txt  = "A(2,a) and B(3,9)";
	  //String txt  = "Find";
	  
	  //String txt  = "BB(2,a)";
	  //String txt = "ax+y+1=0";
	  //y=3x+2
	  String txt = "(x-2)^2+(y-3)^2=4";
	  //String txt = "1";
	  
      ANTLRInputStream input = new ANTLRInputStream(txt);

      ExpressionLexer lexer = new ExpressionLexer(input);

      CommonTokenStream tokens = new CommonTokenStream(lexer);

      ExpressionParser parser = new ExpressionParser(tokens);
      
      //ParseTree tree = parser.problem();
      parser.addErrorListener(new ExpressionErrorListener());
      parser.addParseListener(new ExpressionParseListener());
      ParseTree tree = parser.math();

      //ParseTree tree = parser.form2();
      //ParseTree tree = parser.expr(); 
            
      //ParseTreeWalker.DEFAULT.walk(extractor, tree);
            
//      PointExpressionVisitor visitor = new PointExpressionVisitor();
//      ExpressionValue ev =  visitor.visit(tree);
      
      LineExpressionVisitor visitor = new LineExpressionVisitor();
      visitor.visit(tree);
      System.out.println("Is Line: " + visitor.IsLine());

      QuadraticExpressionVisitor visitor1 = new QuadraticExpressionVisitor();
      visitor1.visit(tree);
      System.out.println("Is Quadratic: " + visitor1.IsQuadraticForm());
     
      parser.reset();      
//      System.out.println(ev.toString());
            
////  

      //System.out.println(tree.toStringTree(parser)); // print LISP-style tree
     
//      ParseTreePattern p = parser.compileParseTreePattern("<pointform>", ExpressionParser.RULE_pointform);
//      ParseTreeMatch m = p.match(tree);
//            
//      if ( m.succeeded() )
//      {
//    	  List<ParseTree> lst = m.getAll("pointform");    	  
//    	  System.out.println(lst.size());
//      }
     
////      parser.addParseListener(new ExpressionBaseListener());
////      
////      ParseTreeWalker.DEFAULT.walk(new ExpressionBaseListener(), tree);
//      
//      ExpressionParser.ExprContext uu = parser.expr();            
  }
}