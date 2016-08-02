package com.mathapollo.feature.event.distance;

import java.util.ArrayList;

import com.mathapollo.feature.event.*;
import com.mathapollo.feature.conceptpairs;
import com.mathapollo.io.ConceptInfo;
import com.mathapollo.io.ProblemModule;
import com.mathapollo.io.annotation.FeatureIO;
import com.mathapollo.parsing.EventandRelation.DistanceRelationNode;

public class ComposeDistance extends FeatureIO{
	// for a distance instance, choose the highest value feature, no matter
	// point_point or point_line
	// inter-elements feature, <line,point>, <point,point>
	public composeFeatureofDistance[][] point_pointFeature;
	public composeFeatureofDistance[][] point_lineFeature;

	public ComposeDistance(ProblemModule pm) {
		// all
		// the
		// distance
		// information
		ComputeEventFeature computeevent = new ComputeEventFeature();
		//ArrayList<conceptpairs> pointpairs = computeevent.getConceptPairof_points(pm);
		// hybrid pointpairs: 
		ArrayList<conceptpairs> pointpairs = computeevent.GetHybridPairof_points(pm);
		//ArrayList<conceptpairs> point_linePair = computeevent.getConceptPairof_linepoint(pm);
		// hybrid point_linePair:
		ArrayList<conceptpairs> point_linePair = computeevent.GetHypridPairof_linepoint(pm);
				
		ArrayList<DistanceRelationNode> distancelist = pm.ConceptInstances.DistanceInstance;
		// in the composeFeatureofDistance[][] point_pointFeature; the first
		// element should be the distance, the second is the elements
		point_lineFeature = new composeFeatureofDistance[distancelist.size()][point_linePair.size()];
		point_pointFeature = new composeFeatureofDistance[distancelist.size()][pointpairs.size()];

		if (pointpairs.size() > 0 && distancelist.size() > 0) {
			// points pair alignment feature for distance
			for (int i = 0; i < distancelist.size(); i++)
				for (int j = 0; j < pointpairs.size(); j++) {
					// distance concept information
					ConceptInfo distanceinfo = distancelist.get(i).conceptinfo;
					// concept pair
					ConceptInfo concept1 = pointpairs.get(j).concept1;
					ConceptInfo concept2 = pointpairs.get(j).concept2;
					if(pointpairs.get(j).concept1.global_index==pointpairs.get(j).concept2.global_index)
					{
						point_pointFeature[i][j] = new composeFeatureofDistance();
						point_pointFeature[i][j] = computeevent.getdistancesFeature(pm, distanceinfo, concept1, concept2);
					}
						point_pointFeature[i][j] = new composeFeatureofDistance();
					point_pointFeature[i][j] = computeevent.getdistancesFeature(pm, distanceinfo, concept1, concept2);

				}

			// line_point alignment feature for distance

			if (point_linePair.size() > 0 && distancelist.size() > 0) {
				for (int i = 0; i < distancelist.size(); i++)
					for (int j = 0; j < point_linePair.size(); j++) {
						// distance concept information
						ConceptInfo distanceinfo = distancelist.get(i).conceptinfo;
						// concept pair
						ConceptInfo concept1 = point_linePair.get(j).concept1;
						ConceptInfo concept2 = point_linePair.get(j).concept2;
						point_lineFeature[i][j] = new composeFeatureofDistance();
						point_lineFeature[i][j] = computeevent.getdistancesFeature(pm, distanceinfo, concept1,
								concept2);
					}
			}

		}

	}

	/*
	 * 
	 * point point alignment feature
	 * 
	 */
	private void SaveFeature_point_point(String id) {
		if (point_pointFeature != null && point_pointFeature.length != 0) {
			if (point_pointFeature[0] != null) {
				for (int i = 0; i < point_pointFeature.length; i++)
					for (int j = 0; j < point_pointFeature[0].length; j++) {
						composeFeatureofDistance alignfeature = point_pointFeature[i][j];
						Save(id, alignfeature);
					}
			}
		}
	}

	/*
	 *  Point_Line alignment for the distance
	 *  
	 */	
	private void SaveFeature_point_line(String id) {		
		if (point_lineFeature != null && point_lineFeature.length != 0) {
			if (point_lineFeature[0] != null) {
				int n1 = point_lineFeature.length;// distance
				int n2 = point_lineFeature[0].length;// pointline
				for (int i = 0; i < point_lineFeature.length; i++)
					for (int j = 0; j < point_lineFeature[0].length; j++) {
					if( point_lineFeature[i][j]!=null)
						{
						composeFeatureofDistance alignfeature = point_lineFeature[i][j];						
						
						Save(id, alignfeature);
						}
					}
			}
		}
	}

	public void Save(String id) {
		SaveFeature_point_point(id);
		SaveFeature_point_line(id);
	}
}