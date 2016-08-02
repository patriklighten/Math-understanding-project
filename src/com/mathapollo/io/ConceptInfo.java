package com.mathapollo.io;

import java.util.Comparator;

import com.mathapollo.feature.*;

public class ConceptInfo {

	//private Chunker
	
	 public String concept_code; 
     public String concept_tag;
	 public Chunkers chunk_info;
	 // global_index2: global index for computing distance 
	 public int global_index2;//this is used for computing the distance in concept, the two same concept that has same chunk( two points) should have same global_index for distance  
     public int index; // concept instance index; 
	 public int NNtoken_index;// the token index of token with NN POS; "standard form"'s NN token is form 
	 public int Firstmention_index; // the index of first word of the concept phrase
	 public int if_event_concept;
	 public String NNPOS; 
	 
	 public int global_index; // global index is to index all the concepts independent of specific concept category 
	 public conceptFeatureNode conceptFeature= new conceptFeatureNode(concept_code);
	 public ConceptInfo()
	 {
		
	 }
	 // java base has toString, so here use Override method
	 @Override 
	 public String toString(){
		return concept_code;
	 }
	 /* sort comparator */
	 public static Comparator<ConceptInfo> conceptIndexcomparator= new Comparator<ConceptInfo>()
		{
			public int compare(ConceptInfo concept1,ConceptInfo concept2)
			{
				int chunkindex1=concept1.NNtoken_index;
				int chunkindex2=concept2.NNtoken_index;
				
				 /*For ascending order*/
				return chunkindex1-chunkindex2;                                
	                                                                                             
				/* for decending order*/          
				
				//return chunkindex2-chunkindex1;
			}
		};
	 
}
