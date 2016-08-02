package com.mathapollo.io;

import java.util.ArrayList;
import java.util.List;

public class Chunkers {

	public ArrayList<WordFeatureInfo> words_info;// words with their pos in the chunker
	public int chunk_index; 
	public int sentence_index; 
	public String chunk_Pos;
	
	public Chunkers()
	{
		words_info = new ArrayList<WordFeatureInfo>();
	}

	
	public String GetTokens()
	{
		StringBuffer tokens = new StringBuffer();
		
		for(WordFeatureInfo wfi : words_info)
		{
			tokens.append(wfi.token.toLowerCase()).append(' ');
		}
		return tokens.toString();
	}	
	public String GetTokens_Origin()// the token is not to low case 
	{
		StringBuffer tokens = new StringBuffer();
		
		for(WordFeatureInfo wfi : words_info)
		{
			tokens.append(wfi.token).append(' ');
		}
		return tokens.toString();
	}	
}
