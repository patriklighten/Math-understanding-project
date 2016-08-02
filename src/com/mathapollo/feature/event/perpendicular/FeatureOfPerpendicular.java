package com.mathapollo.feature.event.perpendicular;

import com.mathapollo.feature.conceptpairs;
import com.mathapollo.io.ConceptInfo;

public class FeatureOfPerpendicular {

	public ConceptInfo perpendicularinfo;
	public conceptpairs Linepairinfo;

	public int if_samesentence;// if the conceptA, B and the distance concept in
								// the same sentence
	public int ifsplitByTo;// distance from point1 to the line
	public int ifsplitByAnd;// two perpendicular line A and B
	public double distance_inchunker;// distance between conceptA and conceptB
	public int If_anotherlinebetween;
	public double distancefromconceptAtoPerpend;// should take relative location
												// into account
	public double distancefromconceptBtoPerpend;// should take relative location
												// into account
	public int if_perpendicular_between;

	public FeatureOfPerpendicular() {
		perpendicularinfo = new ConceptInfo();
		Linepairinfo = new conceptpairs();
	}
	
	@Override
	public String toString()
	{
		String printfeature = "[" + 
				if_samesentence + ".;" + 
				ifsplitByTo + ".;" + 
				ifsplitByAnd + ".;" + 
				If_anotherlinebetween + ".;" + 
				distance_inchunker + ";" + 
				distancefromconceptAtoPerpend + ";" + 
				distancefromconceptBtoPerpend + ";" + 
				if_perpendicular_between + "." + "]";
		
		return printfeature;
	}
}
