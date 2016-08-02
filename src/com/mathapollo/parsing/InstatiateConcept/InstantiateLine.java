package com.mathapollo.parsing.InstatiateConcept;
import java.util.ArrayList;

import com.mathapollo.io.*;
import com.mathapollo.knowledge.concept.*;
import com.mathapollo.parsing.contextual.*;


public class InstantiateLine {

public ArrayList<Circleconcept> CircleInstances;
public ArrayList<Lineconcept>    LineInstances;
public ArrayList<Pointconcept>   PointInstances;
public  ArrayList<Equation>   EquationInstances;
public void instantiate_line(ProblemModule pm)
{
    LineInstances=pm.ConceptInstances.LineInstances;
  for(Lineconcept lineInstant:LineInstances)
  {
	//load point element 
	
	// load slop element 
	
	// load standard form
	
	//y-intercept 
	
	
  }
}



}
