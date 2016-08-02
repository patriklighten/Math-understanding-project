package com.mathapollo.feature;

import java.util.ArrayList;
import java.util.List;

import com.mathapollo.feature.conceptFeatureNode.conceptfeatures;
import com.mathapollo.io.*;
import com.mathapollo.knowledge.concept.Pointconcept;
import com.mathapollo.parsing.contextual.ComputeQuantity;
import com.mathapollo.parsing.contextual.ProblemtoSentences;
import com.mathapollo.parsing.contextual.Sentence;

import LBJ2.IR.IfStatement;
import edu.illinois.cs.cogcomp.lbj.coref.learned.eHHeadLastWord;

public class SingleFearture {

  //single concept feature 
  public List<conceptFeatureNode> linesFeature;
  public List<conceptFeatureNode> pointsFeature;
  public List<conceptFeatureNode> circlesFeature;
  public List<conceptFeatureNode> equationsFeature;
  public List<conceptFeatureNode> slopeFeature;
  public List<conceptFeatureNode> pointValueFeature;
  public ComputeQuantity computequantity= new ComputeQuantity();
  public ArrayList<WordFeatureInfo> WhatList= new  ArrayList<WordFeatureInfo>();
  public ArrayList<WordFeatureInfo> ImperativeList= new ArrayList<WordFeatureInfo>();
  public SingleFearture()
  {
	  linesFeature= new ArrayList<conceptFeatureNode>();
	  pointsFeature= new ArrayList<conceptFeatureNode>();
	  circlesFeature= new ArrayList<conceptFeatureNode>();
	  equationsFeature= new ArrayList<conceptFeatureNode>();
	  slopeFeature= new ArrayList<conceptFeatureNode>();
	  pointValueFeature= new ArrayList<conceptFeatureNode>();
  }
   
   public void computeFeature(ProblemModule pm)
   {     // for every line instance in the problem, compute a feature vector for it
	   for(int i=0;i<pm.ConceptChain.size();i++)
	   {
		   ConceptInfo temp_concept=pm.ConceptChain.get(i);
		   /*
		    public int is_followed_by_of;
    	public int  is_near_after_higher_concept;
    	public int is_after_imperativeverb;
    	public int is_after_what;
		    */
		   // currently, the single feature has 4 feature elements 
		   // compute the every feature element 
		   // if it after what(what is):
		   temp_concept.conceptFeature.features.is_after_what=if_after_what(pm, temp_concept);
		   //if it is followed by of( the value of):
		   
		   temp_concept.conceptFeature.features.is_followed_by_of=if_before_after_of(pm, temp_concept);
		   
		   // if it is near after or before another concept(line's center) 
		   temp_concept.conceptFeature.features.is_near_after_higher_concept=if_near_anotherConcept(pm, temp_concept)    ;                                                   
		                             
		 
			   temp_concept.conceptFeature.features.if_The_before=if_The_before(temp_concept);
		  
		   // if it is after an imperative verb 
		  // temp_concept.conceptFeature.features.is_after_imperativeverb=if_after_imperative(pm, temp_concept);
		   pm.ConceptChain.get(i).conceptFeature.features=temp_concept.conceptFeature.features;
	   }
   }
   public int if_The_before( ConceptInfo concept)
   {
	   int flag=0;
	   for(WordFeatureInfo wordinfo: concept.chunk_info.words_info)
	   {
		   if(wordinfo.token.toLowerCase().equals("the")||wordinfo.token.toLowerCase().equals("this"))
		   {
			   flag=1;
		   }
	   }
	   
	   return flag;
   }
   public int if_after_what(ProblemModule pm, ConceptInfo conceptinfo)
   {
	   int flag=0;
	   ArrayList<WordFeatureInfo> WhatList= findwhatlist( pm);
	   // find the what list
	  for(WordFeatureInfo temp_what: WhatList)
	  {
	   ConceptInfo temp_concept= new ConceptInfo();
		  temp_concept=computequantity.findnearestBehindConcept(temp_what,pm);
		 if(temp_concept.chunk_info!=null)
	    if(temp_concept.chunk_info.equals(conceptinfo.chunk_info))
	    {
	    	flag=1;// first concept after what 
	    	break;
	    }
	    // else if there is a concept after the concept that nearest to the what, we also take it as unknown concept
	    else
	    {
	      ConceptInfo temp_concept1= computequantity.findnearestBehindConcept(temp_concept.chunk_info.words_info.get(temp_concept.chunk_info.words_info.size()-1),pm);
	    if(temp_concept1.chunk_info!=null)
	      if(temp_concept1.chunk_info.equals(conceptinfo.chunk_info)&&temp_concept1.chunk_info.sentence_index==temp_what.sentence_index)
	    {
	    	flag=2;// second concept after what
	    	break;
	    }
	    }
	  }
	   
	  return flag;
	  
   }
   
   // if after of is 1, if before of is 2
   public int if_before_after_of(ProblemModule pm, ConceptInfo conceptInfo)
   {
	   int ifafterof=0;
	   Chunkers chunkinfo= new Chunkers();
	   // if the chunk before it contain "of" then the flag=1
	   if(conceptInfo.chunk_info.chunk_index>0)
	   {
		    chunkinfo=pm.ChunkerChain.get(conceptInfo.chunk_info.chunk_index-1);
	   
	   }
	   // if concept after "of" then ifafterof=1
	   if(chunkinfo.GetTokens().toLowerCase().contains("of"))
	   {
		   ifafterof=1;
		   
	   }
	   if(conceptInfo.chunk_info.chunk_index<pm.ChunkerChain.size()-2)
	   { Chunkers chunkinfo1=pm.ChunkerChain.get(conceptInfo.chunk_info.chunk_index+1);
	  
	   // if concept before "of", then ifafterof=2
	   if(chunkinfo1.GetTokens().toLowerCase().contains("of"))
	   {
		   ifafterof=2;
	   }
	   }
	   return ifafterof;
   }
   // this is the case like "the slope of line" and " the line's slope" the distance between two concept is no more than 3
   // this feature also include some event case, [a line] [intersect with] [the circle]  
   public int if_near_anotherConcept(ProblemModule pm, ConceptInfo conceptInfo)
   {
	   int flag=0;// there is no concept in nearby 
	   // if after another concept
	   int temp_distance=1000;
	   ConceptInfo beforeconcept= computequantity.findnearestBeforeConcept(conceptInfo.chunk_info.words_info.get(0),pm);
	   if(beforeconcept.chunk_info!=null)
	   {  temp_distance=conceptInfo.chunk_info.chunk_index-beforeconcept.chunk_info.chunk_index;
	   if(beforeconcept.chunk_info.sentence_index==conceptInfo.chunk_info.sentence_index)
		   if(temp_distance<3&&temp_distance>0)
		   {
			   flag=1;// after another concept instance
		   }
	   }
	   ConceptInfo afterconcept= computequantity.findnearestBehindConcept(conceptInfo.chunk_info.words_info.get(conceptInfo.chunk_info.words_info.size()-1),pm);
	  if(afterconcept.chunk_info!=null)
	  { temp_distance=afterconcept.chunk_info.chunk_index-conceptInfo.chunk_info.chunk_index;
	   if(afterconcept.chunk_info.sentence_index==conceptInfo.chunk_info.sentence_index)
		   if(temp_distance<3&&temp_distance>0)
		   {
			   flag=2;//before another concept instance
		   }
	  }
	   return flag;
   }
   
   public int if_after_imperative(ProblemModule pm,ConceptInfo conceptInfo)
   {
	   int flag=0;
	   ArrayList<WordFeatureInfo> ImperativeList=findImperativeList(pm);
	   for(WordFeatureInfo temp_imperative: ImperativeList)
	   {
		  ConceptInfo temp_concept=new ConceptInfo();
		  temp_concept=computequantity.findnearestBehindConcept(temp_imperative, pm);
		  ConceptInfo temp_concept1=computequantity.findnearestBehindConcept(temp_concept.chunk_info.words_info.get(temp_concept.chunk_info.words_info.size()-1), pm);
		  if(temp_concept.equals(conceptInfo))
		  {
			 if(conceptInfo.chunk_info.sentence_index==temp_imperative.sentence_index)
			 {
			      flag=1;// first concept
			      break;
			 }
		  }
		  else if(temp_concept1.equals(conceptInfo))
		  {
			  if(conceptInfo.chunk_info.sentence_index==temp_imperative.sentence_index)
				 {
				      flag=2;// sencond concept(but same sentence) after imperative 
				      break;
				 }
		  }
		 
	   }
	   return flag;
	   
	   
   }
   
   public ArrayList<WordFeatureInfo> findwhatlist(ProblemModule pm)
   {
	  
	   for(WordFeatureInfo word: pm.FeatureChain)
	   {    WordFeatureInfo temp_word=new WordFeatureInfo();
	 	  if(word.token.toLowerCase().equals("what"))
	 	  {
	 		  temp_word=word;
	 		 WhatList.add(temp_word);
	   }
	   }
	   
	   return WhatList;
   }
      
   public ArrayList<WordFeatureInfo> findImperativeList(ProblemModule pm)
   {
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
		 
		 return ImperativeList;
		 
   }
   
}
