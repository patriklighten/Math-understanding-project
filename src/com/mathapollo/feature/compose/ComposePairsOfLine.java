package com.mathapollo.feature.compose;

import com.mathapollo.feature.conceptpairs;
import com.mathapollo.io.annotation.FeatureIO;

public class ComposePairsOfLine extends FeatureIO{
	
	// candidate compose concepts pairs 
	public conceptpairs[][]Points_lines;
	public conceptpairs[][]Slopes_lines;
	public conceptpairs[][]Equation_lines;
	
	// feature for the candidates concpets pairs 
	
	public ComposeFeature[][]Feature_Pt_Lines; // need to allocate space when use 
	public ComposeFeature[][]Feature_Sl_Lines;
	public ComposeFeature[][]Feature_Eq_Lines;
	
	
	private void SaveFeature_Pt_Line(String id)
	{
		int m1 = 0; 
		if(Feature_Pt_Lines != null)
		{
			m1 = Feature_Pt_Lines.length;
		}
		int n1 = 0;
		if(m1!= 0 && Feature_Pt_Lines[0] != null)
		{
			n1 = Feature_Pt_Lines[0].length;
		}
		
		for (int i = 0; i < m1; i++) {
			for (int j = 0; j < n1; j++) {
				ComposeFeature f1 = Feature_Pt_Lines[i][j];				
				Save(id,f1);				
//				Pipeline.getInstance().AddComposeFeature(id, f1);
//				System.out.println(f1.toString());
			}
		}			
	}
	
	private void SaveFeature_Sl_Line(String id)
	{
		int m1 = 0; 
		if(Feature_Sl_Lines != null)
		{
			m1 = Feature_Sl_Lines.length;
		}
		int n1 = 0;
		if(m1!=0&&Feature_Sl_Lines[0] != null)
		{
			n1 = Feature_Sl_Lines[0].length;
		}
		
		for (int i = 0; i < m1; i++) {
			for (int j = 0; j < n1; j++) {
				ComposeFeature f1 = Feature_Sl_Lines[i][j];
				Save(id,f1);
//				Pipeline.getInstance().AddComposeFeature(id, f1);
//				System.out.println(f1.toString());
			}
		}			
	}
	
	private void SaveFeature_Eq_Line(String id)
	{
		int m1 = 0;
		if(Feature_Eq_Lines != null)
		{
			m1 = Feature_Eq_Lines.length;			
		}
		int n1 = 0;
		if(m1 != 0 && Feature_Eq_Lines[0] != null)
		{
			n1 = Feature_Eq_Lines[0].length;			
		}
		
		for (int i = 0; i < m1; i++) {
			for (int j = 0; j < n1; j++) {
				ComposeFeature f1 = Feature_Eq_Lines[i][j];
				Save(id,f1);
//				Pipeline.getInstance().AddComposeFeature(id, f1);
//				System.out.println(f1.toString());
			}
		}			
	}
	
	public void Save(String id)
	{
		SaveFeature_Pt_Line(id);
		SaveFeature_Sl_Line(id);
		SaveFeature_Eq_Line(id);
	}	
}
