package com.mathapollo.test;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import java.util.ArrayList;

import com.mathapollo.io.raw.MathExprProblem;
import com.mathapollo.io.raw.MathProblem;
import com.mathapollo.io.raw.ProblemIOReader;
import com.mathapollo.parse.expression.ExprPipeline;

public class ExprParsingTest {

	@Test
	public void testProblem0001()
	{
		//Find the distance between A(2,0) and B(5,4) ?
		ArrayList<MathProblem> problems = ProblemIOReader.getInstance().GetProblems();
		MathProblem problem = ProblemIOReader.getInstance().Read("0001");	
		MathExprProblem mp = ExprPipeline.getInstance().LoadProblem(problem);
		
		System.out.println(mp.toString());
				
		assertTrue(mp.ContainMathExpression());
		assertTrue(mp.CountMathExpression() == 2); //A(2,0) and B(5,4)
	}

	@Test
	public void testProblem0006()
	{
		//If the equation of a line p in the coordinate plane is y=3x+2, 
		//what is the general form of line q which is a reflection of line p in the x-axis ?
		ArrayList<MathProblem> problems = ProblemIOReader.getInstance().GetProblems();
		MathProblem problem = ProblemIOReader.getInstance().Read("0006");	
		MathExprProblem mp = ExprPipeline.getInstance().LoadProblem(problem);
		System.out.println(mp.toString());
		assertTrue(mp.ContainMathExpression());
		assertTrue(mp.CountMathExpression() == 2); // y=3x+2 and x-axis
	}
	
	@Test
	public void testProblem0014()
	{
		//What is the center of the circle described by the equation (X-2)^2+(Y-3)^2=4 ? What is the radius ?
		ArrayList<MathProblem> problems = ProblemIOReader.getInstance().GetProblems();
		MathProblem problem = ProblemIOReader.getInstance().Read("0014");	
		MathExprProblem mp = ExprPipeline.getInstance().LoadProblem(problem);
		System.out.println(mp.toString());
		assertTrue(mp.ContainMathExpression());
		assertTrue(mp.CountMathExpression() == 1); // (X-2)^2+(Y-3)^2=4
	}
	
	@Test
	public void testProblem0002()
	{
		//There exists two points A(3,4) and B(7,v), the distance between A and B is 5. What is the value of v ? ?
		ArrayList<MathProblem> problems = ProblemIOReader.getInstance().GetProblems();
		MathProblem problem = ProblemIOReader.getInstance().Read("0002");	
		MathExprProblem mp = ExprPipeline.getInstance().LoadProblem(problem);
		System.out.println(mp.toString());
//		assertTrue(mp.ContainMathExpression());
//		assertTrue(mp.CountMathExpression() == 1); // (X-2)^2+(Y-3)^2=4
	}
	
	
	//TODO integrate with the later pipeline test
		
	/*
	@Test
	public void test1()
	{
		//txt = A(2,3)
		
		//Matcher m = m.parser(txt);
		//m.succeed
		
		ConceptInfo ci = new ConceptInfo();
		
		ci.concept_code = "pointinstance";
		ci.NNtoken_index = 2; //0 word index, not chunk index
	}
	
	@Test
	public void test2()
	{
		//txt: x+y+1=0
		
		//Matcher m = m.parser(txt);
		//m.succeed
		
		ConceptInfo ci = new ConceptInfo();
		
		ci.concept_code = "lineEquation";
		ci.NNtoken_index = 2; //0 word index, not chunk index
	}
	
	@Test
	public void test3()
	{
		//txt: x^2+y^2+1=0
		
		//Matcher m = m.parser(txt);
		//m.succeed
		
		ConceptInfo ci = new ConceptInfo();
		
		ci.concept_code = "circleEquation";
		ci.NNtoken_index = 2; //0 word index, not chunk index
	}
*/
}
