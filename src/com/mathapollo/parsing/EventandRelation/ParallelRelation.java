package com.mathapollo.parsing.EventandRelation;

import com.mathapollo.io.ConceptInfo;
import com.mathapollo.knowledge.concept.Lineconcept;

public class ParallelRelation 
{
    public ConceptInfo paralellConceptinfo; 
    public Lineconcept[] LinePairs=new Lineconcept[2]; 
    public int index; 
    public ParallelRelation()
    {
    	paralellConceptinfo=new ConceptInfo();
    	LinePairs=new Lineconcept[2];
    }
}
