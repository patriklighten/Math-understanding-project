package com.mathapollo.parsing.contextual;

import java.util.ArrayList;

import com.mathapollo.io.Chunkers;
import com.mathapollo.io.ConceptInfo;
import com.mathapollo.io.ProblemModule;
import com.mathapollo.io.WordFeatureInfo;
import com.mathapollo.knowledge.contract.ConceptMock;
import com.mathapollo.parsing.EventandRelation.DistanceRelationNode;
import com.mathapollo.parsing.EventandRelation.Intersect_relation;
import com.mathapollo.parsing.EventandRelation.ParallelRelation;
import com.mathapollo.parsing.EventandRelation.PerpendicularRe;


public class LoadEvent {
	
	private static ConceptMock _conceptBase= new ConceptMock();
	
public void LoadEvent(ProblemModule pm)
{
	for (Chunkers ch : pm.ChunkerChain) {
		// pattern match chunker with knowledge base to detect concept.
		// string matching
		String pattern = _conceptBase.ContainEvent(ch.GetTokens());
		
		
	  // record the NN part of the concept, in general a concept is NP, and contain a NN or NNS, 
		if(pattern != null)
		{
			ConceptInfo ci = new ConceptInfo();
			ci.chunk_info = ch;
			ci.concept_code = pattern;
			ci.if_event_concept=1;
			// find the last subpattern
			String[] subpatterns=pattern.split(" ");
			String last_subpattern = subpatterns[subpatterns.length - 1];
            String first_subpattern= subpatterns[0]; 
	
			
		    // find the location of the first word of concept
			int index_of_firstmentioned=0;
			int index_of_lastmentioned = 0;
			for(WordFeatureInfo word0:ch.words_info)
			{
			 if(word0.token.contains(first_subpattern))
			 {
				 index_of_firstmentioned=word0.token_index;
				 ci.Firstmention_index=index_of_firstmentioned;
				 break;
			 }
			}
			
			for (WordFeatureInfo word0 : ch.words_info) {
				if (word0.token.toLowerCase().contains(last_subpattern)) {
					index_of_lastmentioned = word0.token_index;
                     break; 
				}
			}
			
			for(WordFeatureInfo word1: ch.words_info)
			{    
		        if(word1.token_index==index_of_lastmentioned)
				{
		        	
				    ci.NNPOS=word1.POS_Tag;// to judge if the NN is singular or plural
				    ci.NNtoken_index=word1.token_index;//spatial distribution of the token, word
				
				           
			}
			}
			pm.EventChain.add(ci);
          }
		
}
}

   public void getEventInstance( ProblemModule pm)
   {
	   ArrayList<ConceptInfo> eventconcepts= pm.EventChain;
	   int distanceindex=0;
	   int intersectindex=0;
	   int perpendindex=0;
	   int parallelindex=0;
	   for(ConceptInfo temp_event:eventconcepts)
	   {
		   // get distance instance
		   if(temp_event.concept_code.equals("distance"))
		   {
			   DistanceRelationNode distance_instance= new DistanceRelationNode();
			   
			   temp_event.index=distanceindex;
			   
			   distance_instance.conceptinfo=temp_event;
			   distance_instance.conceptindex=distanceindex;
			   pm.ConceptInstances.DistanceInstance.add(distance_instance);
			   
			   distanceindex++;
			   
		   }
		   
		   if(temp_event.concept_code.equals("intersect"))
		   {
			  Intersect_relation intersect_instance=new Intersect_relation();
			  temp_event.index=intersectindex;			 
				intersect_instance.conceptinfo=temp_event;
				intersect_instance.index=intersectindex;
			    pm.ConceptInstances.intersect_relations.add(intersect_instance);
			    
			    intersectindex++;
			
		   }
		   
		   if(temp_event.concept_code.equals("perpendicular"))
		   {
			   PerpendicularRe perpendicular_instance=new PerpendicularRe();
			   temp_event.index=perpendindex;
			   perpendicular_instance.perpendInfo=temp_event;
			   perpendicular_instance.index=perpendindex;
			   pm.ConceptInstances.perpendi_relations.add(perpendicular_instance);
			   perpendindex++;
		   }
		   
		   if(temp_event.concept_code.equals("parallel"))
		   {
			   ParallelRelation parallelInstance= new ParallelRelation();
			   temp_event.index=parallelindex;
			   parallelInstance.paralellConceptinfo=temp_event;
			   parallelInstance.index=parallelindex;
			   pm.ConceptInstances.parallelRelations.add(parallelInstance);
			   parallelindex++;
		   }
	   }
	   
	   
	   
   }

}
