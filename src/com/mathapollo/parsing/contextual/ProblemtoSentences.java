package com.mathapollo.parsing.contextual;

import java.util.ArrayList;

import com.mathapollo.io.Chunkers;
import com.mathapollo.io.ProblemModule;

public class ProblemtoSentences {

	public ArrayList<Sentence> ProblemToSentences(ProblemModule pm)
	{
		ArrayList<Sentence> sentences= new ArrayList<Sentence>();
	    // compute the number of the sentence 
		int numberofsentence=pm.ChunkerChain.get(pm.ChunkerChain.size()-1).sentence_index+1;
	     
		for(int i=0;i<numberofsentence;i++)
		{
			Sentence temp_sentence= new Sentence();
		  for(Chunkers chunk: pm.ChunkerChain)
		  {
			  if(chunk.sentence_index==i)
				 temp_sentence.sentence.add(chunk);
		  }
		 sentences.add(temp_sentence);
		}
		
		return sentences;
	}
}
