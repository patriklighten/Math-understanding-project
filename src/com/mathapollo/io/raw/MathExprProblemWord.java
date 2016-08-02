package com.mathapollo.io.raw;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.antlr.v4.parse.GrammarTreeVisitor.tokenSpec_return;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.validator.PublicClassValidator;

import com.mathapollo.equation.codegen.ExpressionLexer;
import com.mathapollo.equation.codegen.ExpressionParser;
import com.mathapollo.expression.ExpressionValue;
import com.mathapollo.expression.LineExpressionVisitor;
import com.mathapollo.expression.PointExpressionVisitor;
import com.mathapollo.expression.QuadraticExpressionVisitor;
import com.mathapollo.knowledge.concept.Circleconcept;
import com.mathapollo.knowledge.concept.Lineconcept;
import com.mathapollo.knowledge.concept.Pointconcept;

public class MathExprProblemWord {

	public String Token;                       	                                        
	public String tag;                                                                       
	public int Id;                   
	public int wordinfo_index; // to compatible with the shallow chunker's word feature info 
	public int if_mapped;// if the mathword has been mapped to a concrete concept 
	public String concept_code="";
    private ExpressionParser problemParser;	                                             
	public boolean IsMathExpression;
	public boolean HaveParseSyntaxError;
	
	public Object MathInstance;
		
	public MathExprProblemWord(int id, String token)                                                     
	{
		Id = id;                                         
		Token = token;                                                               
		IsMathExpression = false;
		HaveParseSyntaxError = false;
		concept_code="";
		ExpressionCheck();
	}
	
	private void ExpressionCheck()
	{		
		if(!HaveSpecialCharacters()) return;

		ANTLRInputStream input = new ANTLRInputStream(Token);
	    ExpressionLexer lexer = new ExpressionLexer(input);
	    CommonTokenStream tokens = new CommonTokenStream(lexer);
	    problemParser = new ExpressionParser(tokens);
	    problemParser.addErrorListener(new MathExpressionErrorListener(this));
	    problemParser.math(); 
	    if(!HaveParseSyntaxError)
	    {
	    	problemParser.reset();
	    	IsMathExpression = true;
		    //Further deterministic recognition
	    	if(!Token.contains("="))
	    	{
	        CheckPointPattern();
	    	}
	    	if(!Token.contains("^"))
	    	{
	    	 CheckLinePattern();
	    	}
	    	else 
	    	CheckQuadraticPattern();
	    }
	    else if((Token.toLowerCase().contains("x")||Token.toLowerCase().contains("y"))&&Token.toLowerCase().contains("="))
	    {
	    	problemParser.reset();
	    	if(!Token.contains("^"))
	    	{
	    		CheckLinePattern();
	    	}
	    	else 
	    	CheckQuadraticPattern();
	    	
	    }
	}
	
	private void CheckPointPattern()
	{
		ParseTree tree = problemParser.pointform();                              
		if(!HaveParseSyntaxError&& !Token.contains("="))                    
		{
			PointExpressionVisitor visitor = new PointExpressionVisitor();      
		    ExpressionValue ev =  visitor.visit(tree);
		    Pointconcept pc = new Pointconcept();
			
			ArrayList<ExpressionValue> lst = (ArrayList<ExpressionValue>)ev.value;			
			assert lst != null;
			
			ExpressionValue xCord = lst.get(0);
			ExpressionValue yCord = lst.get(1);
			ExpressionValue pLabel = null;
            
			
			
			if(lst.size() == 3)
			{
				pLabel = lst.get(2);
			}
		
			if(xCord.isDouble())
			{
				pc.X.value = xCord.asDouble();	
				
			}
			else
			{
				pc.X.unknown_variable = xCord.asString();
				
			}
				
			if(yCord.isDouble())
			{
				pc.Y.value = yCord.asDouble();
				
			}
			else
			{
				pc.Y.unknown_variable = yCord.asString();
				
			}
			
			if(pLabel != null)
			{
				pc.Label = pLabel.asString();
			}
			else
			{
				pc.Label=Token;
			     
			}
		     
			MathInstance = pc;			
			concept_code="point_value";
			tag=pc.Label;
		}
		problemParser.reset();
	}

	/*
	 * Line Pattern Match
	 */
	private void CheckLinePattern()
	{
		ParseTree tree = problemParser.math();
		LineExpressionVisitor visitor = new LineExpressionVisitor();
	    visitor.visit(tree); 
	    String Line_token=Token;
        
        if(Line_token.length()>1)
        {
        	if(if_stop_word(Line_token.charAt(0))==1)
           Line_token=Line_token.substring(1); //substring(begining index)
           
        }
        if(Line_token.length()>1)
        {
        	if(if_stop_word(Line_token.charAt(Line_token.length()-1))==1)
        	Line_token=Line_token.substring(0, Line_token.length()-1);
        }
	    
        Token=Line_token;
	    if(visitor.IsLine())
	    {
	    	//TODO Line Recognition	    	            
	    	IsMathExpression= true;                                                       
        	Lineconcept lc = new Lineconcept();	    	                                                        
	    	MathInstance = lc;
	    	
	    	concept_code="line_value";
	    	tag=Token;
	    }	   
	    
	    // new code to judge if it is circle: 
	    else  if((Token.toLowerCase().contains("x")||Token.toLowerCase().contains("y"))&&Token.toLowerCase().contains("=")&&!Token.toLowerCase().contains("^"))
	    {
	    	IsMathExpression=true;
	    	Lineconcept lc = new Lineconcept();	    	
	    	MathInstance = lc;
	    	
	    	
	    	
	    	concept_code="line_value";
	    	tag=Token;
	    	
	    	
	    }

problemParser.reset();
	}
	
	/*
	 * Circle or Ellipse Pattern Match 
	 */
	private void CheckQuadraticPattern()
	{
		ParseTree tree = problemParser.math();
		QuadraticExpressionVisitor visitor = new QuadraticExpressionVisitor();
	     visitor.visit(tree); 
       
	     String Circle_Token= Token;
        
        if(Circle_Token.length()>1)
        {
        	if(if_stop_word(Circle_Token.charAt(0))==1)
        	Circle_Token=Circle_Token.substring(1); //substring(begining index)
           
        }
        if(Circle_Token.length()>1)
        {
        	if(if_stop_word(Circle_Token.charAt(Circle_Token.length()-1))==1)
        	Circle_Token=Circle_Token.substring(0, Circle_Token.length()-1);
        }
	    
        Token=Circle_Token;
	    if(visitor.IsQuadraticForm())
	    {
	    	//TODO Circle Recognition
	    	IsMathExpression=true;
	    	Circleconcept cc = new Circleconcept();	
	    	MathInstance = cc;
	    	concept_code="circle_value";
	    	tag=Token;
	    }	  
	    else  if(Token.toLowerCase().contains("x")&&Token.toLowerCase().contains("y")&&Token.toLowerCase().contains("=")&&Token.toLowerCase().contains("^"))
	    {
	    	//TODO Circle Recognition
	    	IsMathExpression=true;
	    	Circleconcept cc = new Circleconcept();	
	    	MathInstance = cc;
	    	concept_code="circle_value";
	    	
	    	tag=Token;
	    }
		problemParser.reset();
	}
	
	/*
	 * Check with other parsers
	 * 
	 */
	public boolean IsPartOfToken(String input)
	{
		char[] array = Token.toCharArray();
		for(char c : array)
		{
			String temp = Character.toString(c);			
			if(input.equals(temp))
			{
				return true;
			}
		}
		return false;
	}
	
	/*
	 * Use regular expression to check. 
	 * 
	 */
	private boolean HaveSpecialCharacters()
	{
		Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(Token);
		boolean b = m.find();
		return b;
	}
	
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		
		String first = String.format("Id:" + Id + ", Word:" + Token + ", MathExpr:" + IsMathExpression);
		
		if(!IsMathExpression)
		{
			return first;
		}
	
		if(MathInstance != null)
		{
			sb.append(first).append(", ").append(MathInstance.toString());	
		}
		else
		{
			sb.append(first).append(", ").append("TODO");	
		}
		
		return sb.toString();
	}	
	
	public int if_stop_word(char temp_word)
	{
		if(temp_word==','||temp_word=='.'||temp_word==';'||temp_word=='?')
		{
		      return 1;                                                                                                  
		}
		
		return 0;
	}
}
