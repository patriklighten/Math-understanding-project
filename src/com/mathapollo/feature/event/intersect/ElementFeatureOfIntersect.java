package com.mathapollo.feature.event.intersect;

import com.mathapollo.feature.conceptpairs;
import com.mathapollo.io.ConceptInfo;

/***
 * the features here is the feature of aligning the event objects,that is to
 * align the two intersect object, line,circle
 ***/
public class ElementFeatureOfIntersect {

	public ConceptInfo intersectinfo = new ConceptInfo();
	/*
	 * the composepairs is refer to the pairs of concepts that will load into
	 * the distance's template for example
	 * distance<conceptpairs.concept1,conceptpairs.concept2>
	 */
	// for example distance<conceptpairs.concept1,conceptpairs.concept2>
	public conceptpairs conceptpairinfo = new conceptpairs();

	// logistic confidence
	public int Composescore;

	// the following feature is for the concept pairs
	public int if_verb_between;
	public int if_samesentence;// if the conceptA, B and the distance concept in
								// the same sentence

	public double distance_inchunker;// distance between conceptA and conceptB
	public int If_sametypeconceptA_between;
	public int if_sametypeConceptB_between;
	public double distancefromconceptAtointersect;// should take relative
													// location into account
	public double distancefromconceptBtointersect;// should take relative
													// location into account
	public int if_anotherIntersectbetween;
    //public int if_And_beforeIntersect;// under the same senence:line A..., and is intersect with ...
	
	public ElementFeatureOfIntersect() {
		intersectinfo = new ConceptInfo();
		conceptpairinfo = new conceptpairs();
	}
	
	@Override
	public String toString()
	{
		String printfeature = "[" + 
				distance_inchunker + ";"+ 
				distancefromconceptAtointersect + ";" + 
				distancefromconceptBtointersect + ";" + 
				if_anotherIntersectbetween + ".;" +  
				if_samesentence + ".;" + 
				If_sametypeconceptA_between + ".;" + 
				if_sametypeConceptB_between + ".;" + 
				if_verb_between + ".]";
		return printfeature;
	}

}