package com.mathapollo.feature.event.distance;

import java.util.ArrayList;

import com.mathapollo.feature.conceptpairs;
import com.mathapollo.io.ConceptInfo;

import edu.illinois.cs.cogcomp.lbj.coref.learned.eHHeadLastWord;

// compose distance template  
public class composeFeatureofDistance {
	
	public ConceptInfo distanceconceptinfo=new ConceptInfo();
	/* the composepairs is refer to the pairs of concepts that will load into the distance's template 
	for example distance<conceptpairs.concept1,conceptpairs.concept2>
	*/
	//for example distance<conceptpairs.concept1,conceptpairs.concept2>
	public conceptpairs conceptpairinfo= new conceptpairs();
	
	// logistic confidence 
	public int Composescore;
	
	// the following feature is for the concept pairs 
    public int if_verb_between;
	public int if_samesentence;// if the conceptA, B and the distance concept in the same sentence 
	public int ifsplitByTo;// distance from point1 to the line 
	public int ifsplitByAnd;// distance between point1 and point2    
	public int if_they_afterBetween;// distant between point1 and point2 
	public int if_theyBothAfterDistance;// two distance's elements should be mentioned after the distance 
	public double distance_inchunker;// distance between conceptA and conceptB 
	public int If_sametypeconceptA_between;
	public int if_sametypeConceptB_between;
	public double distancefromconceptAtoDistance;// should take relative location into account 
	public double distancefromconceptBtoDistance;// should take relative location into account 
	public int if_anotherDistancebetween; 
   
	public composeFeatureofDistance()
	{
		distanceconceptinfo=new ConceptInfo();
		conceptpairinfo= new conceptpairs();
	}
	
	@Override
	public String toString()
	{
		String printfeature = "[" + 
				distance_inchunker + ";" + 
				distancefromconceptAtoDistance + ";"+ 
				distancefromconceptBtoDistance + ";" + 
				if_anotherDistancebetween + ".;" + 
				if_samesentence + ".;"+ 
				If_sametypeconceptA_between + ".;" + 
				if_sametypeConceptB_between + ".;" + 
				if_they_afterBetween + ".;" + 
				if_theyBothAfterDistance + ".;" + 
				if_verb_between + ".;" + 
				ifsplitByAnd + ".;" +  
				ifsplitByTo + ".]";				
		return printfeature;
	}
		
}
     
  
   