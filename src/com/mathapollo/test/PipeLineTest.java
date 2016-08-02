package com.mathapollo.test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.mathapollo.io.annotation.AnnotationIOReader;
import com.mathapollo.io.raw.MathProblem;
import com.mathapollo.io.raw.ProblemIOReader;
import com.mathapollo.parsing.Pipeline;

public class PipeLineTest {
	@Before
	public void setUp() throws Exception {
    
	}
	
	/****************  Problem Annotation Test *******************/

	@Test
	public void Annotation_Concept_Read() {
		try {
			AnnotationIOReader.getInstance().LoadConceptAnnotation();			
			AnnotationIOReader.getInstance().PrintConceptAnnotationStatistics();					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void Annotation_Problem_Read(){
		try {
			AnnotationIOReader.getInstance().LoadAnnotatedProblems();			
			AnnotationIOReader.getInstance().PrintProblemAnnotationStatistics();						
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	@Test
	public void Annotation_Feature_Write()
	{
		try {
			AnnotationIOReader.getInstance().LoadConceptAnnotation();			
			//AnnotationIOReader.getInstance().MergeFeatureWithAnnotation();					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	@Test
	public void Annotation_Feature_Read()
	{
		//AnnotationIOReader.getInstance().MergeFeatureWithAnnotation();	
	}
		
	/****************  Raw Problem IO Test *******************/
	
	@Test
	public void Problems_load() {
		ArrayList<MathProblem> problems = ProblemIOReader.getInstance().GetProblems();
		int count = problems.size();
		assertTrue(count == 186);
	}
	
	@Test
	public void Problems_get() {
		Problems_load();
		MathProblem problem = ProblemIOReader.getInstance().Read("0001");
		assertTrue(problem != null);
	}
	
	
}
