package com.mathapollo.feature.event.parallel;

import java.util.ArrayList;

import com.mathapollo.feature.conceptpairs;
import com.mathapollo.feature.event.ComputeEventFeature;
import com.mathapollo.io.ProblemModule;
import com.mathapollo.io.annotation.FeatureIO;
import com.mathapollo.parsing.EventandRelation.ParallelRelation;

public class AlignParallel extends FeatureIO {

	public FeatureOfParallel[][] Alignfeatures;

	public AlignParallel(ProblemModule pm) {
		ComputeEventFeature computeevent = new ComputeEventFeature();
		/*
		 * ArrayList<PerpendicularRe>
		 * perpendlist=pm.ConceptInstances.perpendi_relations;
		 * ArrayList<conceptpairs>
		 * linepairconcepts=computeevent.getConceptPairof_lines(pm);
		 * AlignPerpendicular featureofAlignment=new AlignPerpendicular();
		 * featureofAlignment.perpendlinefeatures=new
		 * FeatureOfPerpendicular[perpendlist.size()][linepairconcepts.size()];
		 */
		ArrayList<ParallelRelation> paralelList = pm.ConceptInstances.parallelRelations;
	
		//ArrayList<conceptpairs> linepairconcepts = computeevent.getConceptPairof_lines(pm);
		// we use the hybrid lineconcept pairs
		
		ArrayList<conceptpairs> linepairconcepts = computeevent.GethHybridPairof_lines(pm);
		
		Alignfeatures = new FeatureOfParallel[paralelList.size()][linepairconcepts.size()];

		if (linepairconcepts.size() > 0 && paralelList.size() > 0) {
			for (int i = 0; i < paralelList.size(); i++)
				for (int j = 0; j < linepairconcepts.size(); j++) {
					if(paralelList.get(i).paralellConceptinfo!=null&&linepairconcepts.get(j)!=null&&linepairconcepts.get(j).concept2!=null)
					{
						Alignfeatures[i][j] = new FeatureOfParallel();
					Alignfeatures[i][j] = computeevent.computeParallelFeature(pm,
							paralelList.get(i).paralellConceptinfo, linepairconcepts.get(j).concept1,
							linepairconcepts.get(j).concept2);
					}

				}
		}
	}

	public void Save(String id) {
		if (Alignfeatures != null && Alignfeatures.length != 0) {
			for (int i = 0; i < Alignfeatures.length; i++)
				for (int j = 0; j < Alignfeatures[0].length; j++) {
					FeatureOfParallel tempft = Alignfeatures[i][j];
					if(Alignfeatures[i][j]!=null)
					Save(id, tempft);
				}
		}
	}
}
