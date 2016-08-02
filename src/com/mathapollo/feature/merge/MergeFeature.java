package com.mathapollo.feature.merge;

import com.mathapollo.feature.conceptpairs;
import com.mathapollo.io.annotation.FeatureIO;

public class MergeFeature extends FeatureIO{
	// <A,B>

	public conceptpairs MergePair; //<concept1,concept2> 
	
	public String conceptcode;
	public int[] index_of_conceptPair;
	
	// feature 
	public int if_sameTag;
    public int if_same_sentence;
    public int if_SameConcept_between;
    public int if_AhasTag;
    public int if_BhasTag;// include first, second; 
    public int if_Bhas_anotherLabel;
    public int if_eventBetween;
   // public int if_sameValue;
    public int if_sameChunker;//derive from same chunk,phrase
    public int if_Beverb_between;
    public int if_proposition_between;// "the circle is center at point1", at infer the center is point1, at is similar to the role of verb  
    public double distance_inchunk;
    public int if_BhasThebefore;
    public int if_AhasThebefore; 
    public int if_conceptbetwen_has_the_before; 
    public int if_conceptBisbefore_of; 
    public  MergeFeature()
    {
    	MergePair= new conceptpairs();
    	index_of_conceptPair=new int[2];
    }
    
    public void PrintFeature()
    {
    	String features="["+if_sameTag+".,"+if_same_sentence+".,"+if_SameConcept_between+".,"+if_AhasTag+".,"+if_BhasTag+".,"+if_Bhas_anotherLabel+".,"+if_eventBetween+".,"+if_sameChunker+".,"+if_Beverb_between+".,"+if_proposition_between+".,";
    	System.out.println(features);
    }
    
    @Override
	public String toString()
	{
//		int wordindex1 = MergePair.concept1.NNtoken_index;
//		int wordindex2 = MergePair.concept2.NNtoken_index;
    	String features="["+
    				if_sameTag+".;"+
    				if_same_sentence+".;"+
    				if_SameConcept_between+".;"+
    				if_AhasTag+".;" +
    				if_BhasTag+".;"+
    				if_Bhas_anotherLabel+".;"+
    				if_eventBetween+".;"+
    				if_sameChunker+".;"+
    				if_Beverb_between+".;"+
    				if_proposition_between+
    				".;"+
    				if_BhasThebefore+
    				".;"+
    				if_AhasThebefore+
    				".;"+
    				if_conceptbetwen_has_the_before+
    				".;"+
    				if_conceptBisbefore_of+
    				".]";
//		StringBuffer sb = new StringBuffer();
//		sb.append(wordindex1).append(',').append(wordindex2).append(',');
//		sb.append(features);
		//return sb.toString();
    	return features;
	}
    
    public void Save(String problemIndex)
    {
    	Save(problemIndex, this);
    } 
}
