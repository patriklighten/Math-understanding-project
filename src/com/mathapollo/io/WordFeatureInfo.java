package com.mathapollo.io;

public class WordFeatureInfo {

	public String token;

	public String POS_Tag;

	public int token_index;
	public int sentence_index;

	public WordFeatureInfo() {
	}

	public WordFeatureInfo(String token1, String pos, int token_index1, int sentence_index1) {
		// POS_Tag= new String();
		POS_Tag = pos;
		// token=new String();
		token = token1;

		token_index = token_index1;

		sentence_index = sentence_index1;
	}
}
