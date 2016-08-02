package com.mathapollo.io;

import java.lang.reflect.Array;
import java.util.ArrayList;

import com.mathapollo.knowledge.concept.Circleconcept;
import com.mathapollo.knowledge.concept.Equation;
import com.mathapollo.knowledge.concept.Lineconcept;
import com.mathapollo.knowledge.concept.Pointconcept;
import com.mathapollo.knowledge.concept.Radiusconcept;
import com.mathapollo.knowledge.concept.Slope;
import com.mathapollo.parsing.EventandRelation.DistanceRelationNode;
import com.mathapollo.parsing.EventandRelation.Intersect_relation;
import com.mathapollo.parsing.EventandRelation.PerpendicularRe;
import com.mathapollo.parsing.EventandRelation.*;
// Concept Class Instance
public class ConceptInstances {
 public ArrayList<Circleconcept> CircleInstances;
 public ArrayList<Lineconcept>    LineInstances;
 public ArrayList<Pointconcept>   PointInstances;
public  ArrayList<Equation>   EquationInstances;
public ArrayList<Slope> SlopeInstances;
public  ArrayList<Radiusconcept> RadiusInstances;  
public ArrayList<DistanceRelationNode> DistanceInstance;
public ArrayList<Intersect_relation> intersect_relations;
public ArrayList<PerpendicularRe> perpendi_relations;
public ArrayList<ParallelRelation> parallelRelations;
public ConceptInstances() {
	   CircleInstances= new ArrayList<Circleconcept>();
	   LineInstances=new ArrayList<Lineconcept>();
	   PointInstances= new ArrayList<Pointconcept>();
	   EquationInstances= new ArrayList<Equation>();
	   SlopeInstances=new ArrayList<Slope>();
	   RadiusInstances= new ArrayList<Radiusconcept>();
	   DistanceInstance= new ArrayList<DistanceRelationNode>();
	   intersect_relations= new ArrayList<Intersect_relation>();
	   perpendi_relations= new ArrayList<PerpendicularRe>();
	   parallelRelations=new ArrayList<ParallelRelation>();
}
 
}
