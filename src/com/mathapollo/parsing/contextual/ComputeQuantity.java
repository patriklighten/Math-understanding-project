package com.mathapollo.parsing.contextual;

import java.security.KeyStore.TrustedCertificateEntry;
import java.util.ArrayList;
import java.util.List;

import org.junit.experimental.theories.Theories;

import com.mathapollo.io.Chunkers;
import com.mathapollo.io.ConceptInfo;
import com.mathapollo.io.ConceptInstances;
import com.mathapollo.io.ProblemModule;
import com.mathapollo.io.WordFeatureInfo;

import edu.illinois.cs.cogcomp.lbj.coref.util.aux.SamePair;

public class ComputeQuantity {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
 
    public int ComputeQuantity1(ProblemModule pm, ConceptInfo conceptinfo)
    {
    	int number=1;
        if(conceptinfo.NNPOS.equals("NN"))
            number=1;
        else if (conceptinfo.NNPOS.equals("NNS")) {
			//if the concepts instance type is plural
        	number=2;// default 2; 
          if(isthere_CDbefore(conceptinfo)>-1)
          {
        	  int cdindex=isthere_CDbefore(conceptinfo);
             number=ComputeCD_Quantity(pm,cdindex);
             if(cdindex>2)
             {
            	 // if there is number of number concepts structure, one point of three points 
            WordFeatureInfo wordinfo= pm.FeatureChain.get(cdindex-2);
            if(wordinfo.POS_Tag.contains("CD")&&pm.FeatureChain.get(cdindex-1).token.equals("of"))
     	   {
     		   
     			   number =ComputeCD_Quantity(pm, wordinfo.token_index);// the nearest beforehand  cd would be the idea cd
     		  //return wordinfo.token_index;
         	}
             }
             
             
             return number;
          }
         
        } // end of else if
        
    	
    	return number; 
    }
   
    public int isthere_CDbefore (ConceptInfo conceptinfo)
    {
        int cd_index=-1;// if there is cd before the concept in the same chunk, the cd_index>-1
    	// todo 
    	Chunkers chunkerinfo= conceptinfo.chunk_info;      
    	for(WordFeatureInfo wordinfo: chunkerinfo.words_info)
    	{
    	   if(wordinfo.POS_Tag.contains("CD")&&wordinfo.token_index<conceptinfo.NNtoken_index)
    	   {
    		   if(wordinfo.token_index>cd_index)
    			   cd_index  = wordinfo.token_index;// the nearest beforehand  cd would be the idea cd
    		  //return wordinfo.token_index;
    	}
    	}
    	return cd_index;
    }
    
    public int ComputeCD_Quantity(ProblemModule pm, int CDindex)
    {
    	int quantity=1; // by defauld 1; 
    	String CDtoken=pm.FeatureChain.get(CDindex).token;// one, two, three, four 
    	switch(CDtoken.toLowerCase())
    	{
    	case "one":          
    		quantity=1;
    		break;
    	case "two":
    		quantity=2;
    		break;
    	case "tree":
    		quantity=3;
    	case "four": 
    		quantity=4;
    		break;
    	case "five":
    		quantity=5;
    		break;
    	case "six":
              quantity=6;
        		break;
        	case "seven":
        		quantity=7;
        		break;
        	case "eight":
        		quantity=8;
        		break;
        	case "nine":
        		quantity=9;
        	 break;
    		
    	}
    return quantity;
    }
    
    public int numberofCDbehind(ConceptInfo conceptinfo, ProblemModule pm)
    {
    	// don't consider the two concept instance in a same sentence circumstance 
    	int NN_token_index=conceptinfo.NNtoken_index;
    	int sentence_id= conceptinfo.chunk_info.sentence_index;
      int count=0;
      ComputeQuantity compute_quant= new ComputeQuantity();
    	for(WordFeatureInfo words:pm.FeatureChain)
    	{
    		if(words.POS_Tag.contains("CD")&&words.token_index>NN_token_index&&words.sentence_index==sentence_id)
    		{
    		  // make sure the cd behind is refer to the specify concept 
    			ConceptInfo temp_concept= new ConceptInfo();
    			temp_concept=compute_quant.findnearestBeforeConcept(words, pm);
    		if(temp_concept.chunk_info.chunk_index==conceptinfo.chunk_info.chunk_index)
    			++count;
    	       }
    	}
    	
    	return count;
         
    }
    
     public Boolean isSameSentenceConceptBehind(ConceptInfo conceptInfo, ProblemModule pm)
     {
    	Boolean bool= false;
        List<ConceptInfo> conceptchain=pm.ConceptChain;
        for(ConceptInfo concept: conceptchain)
        {
        
        	if(conceptInfo.chunk_info.sentence_index == concept.chunk_info.sentence_index)
        	{
        		if(conceptInfo.NNtoken_index<concept.NNtoken_index)
        			bool=true;
        		return bool;
        		
        	}
        
        }
        return bool;
     }
     
     public ConceptInfo nearestbehindconceptIndex(ConceptInfo conceptInfo, ProblemModule pm)
     {
    	 ConceptInfo conceptinfo_temp= new ConceptInfo();
    	 int conceptindex_difference=1000;// assume the 1000 is very large number
    	 int temp_index_different=1000;
    	 List<ConceptInfo> conceptchain=pm.ConceptChain;
    	
         for(ConceptInfo concept: conceptchain)
         {
        	 if((temp_index_different=conceptInfo.NNtoken_index-concept.NNtoken_index)<0)
        	 { 
        		 temp_index_different=Math.abs(temp_index_different);
            if(temp_index_different< conceptindex_difference)
            {
            	conceptinfo_temp= new ConceptInfo();
            	conceptinfo_temp=concept;
            	conceptindex_difference=temp_index_different;
            	
            }
         
         }
         }
         // may be there is no concept behind;
         return conceptinfo_temp;
     }
     
     // find nearest after concept 
     public ConceptInfo findnearestBehindConcept(WordFeatureInfo word, ProblemModule pm)
     {
    	 int distance= 1000; 
    	 int temp_distance=1000;
    	 ConceptInfo conceptinfomation= new ConceptInfo();
    	 for(ConceptInfo conceptinfo:pm.ConceptChain)
    	 {
    		if((temp_distance= conceptinfo.NNtoken_index-word.token_index)>0)
    		{
    			if(temp_distance<distance)
    			{
    				distance=temp_distance;
    				conceptinfomation= new ConceptInfo();
    				conceptinfomation=conceptinfo;
    			}
    		}
    	 }
    	 return conceptinfomation;
     }
     
     // find nearest beforehand concept 
     public ConceptInfo findnearestBeforeConcept(WordFeatureInfo word, ProblemModule pm)
     {
    	 int distance= 1000; 
    	 int temp_distance=1000;
    	 ConceptInfo conceptinfomation= new ConceptInfo();
    	 for(ConceptInfo conceptinfo:pm.ConceptChain)
    	 {
    		if((temp_distance= conceptinfo.NNtoken_index-word.token_index)<0)
    		{
    			   int temp_distance1=Math.abs(temp_distance);
    			if(temp_distance1<distance)
    			{
    				distance=temp_distance1;
    				conceptinfomation= new ConceptInfo();
    				conceptinfomation=conceptinfo;
    			}
    		}
    	 }
    	 return conceptinfomation;
     }
     
     
}

   
