package com.mathapollo.knowledge.concept;

import com.mathapollo.io.ConceptInfo;
import com.mathapollo.io.WordFeatureInfo;

public class Pointconcept {

	public ConceptInfo Point_ConceptInfo;
	public Value X;
	public Value Y;
	
	public String Label;
	
	public WordFeatureInfo mapingfeatureinfo;// load the words that represent
												// its value
	public int Point_Index;

	public Pointconcept() {
		Point_ConceptInfo = new ConceptInfo();
		X = new Value();
		Y = new Value();
		mapingfeatureinfo = new WordFeatureInfo();
	}

	public Pointconcept(Value x, Value y) {
		X = x;
		Y = y;

	}
	
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append("Point ");		
		if(Label != null)
		{
			sb.append(Label);
		}
		sb.append(": ");
		sb.append(X.toString());
		sb.append(",");
		sb.append(Y.toString());	
		return sb.toString();
	}
}
