package com.mathapollo.knowledge.concept;

import com.mathapollo.io.WordFeatureInfo;

public class Value {

	public String unknown_variable;// if "?" or "non number" is unknown
									// quantity, if "know" means known variable
	public double value;

	public WordFeatureInfo valueinfo = new WordFeatureInfo();

	public Value(String unknow, double values) {

		unknown_variable = unknow;
		value = values;
	}

	public Value() {
		valueinfo = new WordFeatureInfo();
	}
	
	@Override
	public String toString()
	{
		if(unknown_variable != null)
		{
			return unknown_variable;
		}
		else
		{
			return Double.toString(value);
		}	
	}
}
