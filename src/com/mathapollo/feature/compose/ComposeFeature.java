package com.mathapollo.feature.compose;
import org.junit.experimental.theories.Theories;

import com.mathapollo.feature.conceptpairs;
import com.mathapollo.parsing.EventandRelation.*;
import LBJ2.IR.IfStatement;

public class ComposeFeature {
	//<A,B>
	public conceptpairs Composepair= new conceptpairs();// composed concept pairs 
	// logistic confidence 
	public int Composescore;
	
	
	public int if_samesentence;
	public int ifsplitByVerb;// contains, pass, has,on 
	public int if_BisofA;// if the element is just before the of+ higher concept 
	public int if_element_isof_anotherconcept; //if the element is of another concept 
	public int if_BbeforeOf;// "there is a line, the slope of it is 3, y-intercept of it is 2"
	
	public double distance_inchunker;
	public int If_sametypeconceptA_between;
	public int if_sametypeConceptB_between;
	public int relativelocation; // -1 and 1, 1 means A is before B otherwise -1
	public int if_A_is_beforeOf;
	public ComposeFeature()
	{
		Composepair= new conceptpairs();
	}
		
	@Override
	public String toString()
	{
//		int wordindex1 = Composepair.concept1.global_index;
//		int wordindex2 = Composepair.concept2.global_index;
		String featurestring = "[" + 
						distance_inchunker + ";" + 
						if_BbeforeOf + ".;" + 
						if_A_is_beforeOf+".;" + 
						if_BisofA + ".;" + 
						if_element_isof_anotherconcept+".;" +
						if_samesentence + ".;" + 
						If_sametypeconceptA_between + ".;" + 
						if_sametypeConceptB_between + ".;" + 
						ifsplitByVerb + ".]";
//		StringBuffer sb = new StringBuffer();
//		sb.append(wordindex1).append(',').append(wordindex2).append(',');
//		sb.append(featurestring);
//		return sb.toString();
		return featurestring;
	}
}
