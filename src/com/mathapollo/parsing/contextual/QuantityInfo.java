package com.mathapollo.parsing.contextual;

import com.mathapollo.io.Chunkers;
import com.mathapollo.io.ConceptInfo;
import com.mathapollo.io.ConceptInstances;
import com.mathapollo.io.WordFeatureInfo;
import com.mathapollo.knowledge.concept.Value;

public class QuantityInfo {
	public int Quant_Index;
    public Value quantity_value= new Value();
	public String quantitytoken;
	public Chunkers quantityinfo;// record the quantity's chunks information
	public ConceptInfo relatedConcept;// related concept 
	public void QuantityInfo()
	{
		quantity_value= new Value();
		
	}
	
}
