package com.mathapollo.io.raw;

import java.util.ArrayList;

public class MathExprProblem{
	
	public MathProblem problem;
	public ArrayList<MathExprProblemWord> words;
	
	public MathExprProblem(MathProblem mp)
	{
		problem = mp;
		words =  new ArrayList<MathExprProblemWord>();
		Init();
	}
		
	private void Init()
	{
		int countofchar=problem.problem.length();
		//if(problem.problem.length()>1)
	    for(int i=0;i<countofchar;i++)
	    {
	    	if(problem.problem.charAt(i)=='+'||problem.problem.charAt(i)=='-')
	    	{
	    		if(i<countofchar-1&&i>1)
	    		{
	    			if(problem.problem.charAt(i-1)==' ')
	    			{
	    				problem.problem=problem.problem.substring(0,i-1)+problem.problem.substring(i);
	    			    countofchar--;// when eliminating a character, the length of the string will minus 1 and the pointer's location should also move a unit backward
	    			    i--;
	    			    
	    			}
	    			
	    			if(problem.problem.charAt(i+1)==' '&&i<countofchar-2&&i>=1)
	    			{
	    		         problem.problem=problem.problem.substring(0,i+1)+problem.problem.substring(i+2);
	    				 countofchar--;
	    				 i--;
	    			}
	    				
	    		}
	    			                         
	    	}
	    	
	    	if(problem.problem.charAt(i)=='=')
	    	{
	    		if(i<countofchar-1&&i>1)
	    		{
	    			
	    			if(problem.problem.charAt(i-1)==' ')
	    			{
	    				problem.problem=problem.problem.substring(0,i-1)+problem.problem.substring(i);
	    			    countofchar--;
	    			    i--;
	    			}
	    			
	    			if(problem.problem.charAt(i+1)==' '&&i<countofchar-2&&i>=1)
	    			{
	    		    	problem.problem=problem.problem.substring(0,i+1)+problem.problem.substring(i+2);
	    				 countofchar--;
	    				 i--;
	    			}
	    		}
	    			
	    	}
	    		
	    }
		String[] temps = problem.problem.split("\\s+");
	
		for(int i = 0; i < temps.length; i++)
		{
			words.add(new MathExprProblemWord(i, temps[i]));
		}
	}
	
	@Override 
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		for(MathExprProblemWord mpw : words)
		{
			sb.append(mpw.toString());
			sb.append("\n");
		}
		return sb.toString();
	}
		
	/*
	 * Testing Purpose
	 */
	public boolean ContainMathExpression()
	{
		for(MathExprProblemWord w : words)
		{
			if(w.IsMathExpression) return true;
		}		
		return false;
	}
	
	/*
	 * Testing Purpose
	 */
	public int CountMathExpression()
	{
		int count = 0;
		for(MathExprProblemWord w : words)
		{
			if(w.IsMathExpression) count++;
		}		
		return count;
	}	
	
	/*
	 * Alignment Function with UIUC statistical shallow parser
	 */
	public boolean IsMathPart(String token)
	{
		for(MathExprProblemWord mpw : words)
		{
			if(mpw.Token.equals(token))
			{
				return false;
			}
			else
			{
				if(mpw.IsMathExpression)
				{
					if(mpw.IsPartOfToken(token))
					{
						return true;
					}
				}
			}			
		}
		return false;
	}
}