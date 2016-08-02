package com.mathapollo.feature;
import java.awt.Point;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.tree.PositionTrackingStream;
import org.junit.validator.PublicClassValidator;

import com.mathapollo.feature.compose.ComposePairsOfCircle;
import com.mathapollo.feature.compose.ComposePairsOfLine;
import com.mathapollo.io.*;
import com.mathapollo.knowledge.concept.Circleconcept;
import com.mathapollo.knowledge.concept.Equation;
import com.mathapollo.knowledge.concept.Lineconcept;
import com.mathapollo.knowledge.concept.Pointconcept;
import com.mathapollo.knowledge.concept.Radiusconcept;
import com.mathapollo.knowledge.concept.Slope;

import edu.illinois.cs.cogcomp.lbj.coref.learned.introLabel;

// first kind functioin: "Compose Point", "Compose Line", "Compose Circle" 
// second kind of function: " Compose event" : intersect, perpendicular, distance between, parallel 
// Third kind function:"Merge Geometry primitive", "Merge Event" 
public class MachingConcepts {
	
  // public ArrayList<ConceptInfo>
	// compose pair of line: 
	
	public conceptpairs[][]Points_lines(ProblemModule pm)
	{
		 ArrayList<Lineconcept> Linelist= pm.ConceptInstances.LineInstances;
		  ArrayList<Pointconcept> OnPointlist=pm.ConceptInstances.PointInstances;
		
		
		  conceptpairs[][]ComposePointOfLine = new conceptpairs[OnPointlist.size()][Linelist.size()];
		  for(int j=0;j<Linelist.size();j++)
		     {
		    	 int i=0;
		    	ConceptInfo temp_line=Linelist.get(j).Line_ConceptInfo;
		    	
		    	int r=0;
		     for(r=0;r<OnPointlist.size();r++)
		     {
		    	 ComposePointOfLine[i+r][j]=new conceptpairs();
		    	 ComposePointOfLine[i+r][j].concept1=new ConceptInfo();
		    	 ComposePointOfLine[i+r][j].concept1=OnPointlist.get(r).Point_ConceptInfo;
		    	 ComposePointOfLine[i+r][j].concept2=temp_line;  
		     }
		     
		     }
		  return ComposePointOfLine;
		  
	}
	
	public conceptpairs[][]Slope_lines(ProblemModule pm)
	{
		 ArrayList<Lineconcept> Linelist= pm.ConceptInstances.LineInstances;
		  ArrayList<Slope> slopelist=pm.ConceptInstances.SlopeInstances;
		
		  conceptpairs[][]ComposeOfLine = new conceptpairs[slopelist.size()][Linelist.size()];
		  for(int j=0;j<Linelist.size();j++)
		     {
		    	 int i=0;
		    	ConceptInfo temp_line=Linelist.get(j).Line_ConceptInfo;
		    	
		    	int r=0;
		     for(r=0;r<slopelist.size();r++)
		     {
		    	 ComposeOfLine[i+r][j]=new conceptpairs();
		    	 ComposeOfLine[i+r][j].concept1=slopelist.get(r).slope_info;
		    	 ComposeOfLine[i+r][j].concept2=temp_line;  
		     }
		     
		     }
		  return ComposeOfLine;
		  
	}
	
	
	public conceptpairs[][]Equation_lines(ProblemModule pm)
	{
		 ArrayList<Lineconcept> Linelist= pm.ConceptInstances.LineInstances;
		  ArrayList<Equation> equationlist=pm.ConceptInstances.EquationInstances;
		
		  conceptpairs[][]ComposeOfLine = new conceptpairs[equationlist.size()][Linelist.size()];
		  for(int j=0;j<Linelist.size();j++)
		     {
		    	 int i=0;
		    	ConceptInfo temp_line=Linelist.get(j).Line_ConceptInfo;
		    	
		    	int r=0;
		     for(r=0;r<equationlist.size();r++)
		     {
		    	 ComposeOfLine[i+r][j]=new conceptpairs();
		    	 ComposeOfLine[i+r][j].concept1=equationlist.get(r).Equation_ConceptInfo;
		    	 ComposeOfLine[i+r][j].concept2=temp_line;  
		     }
		     
		     }
		  return ComposeOfLine;
		  
	}
	
	// X-intercept of Line 
	//TODO: 
	
	//y-intercept of Line 
	//TODO:
	
   
	// Integrate all the compose elements of lines 
	
	public ComposePairsOfLine ComposeOfLines(ProblemModule pm)
	{    
		ComposePairsOfLine composeoflines=new ComposePairsOfLine();
	    composeoflines.Points_lines=Points_lines(pm);
	    composeoflines.Equation_lines=Equation_lines(pm);
	    composeoflines.Slopes_lines=Slope_lines(pm);
	    
	    return composeoflines;   
	}
	
	
	
	
	
	
  
  // compose circle 
	// compose radius 
	public conceptpairs[][]Radius_Circle(ProblemModule pm)
	{
		ArrayList<Circleconcept> circlelist= pm.ConceptInstances.CircleInstances;
		 ArrayList<Radiusconcept> radiuslist=pm.ConceptInstances.RadiusInstances;
		 conceptpairs[][]ComposeOfcircle = new conceptpairs[radiuslist.size()][circlelist.size()];
		 for(int i=0;i<circlelist.size();i++)
			 for(int j=0;j<radiuslist.size();j++)
			 {
				 ComposeOfcircle[j][i]=new conceptpairs();
				 ComposeOfcircle[j][i].concept1=radiuslist.get(j).radiusconceptinfo;
				 ComposeOfcircle[j][i].concept2=circlelist.get(i).Circle_ConceptInfo;
			 }
		 
		 return ComposeOfcircle;
		 
	}
	// compose point
	public conceptpairs[][]Point_Circle(ProblemModule pm)
	{
		ArrayList<Circleconcept> circlelist= pm.ConceptInstances.CircleInstances;
		 ArrayList<Pointconcept>pointlist=pm.ConceptInstances.PointInstances;
		 conceptpairs[][]ComposeOfcircle = new conceptpairs[pointlist.size()][circlelist.size()];
		 for(int i=0;i<circlelist.size();i++)
			 for(int j=0;j<pointlist.size();j++)
			 {
				 ComposeOfcircle[j][i]=new conceptpairs();
				 ComposeOfcircle[j][i].concept1=pointlist.get(j).Point_ConceptInfo;
				 ComposeOfcircle[j][i].concept2=circlelist.get(i).Circle_ConceptInfo;
			 }
		 
		 return ComposeOfcircle;
		 
	}
	
	public conceptpairs[][]Equations_Circle(ProblemModule pm)
	{
		ArrayList<Circleconcept> circlelist= pm.ConceptInstances.CircleInstances;
		ArrayList<Equation> equationlist=pm.ConceptInstances.EquationInstances;
		conceptpairs [][]ComposeOfcircle = new conceptpairs[equationlist.size()][circlelist.size()];
		 for(int i=0;i<equationlist.size();i++)
		 {
			 for(int j=0;j<circlelist.size();j++)
			 {
				 ComposeOfcircle[i][j]=new conceptpairs();
				ComposeOfcircle[i][j].concept1=equationlist.get(i).Equation_ConceptInfo;
				ComposeOfcircle[i][j].concept2=circlelist.get(j).Circle_ConceptInfo;
			 }
		 }
		 
		 return ComposeOfcircle;
	}
	
	
	
	// integrate all the compose element of circles
	public  ComposePairsOfCircle composeofCircle(ProblemModule  pm )
	{
		ComposePairsOfCircle composeofcircles=new ComposePairsOfCircle();
		composeofcircles.Radius_Circles=Radius_Circle(pm);
		composeofcircles.Points_Circles=Point_Circle(pm);
		composeofcircles.Equations_Circles=Equations_Circle(pm);
		
		return composeofcircles;  
	}
	
	
	
}
/*
class conceptpairs
{
	 public  ConceptInfo concept1;
	 public  ConceptInfo concept2;
}
*/
