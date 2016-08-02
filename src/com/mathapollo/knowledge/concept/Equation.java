package com.mathapollo.knowledge.concept;

import com.mathapollo.io.ConceptInfo;
import com.mathapollo.io.WordFeatureInfo;

public class Equation {
	
	//public String Equation_form;  // we regard all the form as equation instance, so the conceptinfo.concept_code has the form's information 
    public ConceptInfo Equation_ConceptInfo;
	public String unknown_variable; //"?" or  "alphabetic" is unknown variable, other wise "known"
	public String equation; //equation string or "unknown"
	public WordFeatureInfo mapingfeatureinfo;
	public int Equation_Index;
	public int if_line_Equation;
	public int if_circle_Equation;
	//public static String valuetype="string";
	public Equation()
	{
		Equation_ConceptInfo= new ConceptInfo();
		mapingfeatureinfo=new WordFeatureInfo();
		unknown_variable=new String();
		equation=new String();
		//Equation_form= new String();
				}
	public Equation(String unknown, String equa)
	{
		unknown_variable=unknown;
		equation= equa;
	
	}
}
