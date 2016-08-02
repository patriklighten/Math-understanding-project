package com.mathapollo.feature;

import java.util.ArrayList;
import java.util.List;

import com.mathapollo.feature.compose.ComposeFeature;
import com.mathapollo.feature.merge.MergeFeature;
import com.mathapollo.io.ConceptInfo;
import com.mathapollo.io.ProblemModule;
import com.mathapollo.io.WordFeatureInfo;
import com.mathapollo.parsing.EventandRelation.*;
public class computeFeature {
	
	public MergeFeature mergeFeature=new MergeFeature();
	public MapFeature mapFeature=new MapFeature();
	public ComposeFeature composeFeature= new ComposeFeature();
	/*                           
	// public int if_sameTag;
    // public int if_same_sentence;
    public int if_SameConcept_between;
   // public int if_AhasTag;
   // public int if_BhasTag;// include first, second; 
   // public int if_Bhas_anotherLabel;
    public int if_eventBetween;
    public int if_sameValue;
    public int if_sameChunker;//derive from same chunk,phrase
    public int if_Beverb_between;
    public int if_proposition_between;// "the circle is center at point1", at infer the center is point1, at is similar to the role of verb  
    public int distance_inchunk;
	 */
	// the input is not the conceptinstance.conceptinfo, for exeample, circle.Circle_Conceptinfo, equation.Equation_ConceptInfo
     
	// conceptual level features, the features don't include specific concept instance feature, like if two concept instance has same value 
	public MergeFeature computeMergefeature(ProblemModule pm,ConceptInfo concept1, ConceptInfo concept2)
     {
    	 // conceptA is concept appear spatially before the  conceptB   
    	 // if concept1 is same with concept2, when two concept instance derive from same concept appear in problem, we need not further compute other features
    	 
		MergeFeature mergeFeature1=new MergeFeature();
		mergeFeature1.MergePair.concept1=concept1;
		mergeFeature1.MergePair.concept2=concept2;
		mergeFeature1.conceptcode=concept1.concept_code+","+concept2.concept_code;
    	 if(concept1.chunk_info.equals(concept2.chunk_info))
    	 {
    		 mergeFeature1.if_sameChunker=1;
    		 mergeFeature1.distance_inchunk=0;
    		 mergeFeature1.if_AhasTag=0;
    		 mergeFeature1.if_BhasTag=0;
    		 mergeFeature1.if_Beverb_between=0;
    		 mergeFeature1.if_Bhas_anotherLabel=0;
    		 mergeFeature1.if_eventBetween=0;
    	     mergeFeature1.if_proposition_between=0;
    		 mergeFeature1.if_same_sentence=1;
    		 mergeFeature1.if_sameChunker=1;
    		 mergeFeature1.if_SameConcept_between=0;
    		 mergeFeature1.if_sameTag=0;
    		
     
    				 
    	 }
    	 
    	 
    	 else{
    		 mergeFeature1.if_sameChunker=0;
    	 ConceptInfo conceptA= new ConceptInfo();
    	 ConceptInfo conceptB=new ConceptInfo();
    	 
    	 if(concept1.NNtoken_index<concept2.NNtoken_index)
    	 {                                                                                              
    		 conceptA=concept1;
    	     conceptB=concept2;
    	
    	 }
    	 else 
    	 {
    		 conceptA=concept2;
    	     conceptB=concept1;
    	 }
    
    	 if(conceptA.conceptFeature.features.if_The_before==1)
    	     mergeFeature1.if_AhasThebefore=1;
    	 if(conceptB.conceptFeature.features.if_The_before==1)
    		 mergeFeature1.if_BhasThebefore=1;
    		 // same sentence
    	 if(conceptA.chunk_info.sentence_index==conceptB.chunk_info.sentence_index)
    		 mergeFeature1.if_same_sentence=1;
    	 else
    		 mergeFeature1.if_same_sentence=0;
    	 //if same tag, if A has tag, if B has tag 
    	 mergeFeature1.if_sameTag=0;
    	 
    		 if(conceptA.concept_tag==null)
    			 mergeFeature1.if_AhasTag=0;
    		 else {
    			 mergeFeature1.if_AhasTag=1;
			}

    		 if(conceptB.concept_tag==null)
    			 mergeFeature1.if_BhasTag=0;
    		 else {
    			 mergeFeature1.if_BhasTag=1;
			}
    		
    	if(conceptA.concept_tag!=null&&conceptB.concept_tag!=null)
    	{ if(conceptA.concept_tag.equals(conceptB.concept_tag))
    	 {
    		 mergeFeature1.if_sameTag=1;
    	 }
    	 else {
			mergeFeature1.if_sameTag=0;
		}
    	}
    	 //if conceptB has "another" label 
    	 if(pm.FeatureChain.get(conceptB.NNtoken_index-1).token.toLowerCase().contains("another"))
    		 mergeFeature1.if_Bhas_anotherLabel=1;
    	 else
    		 mergeFeature1.if_Bhas_anotherLabel=0;
    	 //if they are derive from same concept mention, if this case, we need not compute other feature
                   
    	 //if event between 
    	 mergeFeature1.if_eventBetween=if_event_between(pm, concept1, concept2);
    	 // if verb is between 
    	 mergeFeature1.if_Beverb_between=if_verbis_between(pm, concept1, concept2);
    	 // if same concept withB between
    	if(if_same_conceptbetween(pm, conceptA, conceptB)!=0)
    	 mergeFeature1.if_SameConcept_between=1;
       if(conceptB.chunk_info.chunk_index-conceptA.chunk_info.chunk_index>0)
       {  
    	   //double d1=conceptB.chunk_info.chunk_index-conceptA.chunk_info.chunk_index;
    	   double d0=1;
      	   mergeFeature1.distance_inchunk= d0/Math.abs((conceptB.global_index2-conceptA.global_index2));
       }
       else {
  		 mergeFeature1.distance_inchunk=0;
		}
       
       if(mergeFeature1.if_same_sentence==0)
       {
    	   // if two concepts is not in a same sentence, the following feature is less meaningful, so assign value of 0 to them 
    	   mergeFeature1.if_proposition_between=0; // proposition information need to infer in the program 
    	   mergeFeature1.if_Beverb_between=0;
    	   mergeFeature1.if_eventBetween=0;
    	  
    	 
       }
    	   mergeFeature1.if_conceptbetwen_has_the_before=if_concepts_betweenhasThe( pm, concept1,  concept2);
    	 
    	 if(conceptB.conceptFeature.features.is_followed_by_of==2)
    		 mergeFeature1.if_conceptBisbefore_of=1;
    	 }
    	//refined the feature
      
    	 
    	 
 
    	   return mergeFeature1;
     }
	
     public int if_event_between(ProblemModule pm, ConceptInfo concept1, ConceptInfo concept2)
     {
    	 int flag=0;
    	 ConceptInfo conceptA= new ConceptInfo();
    	 ConceptInfo conceptB=new ConceptInfo();
    	 
    	 if(concept1.NNtoken_index<concept2.NNtoken_index)
    	 { 
    		 conceptA=concept1;
    	     conceptB=concept2;
    	
    	 }
    	 else 
    	 {
    		 conceptA=concept2;
    	     conceptB=concept1;
    	 }
    	 List<ConceptInfo> eventlist=pm.EventChain;
    	 for(ConceptInfo tempevent:eventlist)
    	 {
    	    if(conceptB.NNtoken_index>tempevent.NNtoken_index&&conceptA.NNtoken_index<tempevent.NNtoken_index)
    	    {
    	    	flag=1;
    	    	break;
    	    }
    	 }
    	 
    	if(concept1.chunk_info.sentence_index!=concept2.chunk_info.sentence_index)
    		flag=0;
    	
    	 return flag;
    	 
     }
     
     public int if_verbis_between(ProblemModule pm, ConceptInfo concept1,ConceptInfo concept2)
     {
    	int flag=0;
    	int index1=0;
    	int index2=0;
    	if(concept1.NNtoken_index<concept2.NNtoken_index)
    	{
    		index1=concept1.NNtoken_index;
    		index2=concept2.NNtoken_index;
    	}
    	else
    	{
    		index1=concept2.NNtoken_index;
    		index2=concept1.NNtoken_index;
    	}
    	for(int i=index1;i<index2;i++)
    	{
    	  if(pm.FeatureChain.get(i).POS_Tag.contains("VB")&&pm.FeatureChain.get(i).token.toLowerCase().contains("is"))
    	  {
    		 flag=1;
    		 break;
    	  }
    	}
    	if(if_same_conceptbetween(pm, concept1, concept2)!=0)
    	{
    		flag=0;
    	}
    	return flag;
     }
	
     
     public int if_verb_between(ProblemModule pm, ConceptInfo concept1,ConceptInfo concept2)
     {
    	int flag=0;
    	int index1=0;
    	int index2=0;
    	if(concept1.NNtoken_index<concept2.NNtoken_index)
    	{
    		index1=concept1.NNtoken_index;
    		index2=concept2.NNtoken_index;
    	}
    	else
    	{
    		index1=concept2.NNtoken_index;
    		index2=concept1.NNtoken_index;
    	}
    	for(int i=index1;i<index2;i++)
    	{
    	  if(pm.FeatureChain.get(i).POS_Tag.contains("VB")&&pm.FeatureChain.get(i).token.toLowerCase()!="is")
    	  {
    		 flag=1;
    		 break;
    	  }
    	}
    	
    	return flag;
     }
	
	public int if_same_conceptbetween(ProblemModule pm, ConceptInfo concept1, ConceptInfo concept2)
	{
	    int flag1=0;                                
	    int flag2=0;                                
	    int index1=0;                                
    	int index2=0;                            
    	if(concept1.NNtoken_index<concept2.NNtoken_index)
    	{
    		index1=concept1.NNtoken_index;
    		index2=concept2.NNtoken_index;
    	}
    	else
    	{
    		index1=concept2.NNtoken_index;
    		index2=concept1.NNtoken_index;
    	}
	    List<ConceptInfo> conceptlist=pm.ConceptChain;
	    for(ConceptInfo concept: conceptlist)
	    {
	         if(concept.NNtoken_index<index2&&concept.NNtoken_index>index1)
	         {
	        	 if(concept.concept_code.equals(concept2.concept_code))
	           {
	        	flag1=2;
	        	//break;
	           }
	             else if(concept.concept_code.equals(concept1.concept_code))
	           {
	        	flag2=1; 
	        	//break;
	            }
	         } 
	    }
	    
		return flag1+flag2;
	    
	}
	
	// for merge feature, concept1.concept_code == concept2.concept_code
	public int if_concepts_betweenhasThe(ProblemModule pm, ConceptInfo concept1, ConceptInfo concept2)
	{
	    int flag1=1;                                
	    int flag2=1;                              
	    int index1=0;                                
    	int index2=0;                            
    	if(concept1.NNtoken_index<concept2.NNtoken_index)
    	{
    		index1=concept1.NNtoken_index;
    		index2=concept2.NNtoken_index;
    		flag2=concept2.conceptFeature.features.if_The_before;
    	}
    	else
    	{
    		index1=concept2.NNtoken_index;
    		index2=concept1.NNtoken_index;
    		flag2=concept1.conceptFeature.features.if_The_before;
    	}
	    List<ConceptInfo> conceptlist=pm.ConceptChain;
	    for(ConceptInfo concept: conceptlist)
	    {
	         if(concept.NNtoken_index<index2&&concept.NNtoken_index>index1&&concept.conceptFeature.features.if_The_before==0)
	         {
	        
	        	 if(concept.concept_code.equals(concept1.concept_code))
	        	 flag1=0;
	         }
	         
	        	 
	    }
	    if(flag2==0)
	    	flag1=0;
	    
		return flag1;
	    
	}
	public int if_another_conceptbetween(ProblemModule pm, ConceptInfo concept1, ConceptInfo concept2)
	{
	    int flag=0; 
	    int index1=0;
    	int index2=0;
    	if(concept1.NNtoken_index<concept2.NNtoken_index)
    	{
    		index1=concept1.NNtoken_index;
    		index2=concept2.NNtoken_index;
    	}
    	else
    	{
    		index1=concept2.NNtoken_index;
    		index2=concept1.NNtoken_index;
    	}
	    List<ConceptInfo> conceptlist=pm.ConceptChain;
	    for(ConceptInfo concept: conceptlist)
	    {
	         if(concept.NNtoken_index<index2&&concept.NNtoken_index>index1)
	        if(!concept.concept_code.equals(concept2.concept_code))
	        	if(!concept.concept_code.equals(concept1.concept_code))
	        {
	        	flag=1;
	        	break; 
	        }
	       
	    }
	    
		return flag;
	    
	}
	
	// compute map feature 
	/*
	 public int if_in_same_sentence;
     public int distance_inchunker;
     public int is_beverb_between;
     public int relative_location; //<a,b> a is before b or a is after b
     public int is_instance_between;
     public int is_of_between;
     public int is_event_between;// if there is event relate two concept they should not merge
    // compute in the specific situation, two specific concept instance compare 
     public int is_anotherA_between;
     public int is_anotherB_between;
	 */
	
	// for mapping case, the concept and value may be in one chunk, but we regard it as one chunk distance 
	public MapFeature computemapfeature(ProblemModule pm,ConceptInfo concept1, ConceptInfo concept2)
	{
		MapFeature mapFeature1= new MapFeature();
       if(Math.abs(concept1.chunk_info.chunk_index-concept2.chunk_info.chunk_index)<2)
    	   mapFeature1.distance_inchunker=1;
       else 
       {
    	   double d0=1;
    	   //double d1=(Math.abs(concept1.chunk_info.chunk_index-concept2.chunk_info.chunk_index));
    	   mapFeature1.distance_inchunker=d0/Math.abs(concept1.global_index2-concept2.global_index2);
       }
       if(concept1.chunk_info.sentence_index==concept2.chunk_info.sentence_index)
      mapFeature1.if_in_same_sentence=1;
       else
    	   mapFeature1.if_in_same_sentence=0;
       
       mapFeature1.is_beverb_between=if_verbis_between(pm, concept1, concept2);
       if(concept1.NNtoken_index>concept2.NNtoken_index)
    	   mapFeature1.relative_location=2;// concept1 after value
       else
    	   mapFeature1.relative_location=1;// concept1 befoe value;
       
       if(if_same_conceptbetween(pm, concept1, concept2)==1)
       {
    	   mapFeature1.is_anotherA_between=1;
       }
       else if(if_same_conceptbetween(pm, concept1, concept2)>0&&concept1.concept_code.equals(concept2.concept_code))
    	   mapFeature1.is_anotherA_between=1;
       else {
    	   mapFeature1.is_anotherA_between=0;
	}
       if(if_same_conceptbetween(pm, concept1, concept2)==2)
    	   mapFeature1.is_anotherB_between=1;
       else if(if_same_conceptbetween(pm, concept1, concept2)>0&&concept1.concept_code.equals(concept2.concept_code))
    	   mapFeature1.is_anotherB_between=1;
       else
    	   mapFeature1.is_anotherB_between=0;
       
      mapFeature1.is_another_instance_between=if_another_conceptbetween(pm, concept1, concept2);
       
       mapFeature1.is_event_between=if_event_between(pm, concept1, concept2);
       
       return mapFeature1;
		
	}
	
	
     // compose feature computing 
	/*
	 int if_samesentence;
	int ifsplitByVerb;// contains, pass, has,on 
	int if_BisofA;
	int if_BfollowedbyOf;// "there is a line, the slope of it is 3, y-intercept of it is 2"
	int distance_inchunker;
	int If_sametypeconceptA_between;
	int if_sametypeConceptB_between;
	 */
	public ComposeFeature composefeaturecomputing(ProblemModule pm, ConceptInfo concept1, ConceptInfo concept2)
	{
		
		// concept 1 is lower concept, concept2 is higher concept
		ComposeFeature composeFeature1= new ComposeFeature();
		
		composeFeature1.Composepair.concept1=concept1;
		composeFeature1.Composepair.concept2=concept2;
		
		 ConceptInfo conceptA= new ConceptInfo();
    	 ConceptInfo conceptB=new ConceptInfo();
    	 
    	 if(concept1.NNtoken_index<concept2.NNtoken_index)
    	 {                                                                                                           
    		 conceptA=concept1;
    	     conceptB=concept2;
    	                                                                       
    	 }
    	 else 
    	 {
    		 conceptA=concept2;
    	     conceptB=concept1;
    	 }
    	 
    	 
    	if(conceptA.conceptFeature.features.is_followed_by_of==2)
    	{
          if(pm.ConceptChain.size()>conceptA.global_index+1)
          {
        	  if(pm.ConceptChain.get(conceptA.global_index+1).equals(conceptB))
        	  {
        		 if(conceptA.chunk_info.sentence_index==conceptB.chunk_info.sentence_index)
        		  composeFeature1.if_BisofA=1;
        	  }
        	  else if(conceptA.chunk_info.sentence_index==pm.ConceptChain.get(conceptA.global_index+1).chunk_info.sentence_index)
        	  {
        		  composeFeature1.if_element_isof_anotherconcept=1;
        	  }
          }
          
          
    	}
    	
    	if(conceptB.conceptFeature.features.is_followed_by_of==2)
    	{
    		 if(pm.ConceptChain.size()>conceptB.global_index+1)
    		 {
    			 if(conceptB.chunk_info.sentence_index==pm.ConceptChain.get(conceptB.global_index+1).chunk_info.sentence_index)
    			 {
    			    composeFeature1.if_element_isof_anotherconcept=1;
    			 }
    		 }
    	}
    	
    	 
		 if(conceptA.chunk_info.sentence_index==conceptB.chunk_info.sentence_index)
			 composeFeature1.if_samesentence=1;
		       else
		    	   composeFeature1.if_samesentence=0;
		
         composeFeature1.ifsplitByVerb=if_verb_between(pm, conceptA, conceptB);
         
         if(conceptB.conceptFeature.features.is_followed_by_of==2)
         composeFeature1.if_BbeforeOf=1;// if B is just after of
         else
        	 composeFeature1.if_BbeforeOf=0;
          if(conceptA.conceptFeature.features.is_followed_by_of==2)// if B is just before of 
        	 composeFeature1.if_A_is_beforeOf=1;
          else
        	  composeFeature1.if_A_is_beforeOf=0;
         
         if(if_same_conceptbetween(pm, conceptA, conceptB)==1)
         
        	 composeFeature1.If_sametypeconceptA_between=1;
         else if(if_same_conceptbetween(pm, conceptA, conceptB)>0&&conceptA.concept_code.equals(conceptB.concept_code))
         {
        	 composeFeature1.If_sametypeconceptA_between=1;
        	 composeFeature1.if_sametypeConceptB_between=1;
         }
         
       
         if(if_same_conceptbetween(pm, conceptA, conceptB)==2)
        	 composeFeature1.if_sametypeConceptB_between=1;
         else if(if_same_conceptbetween(pm, conceptA, conceptB)>0&&conceptB.concept_code.equals(conceptA.concept_code))
         {
        	 composeFeature1.if_sametypeConceptB_between=1;
        	 composeFeature1.If_sametypeconceptA_between=1;
         }
      
          if(if_same_conceptbetween(pm, conceptA, conceptB)==3)
         {
        	 composeFeature1.If_sametypeconceptA_between=1;
        	 composeFeature1.if_sametypeConceptB_between=1;
         }
         
         
         if(Math.abs(conceptB.global_index2-conceptA.global_index2)<2)
         {
        	
      	  composeFeature1.distance_inchunker= 1;
         }
         else
         {
        	 //double d1=conceptB.chunk_info.chunk_index-conceptA.chunk_info.chunk_index;
        	 double d0=1;
        	composeFeature1.distance_inchunker=d0/Math.abs(conceptA.global_index2-conceptB.global_index2); 
         }
         
      // if the two concept is not in a same sentence, then some feature should be zero     
         if(composeFeature1.if_samesentence==0)
         {
        	  
        	 composeFeature1.ifsplitByVerb=0;
        	 //composeFeature1.if_A_is_beforeOf=0;
        	 composeFeature1.if_BisofA=0;
        	 //composeFeature1.if_BfollowedbyOf=0;
        	 //composeFeature1.if
        	 
         }
         
         if(concept1.global_index2<concept2.global_index2)
        	 composeFeature1.relativelocation=-1;
         else 
        	 composeFeature1.relativelocation=1;
         
		return composeFeature1;
	}
     


}
