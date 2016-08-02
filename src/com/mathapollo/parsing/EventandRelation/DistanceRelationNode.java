package com.mathapollo.parsing.EventandRelation;

import org.junit.validator.PublicClassValidator;

import com.mathapollo.io.ConceptInfo;
import com.mathapollo.io.ProblemModule;
import com.mathapollo.knowledge.concept.Lineconcept;
import com.mathapollo.knowledge.concept.Pointconcept;
import com.mathapollo.knowledge.concept.Value;

public class DistanceRelationNode {
	
	public  Distance_Between_Points distance_between_points = new  Distance_Between_Points();
	public  Distance_Between_Point_Line distance_Between_line_point= new Distance_Between_Point_Line();
	public ConceptInfo conceptinfo;
	public int conceptindex; // index in the group of distance instances 
	public DistanceRelationNode()
	{
		conceptinfo=new ConceptInfo();
		distance_between_points = new  Distance_Between_Points();
		// allocate the space for the child elements 
	    distance_between_points.distance_between_points();
	    
		distance_Between_line_point= new Distance_Between_Point_Line();
		distance_Between_line_point.distance_between_point_line();
		
	}
   
}

 class Distance_Between_Points
{
	public Pointconcept pt1= new Pointconcept();;
	public Pointconcept pt2=new Pointconcept();
	public Value distance1=new Value();
	public void distance_between_points()
	{
		pt1= new Pointconcept();
		pt2=new Pointconcept();
		distance1= new Value();
		
	}
	
}

 class Distance_Between_Point_Line
{
	public Pointconcept pt1= new Pointconcept();;
	public Lineconcept line1=new Lineconcept();;
	public Value distance1= new Value();;
	public void distance_between_point_line()
	{
		pt1= new Pointconcept();
		line1=new Lineconcept();
		distance1= new Value();
		
	}
	
}
