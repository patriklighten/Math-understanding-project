package com.mathapollo.parsing.merge;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import com.mathapollo.io.ProblemModule;
import com.mathapollo.knowledge.concept.Lineconcept;
import com.mathapollo.knowledge.concept.Pointconcept;

public class PrintLMC {
	
	// currently only involve line points, line slope, and line equation 
 public  void printlineconcept(Lineconcept lineinstance)
 {
       Lineconcept knownLineElement= new Lineconcept();// load unknown printing list 
       Lineconcept unknownLineElement= new Lineconcept();// load known printing list 
        ArrayList<String> pointUnknownVariable= new ArrayList<String>(); // for example, point(x,y), we will put the x and y in to the printing list separately   
 // all point is put in the knownlineelement
 // the unknown variable of the points will be put in the unknownlineelement 
     knownLineElement.points=lineinstance.points;
     for(Pointconcept pt1:knownLineElement.points)
     {
    	 if(!pt1.X.equals("known"))
    		 pointUnknownVariable.add(pt1.X.unknown_variable);
    	 if(!pt1.Y.equals("known"))
    		 pointUnknownVariable.add(pt1.Y.unknown_variable);
     }
    // putting the slop into the printing list
     if(lineinstance.slope.slopeValue.unknown_variable.equals("unknown"))
     {
    	unknownLineElement.slope=lineinstance.slope;
     }
	 // the problem may not mention the line slope, so that we must ensure that the slope is mentioned or not 
     else if(lineinstance.slope.slopeValue.unknown_variable.equals("known"))
     {
    	 knownLineElement.slope=lineinstance.slope;
     }
     
     if(lineinstance.standard_from.equation.equals("unknown"))
     {
    	 unknownLineElement.standard_from=lineinstance.standard_from;
     }
     else if(lineinstance.standard_from.mapingfeatureinfo!=null)
     {
    	 unknownLineElement.standard_from=lineinstance.standard_from;
     }
     // printing knownlist, only print point, slope and standard form 
     // print points 
       int i=0;
       for(Pointconcept pt2:knownLineElement.points)
       {
    	  System.out.println("point"+i+":" + pt2.mapingfeatureinfo.token);
    	  i++;
       }
       // print slope
      if(knownLineElement.slope.slopeValue.unknown_variable!=null)
      {
    	  System.out.println("slope:"+knownLineElement.slope.slopeValue.value);
    	  }
      
     // print standard form 
      if(knownLineElement.standard_from.unknown_variable!=null)
      {
    	  System.out.println("line standard form:"+knownLineElement.standard_from.equation);
      }
      // print unknown element 
      // print unknown points 
      for( String str:pointUnknownVariable)
      System.out.println(str);
      
      // print unknown slope
      if(unknownLineElement.slope.slopeValue.unknown_variable!=null)
      {
    	  System.out.println("slope ?");
      }
      
      // print standard form 
      if(unknownLineElement.standard_from.equation!=null)
      {
    	 System.out.println("line standard form?");
      }
      
      
 }

}
