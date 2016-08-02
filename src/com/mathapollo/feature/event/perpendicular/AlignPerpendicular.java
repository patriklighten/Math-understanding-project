package com.mathapollo.feature.event.perpendicular;

import java.util.ArrayList;

import com.mathapollo.feature.conceptpairs;
import com.mathapollo.feature.event.ComputeEventFeature;
import com.mathapollo.io.ProblemModule;
import com.mathapollo.io.annotation.FeatureIO;
import com.mathapollo.parsing.EventandRelation.PerpendicularRe;

public class AlignPerpendicular extends FeatureIO
{
	public FeatureOfPerpendicular[][] perpendlinefeatures;
	
	public AlignPerpendicular(ProblemModule pm)
	{
		ComputeEventFeature computeevent = new ComputeEventFeature();
		ArrayList<PerpendicularRe> perpendlist = pm.ConceptInstances.perpendi_relations;
		
		//ArrayList<conceptpairs> linepairconcepts = computeevent.getConceptPairof_lines(pm);
		// we use the hybrid lineconcept pairs
		ArrayList<conceptpairs> linepairconcepts = computeevent.GethHybridPairof_lines(pm);
				
		
		perpendlinefeatures = new FeatureOfPerpendicular[perpendlist.size()][linepairconcepts
				.size()];

		if (linepairconcepts.size() > 0 && perpendlist.size() > 0) {
			for (int i = 0; i < perpendlist.size(); i++)
				for (int j = 0; j < linepairconcepts.size(); j++) {
					perpendlinefeatures[i][j] = new FeatureOfPerpendicular();

					perpendlinefeatures[i][j] = computeevent.computePerpendicularFeature(pm,
							perpendlist.get(i).perpendInfo, linepairconcepts.get(j).concept1,
							linepairconcepts.get(j).concept2);

				}
		}
	}
		
	public void Save(String id)
	{
		if (perpendlinefeatures != null
				&& perpendlinefeatures.length != 0) {		
			if (perpendlinefeatures[0] != null) {
				for (int i = 0; i < perpendlinefeatures.length; i++)
					for (int j = 0; j < perpendlinefeatures[0].length; j++) {
						 FeatureOfPerpendicular tempft = perpendlinefeatures[i][j];
						 Save(id, tempft);
					}
			}
		}
	}
	
}
