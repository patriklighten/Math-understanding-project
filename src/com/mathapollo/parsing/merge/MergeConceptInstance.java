package com.mathapollo.parsing.merge;

import java.util.ArrayList;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.type.NullType;

import com.mathapollo.io.WordFeatureInfo;
import com.mathapollo.knowledge.concept.Equation;
import com.mathapollo.knowledge.concept.Lineconcept;
import com.mathapollo.knowledge.concept.Pointconcept;
import com.mathapollo.knowledge.concept.Slope;

public class MergeConceptInstance {

	// merge start from atomic concept 
	//merge automic  before composing the concept 
	// after that merge the higher concept 
	
	
   /* merge atomic concept*/
	// merge point instance 
	
	public Pointconcept mergePoint(Pointconcept pt1, Pointconcept pt2)
	{
		Pointconcept pointinstance= new Pointconcept();
		if(pt1.mapingfeatureinfo!=null)
		{
			pointinstance.mapingfeatureinfo=pt1.mapingfeatureinfo;
		}
		else
		pointinstance.mapingfeatureinfo=pt2.mapingfeatureinfo;
		
		if(pt1.Point_ConceptInfo!=null)
			pointinstance.Point_ConceptInfo=pt1.Point_ConceptInfo;
		else 
			pointinstance.Point_ConceptInfo=pt2.Point_ConceptInfo;
		if(pt1.X!=null)
			pointinstance.X=pt1.X;
		else
			pointinstance.Y=pt2.X;
		if(pt1.Y!=null)
            pointinstance.Y=pt2.Y;
	  return pointinstance;
	}
	// merge slope
	public Slope mergeSlope(Slope slp1, Slope slp2)
	{
	     Slope slp= new Slope();
	     if(slp1.slopeValue!=null)
	    	 slp.slopeValue=slp1.slopeValue;
	     else
	        slp.slopeValue= slp2.slopeValue;
	     if(slp1.slope_info!=null)
	    	 slp.slope_info=slp1.slope_info;
	     else 
	    	 slp.slope_info=slp2.slope_info;
	    return slp;
	     
	}
	
	// merge equation 
	
	public  Equation mergeEquation(Equation eq1, Equation eq2)
	{
	    Equation eq= new Equation();
	    if(eq1.equation!=null)
	        	eq.equation=eq1.equation;
	    else 
	    	eq.equation=eq2.equation;
	    if(eq1.Equation_ConceptInfo!=null)
	        eq.Equation_ConceptInfo=eq1.Equation_ConceptInfo;
	    else
	    	eq.Equation_ConceptInfo=eq2.Equation_ConceptInfo;
	    if(eq1.unknown_variable!=null)
	    	eq.unknown_variable=eq1.unknown_variable;
	    else
	    	eq.unknown_variable=eq2.unknown_variable;
	    if(eq1.mapingfeatureinfo!=null)
	    	eq.mapingfeatureinfo=eq1.mapingfeatureinfo;
	    else 
	    	eq.mapingfeatureinfo=eq2.mapingfeatureinfo;
	    return eq;
	}
	
	public Lineconcept mergeLineConcept(Lineconcept line1, Lineconcept line2)
	{
	      Lineconcept lineinstance= new Lineconcept();
	      if(line1.equation.Equation_ConceptInfo!=null)
	    	 lineinstance.equation=line1.equation; 
	      else
	    	 lineinstance.equation=line2.equation;
	      if(line1.standard_from.equation!=null)
	    	  lineinstance.standard_from=line1.standard_from;
	      else
	    	  lineinstance.standard_from=line2.standard_from;
	      if(line1.Line_ConceptInfo!=null)
	    	  lineinstance.Line_ConceptInfo=line1.Line_ConceptInfo;
	      else 
	    	  lineinstance.Line_ConceptInfo=line2.Line_ConceptInfo;
	      
	      // each line has different point instance because of merging the points before load the point to line( compose the point)
	         lineinstance.points.addAll(line1.points);
	         lineinstance.points.removeAll(line2.points);
	         ArrayList<Pointconcept> temp_points=line2.points;
	         temp_points.addAll(lineinstance.points);
	          lineinstance.points=new ArrayList<Pointconcept>();
	           lineinstance.points=temp_points;
	       if(line1.slope.slopeValue.unknown_variable!=null)
	          lineinstance.slope=line1.slope;
	       else 
	    	  lineinstance.slope=line2.slope;
	       
	       
	     return lineinstance; 
	}
}
