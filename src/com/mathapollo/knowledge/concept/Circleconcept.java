package com.mathapollo.knowledge.concept;

import java.util.ArrayList;
import java.util.List;

import com.mathapollo.io.Chunkers;
import com.mathapollo.io.ConceptInfo;
import com.mathapollo.io.ProblemModule;
import com.mathapollo.io.WordFeatureInfo;

import LBJ2.nlp.SentenceSplitter;
import LBJ2.nlp.Word;
import LBJ2.nlp.WordSplitter;
import LBJ2.nlp.seg.PlainToTokenParser;
import LBJ2.parse.Parser;
import edu.illinois.cs.cogcomp.lbj.chunk.Chunker;
import java.text.Normalizer.Form;
import java.util.ArrayList;

public class Circleconcept {

	public ConceptInfo Circle_ConceptInfo;
	public Pointconcept centerpoint;
	public Value radius;
	public ArrayList<Pointconcept> on_points;// points on the circle
	// public static String valuetype="circle";
	public Equation equation;
	public Equation standerd_form;
	public int Circle_Index;

	public Circleconcept() {
		Circle_ConceptInfo = new ConceptInfo();
		centerpoint = new Pointconcept();
		radius = new Value();
		on_points = new ArrayList<Pointconcept>();
		equation = new Equation();
		standerd_form = new Equation();
	}
	
	@Override
	public String toString()
	{
		return "Circle TODO";
	}
}
