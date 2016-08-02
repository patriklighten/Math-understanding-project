package com.mathapollo.parsing.InstatiateConcept;

import java.util.ArrayList;

import com.mathapollo.io.Chunkers;
import com.mathapollo.io.ConceptInfo;
import com.mathapollo.io.ConceptInstances;
import com.mathapollo.io.ProblemModule;
import com.mathapollo.io.WordFeatureInfo;
import com.mathapollo.knowledge.concept.Circleconcept;
import com.mathapollo.knowledge.concept.Equation;
import com.mathapollo.knowledge.concept.Lineconcept;
import com.mathapollo.knowledge.concept.Pointconcept;
import com.mathapollo.knowledge.concept.Slope;
import com.mathapollo.parsing.contextual.CDvalue;
import com.mathapollo.parsing.contextual.ComputeQuantity;
import com.mathapollo.parsing.contextual.MapValue;

import edu.illinois.cs.cogcomp.lbj.coref.learned.preModifierNamesCompatible;
import edu.illinois.cs.cogcomp.lbj.pos.wordForm;

public class InstantiateAll_Instance {
	public ArrayList<Circleconcept> CircleInstances;
	public ArrayList<Lineconcept>    LineInstances;
	public ArrayList<Pointconcept>   PointInstances;
	public  ArrayList<Equation>   EquationInstances;
	public MapValue mapvalue= new MapValue();
	public ComputeQuantity computequantity= new ComputeQuantity();
	
	public InstantiateAll_Instance()
	{
		
	}
	public InstantiateAll_Instance(ProblemModule pm) {
		
	// TODO Auto-generated constructor stub

  // instantialize concepts in an order according to the hierarchical structure
	
// instantiate all points and map them to the near by higher concept 
   loadPointsInstance(pm);
// instantiate all single value concepts, like slope, y- intercept, distance value
   loadSlopesInstance(pm);
// instantiate all equation, including kinds of forms 
	LoadEquationInstance(pm);
// instantiate all lines 
	
// instantiate all circles 

}
	
// load points instance, load them to line and circle 
 public void loadPointsInstance(ProblemModule pm)
 {
	 ComputeQuantity computequantity=new ComputeQuantity();
	 PointInstances=pm.ConceptInstances.PointInstances;
	 WordFeatureInfo temp_word;
	 for(Pointconcept pointInstant: PointInstances)
	 {
		 // instantiate the point with value 
		 
		 //load the point to a higher concept instance, like line, circle 
		 // find the nearest before concept 
		 temp_word=pm.FeatureChain.get(pointInstant.Point_ConceptInfo.NNtoken_index);
		 
		 ConceptInfo beforeconcept= computequantity.findnearestBeforeConcept(temp_word, pm);
		 ConceptInfo afterconcept=computequantity.findnearestBehindConcept(temp_word, pm);
				 
		 // find nearest before non point concept, if the nearest before class is point,skip
		 while(beforeconcept.concept_code!=null&&beforeconcept.concept_code.equals("point"))
		 {   
			 temp_word=new WordFeatureInfo();
			 temp_word=pm.FeatureChain.get(beforeconcept.NNtoken_index);
		     beforeconcept=new ConceptInfo();
		     beforeconcept=computequantity.findnearestBeforeConcept(temp_word, pm);
			 
		 }
		 //find nearest after non point concept 
		 while(afterconcept.concept_code!=null&&afterconcept.concept_code.equals("point"))
		 {   
			 temp_word=new WordFeatureInfo();
			 temp_word=pm.FeatureChain.get(afterconcept.NNtoken_index);
		     afterconcept=new ConceptInfo();
		     afterconcept=computequantity.findnearestBehindConcept(temp_word, pm);
			 
		 }
		 //if the before concept is same sentence with the behind one(we think they are mapping, this is a simple case), if no we will find the after concept instead 
		if(beforeconcept.chunk_info!=null)
		 if(beforeconcept.chunk_info.sentence_index==pointInstant.Point_ConceptInfo.chunk_info.sentence_index)
		 {   // if a line before the point
			 if(beforeconcept.concept_code.equals("line"))
			 {
				 // find the corresponding line instance 
				 for(Lineconcept line:pm.ConceptInstances.LineInstances)
				 {
					 if(line.Line_ConceptInfo.chunk_info.chunk_index==beforeconcept.chunk_info.chunk_index)
						 line.points.add(pointInstant);
				 }
			 }
			 
			// if a circle concept before the point
			 else if(beforeconcept.concept_code.equals("circle"))
			 {
				 // find the corresponding line instance 
				 for(Circleconcept circle:pm.ConceptInstances.CircleInstances)
				 {
					 if(circle.Circle_ConceptInfo.chunk_info.chunk_index==beforeconcept.chunk_info.chunk_index)
					 {
						 
						 circle.on_points.add(pointInstant);
					 }
				 }
			 }
			 
		 }
		 //else we find the after concept 
		 else if(afterconcept.chunk_info.sentence_index==pointInstant.Point_ConceptInfo.chunk_info.sentence_index)
		 {
			 if(afterconcept.concept_code.equals("line"))
			 {
				 // find the corresponding line instance 
				 for(Lineconcept line:pm.ConceptInstances.LineInstances)
				 {
					 if(line.Line_ConceptInfo.chunk_info.chunk_index==afterconcept.chunk_info.chunk_index)
						 line.points.add(pointInstant);
				 }
			 }
			 
			// if a circle concept before the point
			 else if(afterconcept.concept_code.equals("circle"))
			 {
				 // find the corresponding line instance 
				 for(Circleconcept circle:pm.ConceptInstances.CircleInstances)
				 {
					 if(circle.Circle_ConceptInfo.chunk_info.chunk_index==afterconcept.chunk_info.chunk_index)
					    circle.on_points.add(pointInstant);
				 }
			 }
		 }
		 
	 }
 }
 
  // if there is of between the loaded concept instance and higher instance 
public Boolean isOfBetween(WordFeatureInfo wordbefore, WordFeatureInfo wordbehind,ProblemModule pm)
{
    Boolean bool= false;
    for(WordFeatureInfo temp_word:pm.FeatureChain)
    {
    	if(temp_word.token.toLowerCase().equals("of"))
    		if(temp_word.token_index>wordbefore.token_index&&temp_word.token_index<wordbehind.token_index)
    		{
    			bool=true;
    			break;
    		}
    }
    
    return bool;
}

// load slope instance to possible line instance 
public void loadSlopesInstance(ProblemModule pm)
{
	// get slope list 
	ArrayList<Slope> SlopeList= pm.ConceptInstances.SlopeInstances;
	
	
	
	  for(Slope slop:SlopeList)
	  {
		  WordFeatureInfo temp_word=pm.FeatureChain.get(slop.slope_info.NNtoken_index);
	 ConceptInfo beforeconcept= computequantity.findnearestBeforeConcept(temp_word, pm);
	 ConceptInfo afterconcept=computequantity.findnearestBehindConcept(temp_word, pm);
			 
	 // find nearest before line concept, if the nearest before class is point,skip
	 while(!beforeconcept.concept_code.equals("line"))
	 {   
		 temp_word=new WordFeatureInfo(); 
		 temp_word=pm.FeatureChain.get(beforeconcept.NNtoken_index);
	     beforeconcept=new ConceptInfo();
	     beforeconcept=computequantity.findnearestBeforeConcept(temp_word, pm);
		 
	 }
	 
	 //find nearest after line concept 
	 while(!afterconcept.concept_code.equals("line"))
	 {   
		 temp_word=new WordFeatureInfo();
		 temp_word=pm.FeatureChain.get(afterconcept.NNtoken_index);
	     afterconcept=new ConceptInfo();
	     afterconcept=computequantity.findnearestBehindConcept(temp_word, pm);
		 
	 }
	 // may be the case of " line's slope"
	 
		 if(beforeconcept.chunk_info.sentence_index==slop.slope_info.chunk_info.sentence_index)
		 {
			 for(Lineconcept line:pm.ConceptInstances.LineInstances)
			 {
				 if(line.Line_ConceptInfo.chunk_info.chunk_index==beforeconcept.chunk_info.chunk_index)
				 {
					 line.slope=slop;
					 
					 
					 
				 }
			 }
		 }
		 else if(afterconcept.chunk_info.sentence_index==slop.slope_info.chunk_info.sentence_index)
		 {
			 for(Lineconcept line:pm.ConceptInstances.LineInstances)
			 {
				 if(line.Line_ConceptInfo.chunk_info.chunk_index==afterconcept.chunk_info.chunk_index)
				 {
					 line.slope=slop;
					 
				 }
			 }
		 }
	 
	 
	  }
}

// load Equation for line including standard form, general form, equation and so on 
public void LoadEquationInstance(ProblemModule pm)
{
	ArrayList<Equation> EquationList=pm.ConceptInstances.EquationInstances;
	for(Equation equation:EquationList)
	{
		WordFeatureInfo temp_word=pm.FeatureChain.get(equation.Equation_ConceptInfo.NNtoken_index);
		 ConceptInfo beforeconcept= computequantity.findnearestBeforeConcept(temp_word, pm);
		 ConceptInfo afterconcept=computequantity.findnearestBehindConcept(temp_word, pm);
		// find nearest before line concept, if the nearest before class is point,skip
		 while(beforeconcept.concept_code!=null&&!beforeconcept.concept_code.equals("line"))
		 {   
			 temp_word=new WordFeatureInfo(); 
			 temp_word=pm.FeatureChain.get(beforeconcept.NNtoken_index);
		     beforeconcept=new ConceptInfo();
		     beforeconcept=computequantity.findnearestBeforeConcept(temp_word, pm);
			 
		 }
		 
		 //find nearest after line concept 
		 
		   while(afterconcept.concept_code!=null&&!afterconcept.concept_code.equals("line"))
		 {   
			 temp_word=new WordFeatureInfo();
			 temp_word=pm.FeatureChain.get(afterconcept.NNtoken_index);
		     afterconcept=new ConceptInfo();
		     afterconcept=computequantity.findnearestBehindConcept(temp_word, pm);
			 
		 }
		 
		 if(beforeconcept.chunk_info!=null&&beforeconcept.chunk_info.sentence_index==equation.Equation_ConceptInfo.chunk_info.sentence_index)
		 {
			 for(Lineconcept line:pm.ConceptInstances.LineInstances)
			 {
				 if(line.Line_ConceptInfo.chunk_info.chunk_index==beforeconcept.chunk_info.chunk_index)
				 {
					 // load line standard form
					 if(equation.Equation_ConceptInfo.concept_code.equals("standard form"))
					 {
						 line.standard_from=equation;
					 }
					 // load line general form
					 else if(equation.Equation_ConceptInfo.concept_code.equals("general form"))
					 {
						 line.generalform=equation;
					 }
					 
				 }
			 }
		 }
		 else if(afterconcept.chunk_info!=null&&afterconcept.chunk_info.sentence_index==equation.Equation_ConceptInfo.chunk_info.sentence_index)
		 {
			 for(Lineconcept line:pm.ConceptInstances.LineInstances)
			 {
				 if(line.Line_ConceptInfo.chunk_info.chunk_index==afterconcept.chunk_info.chunk_index)
				 {
					 // load line standard form
					 if(equation.Equation_ConceptInfo.concept_code.equals("standard form"))
					 {
						 line.standard_from=equation;
					 }
					 // load line general form
					 else if(equation.Equation_ConceptInfo.concept_code.equals("general form"))
					 {
						 line.generalform=equation;
					 }
					 
				 }
			 }
		 }
		 
	}
	
}





}
