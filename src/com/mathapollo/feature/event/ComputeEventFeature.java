package com.mathapollo.feature.event;

import java.util.ArrayList;
import java.util.List;

import javax.script.SimpleScriptContext;

import org.stringtemplate.v4.compiler.CodeGenerator.includeExpr_return;

import com.mathapollo.feature.computeFeature;
import com.mathapollo.feature.conceptpairs;
import com.mathapollo.feature.compose.ComposeFeature;
import com.mathapollo.feature.event.distance.composeFeatureofDistance;
import com.mathapollo.feature.event.intersect.ElementFeatureOfIntersect;
import com.mathapollo.feature.event.parallel.FeatureOfParallel;
import com.mathapollo.feature.event.perpendicular.FeatureOfPerpendicular;
import com.mathapollo.io.ConceptInfo;
import com.mathapollo.io.ProblemModule;
import com.mathapollo.io.WordFeatureInfo;
import com.mathapollo.knowledge.concept.Circleconcept;
import com.mathapollo.knowledge.concept.Lineconcept;
import com.mathapollo.knowledge.concept.Pointconcept;
import com.mathapollo.parsing.EventandRelation.DistanceRelationNode;
import com.mathapollo.parsing.EventandRelation.PerpendicularRe;

import LBJ2.parse.LineByLine;

public class ComputeEventFeature 
{
               
	public computeFeature computecomposefeature = new computeFeature();
	public ComposeFeature composefeature= new ComposeFeature();
	// compute the feature for distance instance 
    public composeFeatureofDistance getdistancesFeature(ProblemModule pm, ConceptInfo distanceconcept, ConceptInfo concept1, ConceptInfo concept2)
     {
    	 int returnflag=0;
    	
    	   composeFeatureofDistance distancefeatures= new composeFeatureofDistance(); 
    	   // because some feature of general compose feature is similar with that of the compose distance feature, we also compute these features 
    	 computeFeature computecomposefeature = new computeFeature();
    		 ComposeFeature composefeature= new ComposeFeature();
        	
    		 // compute the general compute feature 
    		 composefeature=computecomposefeature.composefeaturecomputing(pm, concept1, concept2);
    		
    		 // compute the joint alignment feature, <concept1,concept2>, <distanceconcept,concept1,concept2> 
    		  distancefeatures.distanceconceptinfo=distanceconcept;// pairs
    		  distancefeatures.conceptpairinfo.concept1=concept1;
    		  distancefeatures.conceptpairinfo.concept2=concept2;
    		  distancefeatures.distance_inchunker=Math.abs(composefeature.distance_inchunker); // concept pair feature 
    		  distancefeatures.if_samesentence=if_threeconceptInSameSentence(distanceconcept, concept1, concept2);//joint feature
    	      distancefeatures.If_sametypeconceptA_between=composefeature.If_sametypeconceptA_between;// concept pair feature 
    		  distancefeatures.if_sametypeConceptB_between=composefeature.if_sametypeConceptB_between;// concept pair feature 
    		  distancefeatures.if_verb_between=composefeature.ifsplitByVerb; // concept pair feature 
              distancefeatures.distancefromconceptAtoDistance=computeRelativeDistance(pm, distanceconcept, concept1);//joint feature 
              distancefeatures.distancefromconceptBtoDistance=computeRelativeDistance(pm, distanceconcept, concept2);//joint feature
              distancefeatures.if_anotherDistancebetween=0;//joint feature
             /* 
              if(concept1.NNtoken_index<concept2.NNtoken_index)
    		 {
    			 if(if_same_conceptbetween(pm, distanceconcept, concept2)==1)
    			 distancefeatures.if_anotherDistancebetween=1;
    			 else
    				 distancefeatures.if_anotherDistancebetween=0;
    		 }
    		 else
    		 {
    			 if(if_same_conceptbetween(pm, distanceconcept, concept1)==1)
    				 distancefeatures.if_anotherDistancebetween=1;
    			 else
    				 distancefeatures.if_anotherDistancebetween=0;
    		 }
              */
              
              int[]flag=if_same_conceptbetween(pm, distanceconcept, concept1);
              int[]flag1=if_same_conceptbetween(pm, distanceconcept, concept2);
              if(flag[0]==1||flag1[0]==1)
            	  distancefeatures.if_anotherDistancebetween=1;
              else
            	  distancefeatures.if_anotherDistancebetween=0;
              
              
    		  returnflag= if_ToAnd_between(pm, concept1, concept2);
    		  if(returnflag==0)
    		  {
    			  distancefeatures.ifsplitByAnd=0;
    			  distancefeatures.ifsplitByTo=0;
    		  }
    		  else if(returnflag==1)
    		  {
    			//  distancefeatures.ifsplitByAnd=0;
    			  distancefeatures.ifsplitByTo=1;
    		  }
    		  else if(returnflag==2){
    			  distancefeatures.ifsplitByAnd=1;
    			//  distancefeatures.ifsplitByTo=0;
			}
    		  else if(returnflag==3)
    		  {
    			  distancefeatures.ifsplitByAnd=1;
    			  distancefeatures.ifsplitByTo=1;
    		  }
    		  
    		 // returnflag= if_ToAnd_between(pm, concept1, concept2);
    			  
    		  
    		  distancefeatures.if_they_afterBetween=if_they_after_between(pm, concept1, concept2);
    
    		  if(concept1.NNtoken_index>distanceconcept.NNtoken_index&&concept2.NNtoken_index>distanceconcept.NNtoken_index)
    		  {
    			  distancefeatures.if_theyBothAfterDistance=1;
    		  }
    		  else
    			  distancefeatures.if_theyBothAfterDistance=0;
    		                                                                           
    		  return distancefeatures;
     }
    
    public ElementFeatureOfIntersect  computeintersectfeature(ProblemModule pm, ConceptInfo intersectinfo, ConceptInfo concept1, ConceptInfo concept2)
    {
    	ElementFeatureOfIntersect  intersectelementFeature= new ElementFeatureOfIntersect ();
    	 computeFeature computecomposefeature = new computeFeature();
		 ComposeFeature composefeature= new ComposeFeature();
    	
		 // compute the general compute feature 
		 composefeature=computecomposefeature.composefeaturecomputing(pm, concept1, concept2);
		 
		// compute the joint alignment feature, <concept1,concept2>, <distanceconcept,concept1,concept2> 


	intersectelementFeature.intersectinfo=intersectinfo; 
	 // intersect object concept pair 
	intersectelementFeature.conceptpairinfo.concept1=concept1;
	intersectelementFeature.conceptpairinfo.concept2=concept2;
	intersectelementFeature.distance_inchunker=composefeature.distance_inchunker;
	intersectelementFeature.if_verb_between=composefeature.ifsplitByVerb;
	intersectelementFeature.if_samesentence=0;   
	if(concept1.chunk_info.sentence_index==intersectinfo.chunk_info.sentence_index&&concept2.chunk_info.sentence_index==intersectinfo.chunk_info.sentence_index)
	{
	         intersectelementFeature.if_samesentence=1;      	     
	}
	
	intersectelementFeature.If_sametypeconceptA_between=composefeature.If_sametypeconceptA_between;
	intersectelementFeature.if_sametypeConceptB_between=composefeature.if_sametypeConceptB_between;
	
	intersectelementFeature.distancefromconceptAtointersect=Math.abs(computeRelativeDistance(pm, concept1, intersectinfo));
	intersectelementFeature.distancefromconceptBtointersect=Math.abs(computeRelativeDistance(pm, concept2, intersectinfo));
   
	intersectelementFeature.if_anotherIntersectbetween=0;
	int[] flag=if_same_conceptbetween(pm, intersectinfo, concept1);
	int[] flag1=if_same_conceptbetween(pm, intersectinfo, concept2);
    if(flag[0]==1||flag1[0]==1)
    {    
    	intersectelementFeature.if_anotherIntersectbetween=1;
    }
    else 
    	intersectelementFeature.if_anotherIntersectbetween=0;
		 
    	return intersectelementFeature;
    }
    
    public FeatureOfPerpendicular computePerpendicularFeature(ProblemModule pm, ConceptInfo perpendicularinfo, ConceptInfo concept1, ConceptInfo concept2)
    {
    	FeatureOfPerpendicular alignfeature = new FeatureOfPerpendicular();
    	
    	// because the distancefeatures share many features with the perpendicular, so we compute it 
    	 composeFeatureofDistance distancefeatures= new composeFeatureofDistance(); 
    	   ConceptInfo conceptA=new ConceptInfo();
    	   ConceptInfo conceptB=new ConceptInfo();
    	 if(concept1.global_index>concept2.global_index)
    	 {
    		 conceptA=concept2;
    		 conceptB=concept1;
    	 }
    	 else
    	 {
    		 conceptA=concept1;
    		 conceptB=concept2;
    	 }
		 // compute the general compute feature                
		 distancefeatures=getdistancesFeature(pm,perpendicularinfo,concept1,concept2);
    	 alignfeature.perpendicularinfo=perpendicularinfo;
    	 alignfeature.Linepairinfo.concept1=concept1;
    	 alignfeature.Linepairinfo.concept2=concept2;
    	  
    	 alignfeature.if_samesentence=distancefeatures.if_samesentence;
    	 alignfeature.If_anotherlinebetween=distancefeatures.If_sametypeconceptA_between;
    	 alignfeature.distance_inchunker=distancefeatures.distance_inchunker;
    	 alignfeature.distancefromconceptAtoPerpend=Math.abs(distancefeatures.distancefromconceptAtoDistance);
    	 alignfeature.distancefromconceptBtoPerpend=Math.abs(distancefeatures.distancefromconceptBtoDistance);
    	 alignfeature.ifsplitByAnd=distancefeatures.ifsplitByAnd;
    	 alignfeature.ifsplitByTo=distancefeatures.ifsplitByTo;
    	 
    	 if(if_ToAnd_between(pm, perpendicularinfo, conceptB)==1)
    		 alignfeature.ifsplitByTo=1;
    	 else
    		 alignfeature.ifsplitByTo=0;
    	 
    	 alignfeature.if_perpendicular_between=if_certain_conceptbetween(pm, perpendicularinfo, concept1, concept2);
    	 return alignfeature;
    }
    
    public FeatureOfParallel computeParallelFeature(ProblemModule pm,ConceptInfo parallelinfo,ConceptInfo concept1,ConceptInfo concept2)
    {                                                                
       FeatureOfParallel alignfeature = new FeatureOfParallel();                                              
       FeatureOfPerpendicular perpendFeature = computePerpendicularFeature(pm,parallelinfo,concept1,concept2);
       
       alignfeature.parallelinfo=parallelinfo;
       alignfeature.Linepairinfo.concept1=concept1;
       alignfeature.Linepairinfo.concept2=concept2;
                                                   
       alignfeature.distance_inchunker=perpendFeature.distance_inchunker;
       alignfeature.if_samesentence=perpendFeature.if_samesentence;        
       alignfeature.distancefromconceptAtoParallel=perpendFeature.distancefromconceptAtoPerpend;
       alignfeature.distancefromconceptBtoParallel=perpendFeature.distancefromconceptBtoPerpend;
       alignfeature.If_anotherlinebetween=perpendFeature.If_anotherlinebetween;
       alignfeature.ifsplitByAnd=perpendFeature.ifsplitByAnd;
       alignfeature.ifsplitByTo=perpendFeature.ifsplitByTo;
       alignfeature.if_parallel_between=perpendFeature.if_perpendicular_between;
       
       return alignfeature;
       
    	
    }
    
    public ArrayList<conceptpairs> getConceptPairof_points(ProblemModule pm)
    {
    	ArrayList<Pointconcept> pointlist=pm.ConceptInstances.PointInstances;
    	ArrayList<Lineconcept> lineList=pm.ConceptInstances.LineInstances; 
    	ArrayList<conceptpairs> conceptpairof_points= new ArrayList<conceptpairs>();
    	
    	//ArrayList<distance_ElementPairs> distanceElementPair= new ArrayList<distance_ElementPairs>();
    	conceptpairs temp_conceptpair= new conceptpairs();
    	for(int i=0;i<pointlist.size();i++)
    	{
    		for(int j=i+1;j<pointlist.size();j++)
    		{
    		          
    		   temp_conceptpair.concept1=pointlist.get(i).Point_ConceptInfo;
    		   temp_conceptpair.concept2=pointlist.get(j).Point_ConceptInfo;
    		   conceptpairof_points.add(temp_conceptpair);
    		   temp_conceptpair=new conceptpairs();         
    		}
    	}
    	
    	return conceptpairof_points;
    	
    	
    }
    // Hybrid pairsofpoints include the point's value or point's tag
    public ArrayList<conceptpairs> GetHybridPairof_points(ProblemModule pm)
    {
    	//ArrayList<Pointconcept> pointlist=pm.ConceptInstances.PointInstances;
    	ArrayList<ConceptInfo> pointlist= new ArrayList<ConceptInfo>(); 
    	
    	for(Pointconcept conceptinstance:pm.ConceptInstances.PointInstances)
    	{
    		pointlist.add(conceptinstance.Point_ConceptInfo);
    	}
    	
    	 for(ConceptInfo concept: pm.HybridConceptChain)
    	 {
    		if(concept.concept_code.equals("point_tag")||concept.concept_code.equals("point_value"))
    		{
    			pointlist.add(concept);
    			
    			
    		}
    	 }
    		 
    	//ArrayList<Lineconcept> lineList=pm.ConceptInstances.LineInstances; 
    	
    	ArrayList<conceptpairs> conceptpairof_points= new ArrayList<conceptpairs>();
    	
    	//ArrayList<distance_ElementPairs> distanceElementPair= new ArrayList<distance_ElementPairs>();
    	conceptpairs temp_conceptpair= new conceptpairs();
    	for(int i=0;i<pointlist.size();i++)
    	{
    		for(int j=i+1;j<pointlist.size();j++)
    		{
    		  
    		   temp_conceptpair.concept1=pointlist.get(i);
    		   temp_conceptpair.concept2=pointlist.get(j);
    		   conceptpairof_points.add(temp_conceptpair);
    		   temp_conceptpair=new conceptpairs();         
    		}
    	}
    	
    	
    	
    	return conceptpairof_points;
    	
    	
    }
    
    public ArrayList<conceptpairs> getConceptPairof_lines(ProblemModule pm)
    {
    	ArrayList<Lineconcept> lineList=pm.ConceptInstances.LineInstances; 
    	ArrayList<conceptpairs> linepair= new ArrayList<conceptpairs>();
    	conceptpairs temp_conceptpair= new conceptpairs();
    	for(int i=0;i<lineList.size();i++)
    	{
    		for(int j=i+1;j<lineList.size();j++)
    		{
    		  
    		   temp_conceptpair.concept1=lineList.get(i).Line_ConceptInfo;
    		   temp_conceptpair.concept2=lineList.get(j).Line_ConceptInfo;
    		   linepair.add(temp_conceptpair);
    		   temp_conceptpair=new conceptpairs();
    		}
    	}
    	
    	return linepair;
    }
    
    public ArrayList<conceptpairs> GethHybridPairof_lines(ProblemModule pm)
    {
    	//ArrayList<Lineconcept> lineList=pm.ConceptInstances.LineInstances; 
    	
    	//
    	ArrayList<ConceptInfo> lineList= new ArrayList<ConceptInfo>();
    	for(Lineconcept conceptinstance:pm.ConceptInstances.LineInstances)
    	{
    		lineList.add(conceptinstance.Line_ConceptInfo);
    	}
    	
    	// add the hybridconcepts 
    	for(ConceptInfo concept: pm.HybridConceptChain)
    	{
    		if(concept.concept_code.equals("line_tag")||concept.concept_code.equals("line_value"))
    		{
    		   
    		    lineList.add(concept);
    		}
    	}
    	
    	ArrayList<conceptpairs> linepair= new ArrayList<conceptpairs>();
    	conceptpairs temp_conceptpair= new conceptpairs();
    	for(int i=0;i<lineList.size();i++)
    	{
    		for(int j=i+1;j<lineList.size();j++)
    		{
    		  
    		   temp_conceptpair.concept1=lineList.get(i);
    		   temp_conceptpair.concept2=lineList.get(j);
    		   linepair.add(temp_conceptpair);
    		   temp_conceptpair=new conceptpairs();
    		}
    	}
    	
    	return linepair;
    }
    public ArrayList<conceptpairs> getLineCirclePair(ProblemModule pm)
    {
    	ArrayList<Lineconcept> lineList=pm.ConceptInstances.LineInstances; 
    	
    	ArrayList<Circleconcept> circlelist=pm.ConceptInstances.CircleInstances;
    	
    	
    	ArrayList<conceptpairs> LineCirclePair= new ArrayList<conceptpairs>();
    	conceptpairs temp_conceptpair= new conceptpairs();
    	for(int i=0;i<lineList.size();i++)
    		for(int j=0;j<circlelist.size();j++)
    		{
    			temp_conceptpair.concept1=lineList.get(i).Line_ConceptInfo;
    			temp_conceptpair.concept2=circlelist.get(j).Circle_ConceptInfo;
    			LineCirclePair.add(temp_conceptpair);
    			temp_conceptpair=new conceptpairs();
    		}
    	
    	return LineCirclePair;
    	
    }
    
    public ArrayList<conceptpairs> GetHybridLineCirclePair(ProblemModule pm)
    {
    	//ArrayList<Lineconcept> lineList=pm.ConceptInstances.LineInstances; 
    	ArrayList<ConceptInfo> lineList= new ArrayList<ConceptInfo>();
    	
    	for(Lineconcept conceptinstance:pm.ConceptInstances.LineInstances)
    	{
    		lineList.add(conceptinstance.Line_ConceptInfo);
    	}
    	
    	// add the hybridconcepts 
    	for(ConceptInfo concept: pm.HybridConceptChain)
    	{
    		if(concept.concept_code.equals("line_tag")||concept.concept_code.equals("line_value"))
    		{
    		   
    		    lineList.add(concept);
    		}
    	}
    	
    	ArrayList<ConceptInfo> circlelist= new ArrayList<ConceptInfo>();
    	
        for(Circleconcept conceptinstance: pm.ConceptInstances.CircleInstances)
        {
        	circlelist.add(conceptinstance.Circle_ConceptInfo);
        }
    	
    	for(ConceptInfo concept: pm.HybridConceptChain)
    	{
    		if(concept.concept_code.equals("circle_tag")||concept.concept_code.equals("circle_value"))
    		{
    		    
    		    circlelist.add(concept);
    		}
    	}
    	ArrayList<conceptpairs> LineCirclePair= new ArrayList<conceptpairs>();
    	conceptpairs temp_conceptpair= new conceptpairs();
    	for(int i=0;i<lineList.size();i++)
    		for(int j=0;j<circlelist.size();j++)
    		{
    			temp_conceptpair.concept1=lineList.get(i);
    			temp_conceptpair.concept2=circlelist.get(j);
    			LineCirclePair.add(temp_conceptpair);
    			temp_conceptpair=new conceptpairs();
    		}
    	
    	return LineCirclePair;
    	
    }
    // concept1 is point, concept 2 is line
    public ArrayList<conceptpairs> getConceptPairof_linepoint(ProblemModule pm)
    {
    	ArrayList<Pointconcept> pointlist=pm.ConceptInstances.PointInstances;
    	ArrayList<Lineconcept> lineList=pm.ConceptInstances.LineInstances; 
    	
    	ArrayList<conceptpairs> conceptpairof_linepoint= new ArrayList<conceptpairs>();
    	//ArrayList<distance_ElementPairs> distanceElementPair= new ArrayList<distance_ElementPairs>();
    	conceptpairs temp_conceptpair= new conceptpairs();
    	
    	for(int i=0;i<pointlist.size();i++)
    	{
    		for(int j=0;j<lineList.size();j++)
    		{
    			temp_conceptpair.concept1=pointlist.get(i).Point_ConceptInfo;
    			temp_conceptpair.concept2=lineList.get(j).Line_ConceptInfo;
    		    conceptpairof_linepoint.add(temp_conceptpair);
    		    temp_conceptpair=new conceptpairs();
    		}
    	}
    	
    	return conceptpairof_linepoint;
    }
    
    public ArrayList<conceptpairs> GetHypridPairof_linepoint(ProblemModule pm)
    {
    	//ArrayList<Pointconcept> pointlist=pm.ConceptInstances.PointInstances;
    	ArrayList<ConceptInfo> pointlist= new ArrayList<ConceptInfo>();
    	for(Pointconcept conceptinstance: pm.ConceptInstances.PointInstances)
    	{
    		pointlist.add(conceptinstance.Point_ConceptInfo);
    		
    	}
    	for(ConceptInfo concept: pm.HybridConceptChain)
    	 {
    		if(concept.concept_code.equals("point_tag")||concept.concept_code.equals("point_value"))
    		{
    			
    			pointlist.add(concept);
    			
    			
    		}
    	 }
    	
    //	ArrayList<Lineconcept> lineList=pm.ConceptInstances.LineInstances; 
    	ArrayList<ConceptInfo> lineList= new ArrayList<ConceptInfo>();
        
    	for(Lineconcept conceptinstance:pm.ConceptInstances.LineInstances)
    	{
    		lineList.add(conceptinstance.Line_ConceptInfo);
    		
    	}
    	for(ConceptInfo concept: pm.HybridConceptChain)
    	{
    		if(concept.concept_code.equals("line_tag")||concept.concept_code.equals("line_value"))
    		{
    		   
    		    lineList.add(concept);
    		}
    	}
    	ArrayList<conceptpairs> conceptpairof_linepoint= new ArrayList<conceptpairs>();
    	//ArrayList<distance_ElementPairs> distanceElementPair= new ArrayList<distance_ElementPairs>();
    	conceptpairs temp_conceptpair= new conceptpairs();
    	
    	for(int i=0;i<pointlist.size();i++)
    	{
    		for(int j=0;j<lineList.size();j++)
    		{
    			temp_conceptpair.concept1=pointlist.get(i);
    			temp_conceptpair.concept2=lineList.get(j);
    		    conceptpairof_linepoint.add(temp_conceptpair);
    		    temp_conceptpair=new conceptpairs();
    		}
    	}
    	
    	return conceptpairof_linepoint;
    }
    // if there is word "to" between, return 1, if there is word "and" between, return 2, otherwise return 0; 
    public int if_ToAnd_between(ProblemModule pm, ConceptInfo concept1,ConceptInfo concept2)
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
   	for(int i=index1;i<index2;i++)
   	{
   	  if(pm.FeatureChain.get(i).token.toLowerCase().equals("to"))
   	  {
   		 flag1=1;
   		 //break;
   	  }
   	 if(pm.FeatureChain.get(i).token.toLowerCase().equals("and"))
  	  {
  		 flag2=2;
  		// break;
  	  }
   	}
   if(flag1==0)
	   return flag2;
   else if(flag2==0)
	   return flag1;
   else if(flag1+flag2==3)
   	return flag1+flag2;
   else
   return flag1;
    }
     
    public int if_they_after_between(ProblemModule pm, ConceptInfo concept1,ConceptInfo concept2)
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
       	
       	for(int i=0;i<index1;i++)
       	{
       		WordFeatureInfo temp_word= pm.FeatureChain.get(i);
       	   // if two concept are after the word "between" which in the same sentence with them 
       	   if(temp_word.token.toLowerCase().equals("between")&&temp_word.sentence_index==concept1.chunk_info.sentence_index&&concept1.chunk_info.sentence_index==concept2.chunk_info.sentence_index)
       	   {
       		   flag=1;
       		   break;
       	   }
       	}
       	
       	return flag;
    }
    
    public double computeRelativeDistance(ProblemModule pm, ConceptInfo conceptA, ConceptInfo conceptB)
    {
         //  nearer the two concepts to each other, the bigger the distance
    	// the largest distance value is 1;
    	double relative_distance_inchunk=1; 
    	     if(Math.abs(conceptB.global_index2-conceptA.global_index2)>=1)
        	   {
    	    	 double d1=conceptB.global_index2-conceptA.global_index2;
        	   
        	   double d0=1;
        	   relative_distance_inchunk= d0/d1;
        	   }
    	     else
    	     {
    	    	 if(conceptB.NNtoken_index<conceptA.NNtoken_index)
    	    		 relative_distance_inchunk=-1;
    	     }
           return Math.abs(relative_distance_inchunk);
    }
    public int if_threeconceptInSameSentence(ConceptInfo concept1, ConceptInfo concept2,ConceptInfo concept3)
    {
    	int flag=0; 
    	if(concept1.chunk_info.sentence_index==concept2.chunk_info.sentence_index&&concept2.chunk_info.sentence_index==concept3.chunk_info.sentence_index)
    	{
    		flag=1;
    	}
    	
    	return flag;
    }
    
    public int[] if_same_conceptbetween(ProblemModule pm, ConceptInfo concept1, ConceptInfo concept2)
    {
        int[] flag= new int[2];
        flag[0]=0;
        flag[1]=0;
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
        	//if(concept))
        	
             if(concept.NNtoken_index<index2&&concept.NNtoken_index>index1)
             {
            if(concept.concept_code.equals(concept2.concept_code))
            {
            	flag[1]=1;
            	
            }
            else if(concept.concept_code.equals(concept1.concept_code))
            {
            	flag[0]=1; 
            	
            }
            
             }
        }
        
    	return flag;
        
    }

    
    public int if_certain_conceptbetween(ProblemModule pm, ConceptInfo Certainconcept,ConceptInfo concept1, ConceptInfo concept2)
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
        
    	if((Certainconcept.global_index2-concept1.global_index2)*(Certainconcept.global_index2-concept2.global_index2)<0)
    		flag=1;
        
    	return flag;
        
    }
}

