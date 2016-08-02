package com.mathapollo.feature.compose;

import com.mathapollo.feature.conceptpairs;
import com.mathapollo.io.annotation.FeatureIO;
import com.mathapollo.parsing.Pipeline;

public class ComposePairsOfCircle extends FeatureIO{
	
	// all the candidate compose concept pairs for circles 
	public conceptpairs[][] Radius_Circles;
	public conceptpairs[][] Points_Circles;
	public conceptpairs[][] Equations_Circles;
	                                            
	// Feature for pairs of concepts 
	public ComposeFeature[][] Feature_Rd_Cir;
	public ComposeFeature[][] Feature_Pt_Cir;
	public ComposeFeature[][] Feature_Eq_Cir;

	
	private void SaveFeature_Rd_Cir(String id)
	{
		int m1 = 0; 
		if(Feature_Rd_Cir != null)
		{
			m1 = Feature_Rd_Cir.length;
		}
		int n1 = 0;
		if(m1 != 0 && Feature_Rd_Cir[0] != null)
		{
			n1 = Feature_Rd_Cir[0].length;
		}
		
		for (int i = 0; i < m1; i++) {
			for (int j = 0; j < n1; j++) {
				ComposeFeature f1 = Feature_Rd_Cir[i][j];
				Save(id,f1);
				//Pipeline.getInstance().AddComposeFeature(id, f1);
				//System.out.println(f1.toString());
			}
		}			
	}
	
	private void SaveFeature_Pt_Cir(String id)
	{
		int m1 = 0; 
		if(Feature_Pt_Cir != null)
		{
			m1 = Feature_Pt_Cir.length;
		}
		int n1 = 0;
		if(m1 != 0 && Feature_Pt_Cir[0] != null)
		{
			n1 = Feature_Pt_Cir[0].length;
		}
		
		for (int i = 0; i < m1; i++) {
			for (int j = 0; j < n1; j++) {
				ComposeFeature f1 = Feature_Pt_Cir[i][j];
				Save(id,f1);
//				Pipeline.getInstance().AddComposeFeature(id, f1);
//				System.out.println(f1.toString());
			}
		}			
	}
	
	private void SaveFeature_Eq_Cir(String id)
	{
		int m1 = 0;
		if(Feature_Eq_Cir != null)
		{
			m1 = Feature_Eq_Cir.length;			
		}
		int n1 = 0;
		if(m1 != 0 && Feature_Eq_Cir[0] != null)
		{
			n1 = Feature_Eq_Cir[0].length;			
		}
		
		for (int i = 0; i < m1; i++) {
			for (int j = 0; j < n1; j++) {
				ComposeFeature f1 = Feature_Eq_Cir[i][j];
				Save(id,f1);
//				Pipeline.getInstance().AddComposeFeature(id, f1);
//				System.out.println(f1.toString());
			}
		}			
	}
	
	public void Save(String id)
	{
		SaveFeature_Rd_Cir(id);
		SaveFeature_Pt_Cir(id);
		SaveFeature_Eq_Cir(id);
	}		
}
