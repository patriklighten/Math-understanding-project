package com.mathapollo.parsing.contextual;

import java.text.Normalizer.Form;
import java.util.ArrayList;

import org.w3c.dom.CDATASection;

import com.mathapollo.io.ConceptInfo;
import com.mathapollo.io.ConceptInstances;
import com.mathapollo.io.ProblemModule;
import com.mathapollo.io.WordFeatureInfo;
import com.mathapollo.knowledge.concept.Pointconcept;

// map values to concept instance 

public class MapValue {
    // cd values words information 
    public  ArrayList<CDvalue> CDvalueChain=new ArrayList<CDvalue>();
    public ComputeQuantity computequantity=new ComputeQuantity();
     
// function
// map value to point
public void mapvaluetoPoint(ProblemModule pm)
{
	// map to known value( CDvalue)  
	pm.cdchain=extractCDvalueChain(pm);
	ArrayList<CDvalue> cdchain= pm.cdchain;
	
	CDvalue temp_cd= new CDvalue();
	int distance=1000;
	int temp_distance=1000;
	int index=-1;
	for(Pointconcept ptconcept:pm.ConceptInstances.PointInstances )
	{
		WordFeatureInfo temp_pointinfo= new WordFeatureInfo();
		distance=1000;
		for(int i=0; i<cdchain.size();i++)
	{
			temp_cd=cdchain.get(i);
		
		if(temp_cd.wordinfo.sentence_index==ptconcept.Point_ConceptInfo.chunk_info.sentence_index&&temp_cd.if_used==0)
		{
			if(( temp_distance=temp_cd.wordinfo.token_index-ptconcept.Point_ConceptInfo.NNtoken_index)>0)
			{
				if (temp_distance<distance)
				{
					distance=temp_distance;
					temp_pointinfo=new WordFeatureInfo();
					temp_pointinfo=temp_cd.wordinfo;
					index=i;
					//pm.ConceptInstances.PointInstances.get(index).mapingfeatureinfo=temp_pointinfo;
					
				}
			}
		}
	}
		if(index!=-1)
		{
			// map feature info including the map information 
			ptconcept.mapingfeatureinfo=temp_pointinfo;
			cdchain.get(index).if_used=1;
			
			
		}
	
}
	pm.cdchain= new ArrayList<CDvalue>();
	//update the cdchain of pm
	pm.cdchain=cdchain;
	// TO DO: map to Unknown value 
}

// map value to slope 
public void mapvaluetoSlope()
{

}



//public void mapValuetoSlope
// map value to equation
public void mapvaluetoEquation()
{
	         
}
 
public ArrayList<CDvalue> extractCDvalueChain(ProblemModule pm)
{
	ArrayList<CDvalue> cdchain= new ArrayList<CDvalue>();
	CDvalue temp_cdvalue= new CDvalue();
  for(WordFeatureInfo words: pm.FeatureChain)
  {
	  if(words.POS_Tag.contains("CD"))
	  {
		 temp_cdvalue.if_used=0;
		 temp_cdvalue.wordinfo=words;
		 // we assume that the concept before and nearest to the cd value is the related concept; 
		 ConceptInfo temp_concept= computequantity.findnearestBeforeConcept(words, pm);
		 // we consider the cd that has a concept mentioned before it 
		 if(temp_concept.chunk_info.sentence_index==words.sentence_index)
		 { temp_cdvalue.relatedConceptInfo=temp_concept;
		 
		 cdchain.add(temp_cdvalue);
		 }
		 
		 
		 temp_cdvalue= new CDvalue();// refresh, very important
	  }
}
  return cdchain;
}
public Boolean IsSameSentence(ConceptInfo conceptinfo, WordFeatureInfo word2)
{
	   //WordFeatureInfo word1=conceptinfo.
       Boolean bool=false;
       if(conceptinfo.chunk_info.sentence_index==word2.sentence_index)
       {
    	 bool=true;
       }
	return bool;
}
}


