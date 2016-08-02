package com.mathapollo.feature;

import java.util.List;

public class conceptFeatureNode {
	
	public String concept_code;
	//public int instance_index; // index in the concept instance list 
	public conceptfeatures features;
    
	public conceptFeatureNode(String conceptcode) {
		concept_code= new String();
		features=new conceptfeatures() ;// currently four feature elements 
	}

    public class conceptfeatures
    {
    	public int is_followed_by_of;
    	public int  is_near_after_higher_concept;
    //	public int is_after_imperativeverb;
    	public int is_after_what;
    	public int if_The_before;
    	
      
    }
    
}
