package com.mathapollo.feature.event.intersect;

import java.util.ArrayList;

import com.mathapollo.feature.computeFeature;
import com.mathapollo.feature.conceptpairs;
import com.mathapollo.feature.compose.ComposeFeature;
import com.mathapollo.feature.event.ComputeEventFeature;
import com.mathapollo.io.ProblemModule;
import com.mathapollo.io.annotation.FeatureIO;
import com.mathapollo.knowledge.concept.Pointconcept;

import com.mathapollo.parsing.EventandRelation.Intersect_relation;

public class AlignIntersects extends FeatureIO {

	// alignment features, objects are line, circle
	public ElementFeatureOfIntersect[][] line_lineFeature;
	public ElementFeatureOfIntersect[][] line_circleFeature;

	// compose feature of point_distance
	public ComposeFeature[][] featureofcomposePoint;

	/*
	 * compute the feature of alignment of intersect event relation
	 * 
	 * 
	 * 
	 */
	public AlignIntersects(ProblemModule pm) {
		ComputeEventFeature computeevent = new ComputeEventFeature();
		computeFeature computefeature = new computeFeature();
		//ArrayList<conceptpairs> pairsoflines = computeevent.getConceptPairof_lines(pm);
		// we use the hybrid concept pairs :
		ArrayList<conceptpairs> pairsoflines = computeevent.GethHybridPairof_lines(pm);
		
		//ArrayList<conceptpairs> linecirclepair = computeevent.getLineCirclePair(pm);
		// we use the hybrid concept pairs :
		ArrayList<conceptpairs> linecirclepair = computeevent.GetHybridLineCirclePair(pm);
		
		ArrayList<Intersect_relation> intersect_instance = pm.ConceptInstances.intersect_relations;
		ArrayList<Pointconcept> pointinstances = pm.ConceptInstances.PointInstances;

		line_circleFeature = new ElementFeatureOfIntersect[intersect_instance.size()][linecirclepair.size()];
		line_lineFeature = new ElementFeatureOfIntersect[intersect_instance.size()][pairsoflines.size()];
		featureofcomposePoint = new ComposeFeature[pointinstances.size()][intersect_instance.size()];

		// compute the feature of pairs of line compose the intersect
		if (pairsoflines.size() > 0 && intersect_instance.size() > 0) {
			for (int i = 0; i < intersect_instance.size(); i++)
				for (int j = 0; j < pairsoflines.size(); j++) {
					line_lineFeature[i][j] = new ElementFeatureOfIntersect();
					line_lineFeature[i][j] = computeevent.computeintersectfeature(pm,
							intersect_instance.get(i).conceptinfo, pairsoflines.get(j).concept1,
							pairsoflines.get(j).concept2);

				}
		}
		int count = 0;
		// compute the feature of line circle pair that compose the intersect
		if (linecirclepair.size() > 0 && intersect_instance.size() > 0) {
			for (int i = 0; i < intersect_instance.size(); i++)
				for (int j = 0; j < linecirclepair.size(); j++) {
					if (linecirclepair.get(j).concept1.index == 1 && linecirclepair.get(j).concept2.index == 0
							&& intersect_instance.get(i).conceptinfo.index == 0)
						count = 1;
					line_circleFeature[i][j] = new ElementFeatureOfIntersect();
					line_circleFeature[i][j] = computeevent.computeintersectfeature(pm,
							intersect_instance.get(i).conceptinfo, linecirclepair.get(j).concept1,
							linecirclepair.get(j).concept2);

				}
		}

		// compute feature for compose the point for the intersect_instance
		if (pointinstances.size() > 0 && intersect_instance.size() > 0) {
			for (int i = 0; i < pointinstances.size(); i++)
				for (int j = 0; j < intersect_instance.size(); j++) {
					featureofcomposePoint[i][j] = new ComposeFeature();
					featureofcomposePoint[i][j] = computefeature.composefeaturecomputing(pm,
							pointinstances.get(i).Point_ConceptInfo, intersect_instance.get(j).conceptinfo);
				}
		}

	}

	private void Save_Feature_Line_Line(String problemId) {
		if (line_lineFeature != null && line_lineFeature.length != 0) {
			if (line_lineFeature[0] != null) {
				for (int i = 0; i < line_lineFeature.length; i++)
					for (int j = 0; j < line_lineFeature[0].length; j++) {
						ElementFeatureOfIntersect alignfeature1 = line_lineFeature[i][j];
						Save(problemId, alignfeature1);
					}
			}
		}
	}

	private void Save_Feature_Line_Circle(String problemId) {
		if (line_circleFeature != null && line_circleFeature.length != 0) {
			if (line_circleFeature[0] != null) {
				for (int i = 0; i < line_circleFeature.length; i++)
					for (int j = 0; j < line_circleFeature[0].length; j++) {
						ElementFeatureOfIntersect alignfeature1 = line_circleFeature[i][j];
						Save(problemId, alignfeature1);
					}
			}
		}
	}

	public void Save(String id) {
		Save_Feature_Line_Line(id);
		Save_Feature_Line_Circle(id);
	}
}
