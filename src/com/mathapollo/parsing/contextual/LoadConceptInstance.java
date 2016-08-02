package com.mathapollo.parsing.contextual;

import java.net.ConnectException;
import java.util.ArrayList;

import org.junit.experimental.theories.Theories;

import edu.illinois.cs.cogcomp.lbj.pos.wordForm;

import com.mathapollo.feature.conceptFeatureNode;
import com.mathapollo.io.*;
import com.mathapollo.knowledge.concept.Equation;
import com.mathapollo.knowledge.concept.Slope;
import com.mathapollo.knowledge.concept.Value;

public class LoadConceptInstance {

	 public ComputeQuantity computequantity= new ComputeQuantity();
	public ArrayList<QuantityInfo> unknownquantities= new ArrayList<QuantityInfo>(); 
	public ArrayList<QuantityInfo> knownquantities= new ArrayList<QuantityInfo>();
	public ArrayList<Sentence> sentences= new ArrayList<Sentence>();
// load all conceptInstance 
public void LoadAllConceptInstance()
{
	
}

// load all possible values including unknown quantity 
public void LoadAllquantity()
{
	// load known CD quantity, different with the cd value for the number of the concept instance
	// the CD quantity mainly exist after the 
	
	
	// load unknown cd value 
	//(1) what 
	// (2) find, graph, sketch and some verb in the first location of a sentence 
	
	
}






public ArrayList<QuantityInfo> findUnknConceptAfterWhat(ProblemModule pm)
{
	// find what list information   
	ArrayList<QuantityInfo> unknownquantities1= new ArrayList<QuantityInfo>();
  ArrayList<WordFeatureInfo> WhatList= new ArrayList<WordFeatureInfo>();
  QuantityInfo temp_unknownquant= new QuantityInfo();
  for(WordFeatureInfo word: pm.FeatureChain)
  {    WordFeatureInfo temp_word=new WordFeatureInfo();
	  if(word.token.toLowerCase().equals("what"))
	  {
		  temp_word=word;
		 WhatList.add(temp_word);
  }
  }
    // if the value is the nearest word, it is very likely that an "of" is following   
	 // if there is value, the value should with 3 tokens after the what 
	
  for( WordFeatureInfo temp_what: WhatList)
	  {
	  int[] valueAfterWhat=if_value_ofAfterWhat(pm, temp_what.token_index);
		  // if there is a value after and near the what
		 if(valueAfterWhat[0]==2)
		 {
		   //record the concept after the "value of" 
		    ConceptInfo conceptinfo= computequantity.findnearestBehindConcept(pm.FeatureChain.get(valueAfterWhat[1]), pm);
		    if(conceptinfo.chunk_info!=null)
		    if(conceptinfo.chunk_info.sentence_index==temp_what.sentence_index)
		    {
		    	temp_unknownquant.quantityinfo=conceptinfo.chunk_info;
		    
					  temp_unknownquant.quantitytoken=conceptinfo.concept_code;
					  temp_unknownquant.relatedConcept=conceptinfo;
					  unknownquantities1.add(temp_unknownquant);
		    }
		 }
		
						 
		 if(valueAfterWhat[0]==1)
		 {
			 ConceptInfo conceptinfo=computequantity.findnearestBeforeConcept(pm.FeatureChain.get(valueAfterWhat[1]), pm);
			 if(conceptinfo.chunk_info!=null&&conceptinfo.chunk_info.sentence_index==temp_what.sentence_index)
			 { temp_unknownquant.quantityinfo=conceptinfo.chunk_info;
			  temp_unknownquant.quantitytoken=conceptinfo.concept_code;
			  temp_unknownquant.relatedConcept=conceptinfo;
			  unknownquantities1.add(temp_unknownquant);
			 }
		 }
			
		  // if not the case of value of after what 
		  //record the nerear concept after word 
		  if(valueAfterWhat[0]==0)
		  {ConceptInfo quantity_concept= new ConceptInfo();
		  quantity_concept=computequantity.findnearestBehindConcept(temp_what,pm);
		  if(quantity_concept.chunk_info!=null&&quantity_concept.chunk_info.sentence_index==temp_what.sentence_index)
		  temp_unknownquant.quantityinfo=quantity_concept.chunk_info;
		  temp_unknownquant.relatedConcept=quantity_concept;
		  temp_unknownquant.quantitytoken=quantity_concept.concept_code;
		  unknownquantities1.add(temp_unknownquant);
		  quantity_concept= new ConceptInfo();
		  temp_unknownquant= new QuantityInfo();
		  
		  }
	  }
   
  
  return unknownquantities1;
}

// return 1 if there is word "value" after "what", return 2 if there is "value of" after "what", return 0 if there is no word "value" after what      
public int[] if_value_ofAfterWhat(ProblemModule pm, int what_index)
{
	int[] flag= new int[2];
	flag[0]=0;
	flag[1]=0;
	int what_sentenceindex=pm.FeatureChain.get(what_index).sentence_index;
	for(WordFeatureInfo temp_word: pm.FeatureChain)
	{
		if(temp_word.token_index>what_index&&temp_word.sentence_index==pm.FeatureChain.get(what_index).sentence_index)
		if(temp_word.token.equals("value"))
		{
			if(pm.FeatureChain.get(temp_word.token_index+1)!=null)
			{
				if(pm.FeatureChain.get(temp_word.token_index+1).token.equals("of"))
					{
					   flag[0]=2;
					   flag[1]=temp_word.token_index+1;// "value of" after what 
					}
				else 
				{
					   flag[0]=1;
					   flag[1]=temp_word.token_index;// "value of" after what 
					};// there is no "of" follow the word "value"
			}
			else 
			{
				flag[0]=1;
				
			}
		}
			
	}
	return flag;
    
}

// find unknown quantity after the imperative verb 
public ArrayList<QuantityInfo> findUnknowAfterImperative(ProblemModule pm)
{
	ArrayList<QuantityInfo> unknownquantities2= new ArrayList<QuantityInfo>();
	 ArrayList<WordFeatureInfo> ImperativeList= new ArrayList<WordFeatureInfo>();
	 QuantityInfo temp_unknownquant= new QuantityInfo();
	 
	 ProblemtoSentences problemsentence = new ProblemtoSentences();
	 ArrayList<Sentence> sentences = problemsentence.ProblemToSentences(pm);
	 
	 WordFeatureInfo temp_word;
	 for(Sentence sentence:sentences)
	 {
		temp_word= new WordFeatureInfo();
		temp_word=sentence.sentence.get(0).words_info.get(0);// imperative verb is always the first word of the problem 
		if(temp_word.POS_Tag.contains("VB"))
		ImperativeList.add(temp_word);
	 }
	ConceptInfo temp_concept; 
	 for(WordFeatureInfo word: ImperativeList)
	 {
		 temp_concept= computequantity.findnearestBehindConcept(word, pm);
		 if(temp_concept.chunk_info!=null&&temp_concept.chunk_info.sentence_index==word.sentence_index)
		 { temp_unknownquant= new QuantityInfo();
		 temp_unknownquant.quantityinfo=temp_concept.chunk_info;
		 temp_unknownquant.quantitytoken=temp_concept.concept_code;
		 temp_unknownquant.relatedConcept=temp_concept;
		 unknownquantities2.add(temp_unknownquant);
		 }
	 }
	 return unknownquantities2;
	 
}
   // load quantity, know and unknown to concept instances 
// we currently load line only, we don't consider the point, later we will consider distance
public void loadQuantityToConcept(ProblemModule pm)
{
	ArrayList<CDvalue> cdchain= pm.cdchain;
	QuantityInfo temp_unknownquantity=new QuantityInfo();
    ConceptInfo temp_relatedconcept= new ConceptInfo();
	//load cdvalue to related concept instances 
	for( CDvalue cdvalue: cdchain)
	{
		temp_relatedconcept=cdvalue.relatedConceptInfo;
		// currently consider slope only 
		if(temp_relatedconcept.NNPOS!=null)
	    if(temp_relatedconcept.concept_code.equals("slope"))
	    {
	    	for(Slope temp_slope:pm.ConceptInstances.SlopeInstances)
	    	{  
	    		//some times the cd value is not meaningful, for example, "(1)"
	    		if(temp_slope.slope_info.NNtoken_index==temp_relatedconcept.NNtoken_index)
	    			{
	    			temp_slope.slopeValue.valueinfo=cdvalue.wordinfo;
	    		    temp_slope.slopeValue.unknown_variable="known";
	    		   	}
	    	}
	    }
	    	
	}
	
	
	//load inknown quantities to related concept instances, we only involve the case of slope and standard form  
	for(QuantityInfo unknownquant: pm.unknownquantities)
	{
		temp_unknownquantity=unknownquant;
		if(temp_unknownquantity.relatedConcept!=null)
		if(temp_unknownquantity.relatedConcept.concept_code!=null&&temp_unknownquantity.relatedConcept.concept_code.equals("standard form"))
		{
			for(Equation equationform:pm.ConceptInstances.EquationInstances)
			{
				if(equationform.Equation_ConceptInfo.concept_code.equals("standard form"))
				{
					if(equationform.Equation_ConceptInfo.NNtoken_index==temp_unknownquantity.relatedConcept.NNtoken_index)
					// todo: we should load equation if equation is known 
						equationform.equation="unknown";
					equationform.unknown_variable="unknown";
				}
			}
		}
		else if(temp_unknownquantity.relatedConcept!=null)
		if(temp_unknownquantity.relatedConcept.concept_code!=null&&temp_unknownquantity.relatedConcept.concept_code.equals("slope"))
		{
			for(Slope temp_slope:pm.ConceptInstances.SlopeInstances)
	    	{
	    		if(temp_slope.slope_info.NNtoken_index==temp_unknownquantity.relatedConcept.NNtoken_index)
	    			{
	    			//temp_slope.slopeValue.valueinfo=;// we should load its related concept, for example,line 
	    		    temp_slope.slopeValue=new Value();
	    		    temp_slope.slopeValue.unknown_variable="unknown";
	    			}
	    	}
		}
	}
	
}


}
