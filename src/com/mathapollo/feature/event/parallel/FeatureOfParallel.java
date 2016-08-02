package com.mathapollo.feature.event.parallel;

import com.mathapollo.feature.conceptpairs;
import com.mathapollo.io.ConceptInfo;

public class FeatureOfParallel {
	public ConceptInfo parallelinfo;
	public conceptpairs Linepairinfo;

	public int if_samesentence;// if the conceptA, B and the distance concept in
								// the same sentence
	public int ifsplitByTo;// distance from point1 to the line
	public int ifsplitByAnd;// two perpendicular line A and B
	public double distance_inchunker;// distance between conceptA and conceptB
	public int If_anotherlinebetween;
	public double distancefromconceptAtoParallel;// should take relative
													// location into account
	public double distancefromconceptBtoParallel;// should take relative
													// location into account
	/*
	 * in the case of there is a parallel concept between two lines,and they are
	 * same sentence, in addition, if there is no other line concept between two
	 * lines, then the two lines would be parallel lines, so we use the
	 * if_parallel_between feature to indicate this case
	 */
	public int if_parallel_between;

	public FeatureOfParallel() {
		parallelinfo = new ConceptInfo();
		Linepairinfo = new conceptpairs();
	}
	
	@Override 
	public String toString()
	{
		String printfeature = "[" + 
				if_samesentence + ";" + 
				ifsplitByTo + ";" + 
				ifsplitByAnd + ";" +
				If_anotherlinebetween + ";" + 
				if_parallel_between + ";" + 
				distance_inchunker + ";" + 
				distancefromconceptAtoParallel + ";" + 
				distancefromconceptBtoParallel + "]";
		return printfeature;
	}
}
