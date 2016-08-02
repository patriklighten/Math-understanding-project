package com.mathapollo.parsing.EventandRelation;

import com.mathapollo.feature.conceptpairs;
import com.mathapollo.io.ConceptInfo;
import com.mathapollo.knowledge.concept.Lineconcept;

public class PerpendicularRe 
{
    public ConceptInfo perpendInfo; 
    public Lineconcept[] composeLinePair=new Lineconcept[2];
    public int index;
    public PerpendicularRe()
    {
    	perpendInfo=new ConceptInfo();
    	composeLinePair=new Lineconcept[2];
    }
}
