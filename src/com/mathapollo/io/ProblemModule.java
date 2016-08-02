package com.mathapollo.io;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import LBJ2.infer.PropositionalNegation;

import com.mathapollo.knowledge.concept.Slope;
import com.mathapollo.parsing.contextual.*;

import com.mathapollo.io.raw.*;
public class ProblemModule {

	public List<Chunkers> ChunkerChain;
	
	public List<WordFeatureInfo> FeatureChain; //
	
	public ArrayList<ConceptInfo> ConceptChain;
	
	public ConceptInstances ConceptInstances;

	public ArrayList<ConceptInfo> EventChain;
	public ArrayList<MathExprProblemWord> PointValueChain;
	public ArrayList<MathExprProblemWord> EquationValueChain; 
	public ArrayList<CDvalue> cdchain;
	public ArrayList<QuantityInfo> unknownquantities; 
	public ArrayList<ConceptInfo> HybridConceptChain;
	public ArrayList<WordFeatureInfo> TagList;
	//public ConceptInstances ConceptInstances;
	
	public ProblemModule()
	{
		ChunkerChain = new ArrayList<Chunkers>();
		FeatureChain = new ArrayList<WordFeatureInfo>();
		ConceptChain = new ArrayList<ConceptInfo>();	
		ConceptInstances= new ConceptInstances();
		
		PointValueChain=new ArrayList<MathExprProblemWord>();
		EquationValueChain=new ArrayList<MathExprProblemWord>();
		
		HybridConceptChain= new ArrayList<ConceptInfo>();
		TagList=new ArrayList<WordFeatureInfo>();
		
		EventChain=new ArrayList<ConceptInfo>();	
		cdchain= new ArrayList<CDvalue>();
		unknownquantities= new ArrayList<QuantityInfo>();
	}
	
	
	public String PrintChunkerChain()
	{
		StringBuffer sb = new StringBuffer();
		// print the WordfeatureInfo
		for (int i0 = 0; i0 < ChunkerChain.size(); i0++) {
			System.out.print("[" + ChunkerChain.get(i0).chunk_index /*+ ChunkerChain.get(i0).chunk_Pos*/ + ":");
			for (int i = 0; i < ChunkerChain.get(i0).words_info.size(); i++) {
				WordFeatureInfo temp_wordinfo = new WordFeatureInfo();
				temp_wordinfo = ChunkerChain.get(i0).words_info.get(i);
				int token_index = temp_wordinfo.token_index;
				String temp_token = temp_wordinfo.token;
				String temp_partofspeech = temp_wordinfo.POS_Tag;
				int sentence_index = temp_wordinfo.sentence_index;
				//String out = "wordinfo" + "(" + token_index + "," + temp_token + "," + temp_partofspeech + ","
					//	+ sentence_index + ")";
				String out =  "(" + token_index + "," + temp_token + "," + temp_partofspeech + ","
						+ ")";
				System.out.print(out);
				sb.append(out);
			}
			System.out.print("]");
		}
	
		return sb.toString();
	}
	
	public void PrintConceptChain()
	{
		int numberofconcept=1;
		for(ConceptInfo ci : ConceptChain)
		{
			
			System.out.println(ci.toString());
		}
	}
		
}
