package com.mathapollo.parsing.EventandRelation;

import edu.illinois.cs.cogcomp.lbj.coref.learned.eHHeadLastWord;

import com.mathapollo.io.*;
import com.mathapollo.knowledge.concept.*;

public class Intersect_relation {
	
	public ConceptInfo conceptinfo=new ConceptInfo();
	public  Line_Intersect_Line lineIntersectline=new Line_Intersect_Line();
	public Circle_intersect_Line circle_intersect_Line=new Circle_intersect_Line();
	public int index;
	public Intersect_relation()
	{
		lineIntersectline=new Line_Intersect_Line();
		lineIntersectline.line_Intersect_line();
		
		circle_intersect_Line=new Circle_intersect_Line();
		circle_intersect_Line.Circle_intersect_line();
		conceptinfo=new ConceptInfo();
		
	}
	
}


 class Line_Intersect_Line
{
	Lineconcept line1; 
	Lineconcept line2;
	Pointconcept intersectPoint;
	
	public void line_Intersect_line()
	{
		line1= new Lineconcept();
		line2=new Lineconcept();
		intersectPoint=new Pointconcept();
	}
}

 class Circle_intersect_Line
{
	Lineconcept line1= new Lineconcept(); 
	Circleconcept circle1=new Circleconcept();
	Pointconcept[] intersectPoint= new Pointconcept[2];// two intersect points 
	public void Circle_intersect_line()
	{

		line1= new Lineconcept();
		circle1=new Circleconcept();
		intersectPoint=new Pointconcept[2];
	}
	
}

 