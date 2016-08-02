package com.mathapollo.knowledge.concept;

import java.awt.List;
import java.time.temporal.ValueRange;
import java.util.ArrayList;

import org.junit.validator.PublicClassValidator;

import com.mathapollo.io.ConceptInfo;
import com.mathapollo.io.WordFeatureInfo;

public class Lineconcept 
{
	public ConceptInfo Line_ConceptInfo;
	public Slope slope= new Slope();
	public Value y_intercept= new Value();
	public ArrayList<Pointconcept> points=new ArrayList<Pointconcept>();
    public int Line_Index;
	public Equation standard_from= new Equation();
	public Equation generalform=new Equation(); 
	public Equation equation=new Equation(); 
	public Equation slopeintercept_form=new Equation();
	
	//public WordFeatureInfo mapingfeatureinfo;
	public Lineconcept()
	{
		Line_ConceptInfo= new ConceptInfo();
		slope= new Slope();
		Value y_intercept= new Value();
		points=new ArrayList<Pointconcept>();
		standard_from= new Equation();
		generalform=new Equation(); 
		equation=new Equation(); 
		slopeintercept_form=new Equation();
		
	}
	
	@Override
	public String toString()
	{
		return "Line TODO";
	}  
}
