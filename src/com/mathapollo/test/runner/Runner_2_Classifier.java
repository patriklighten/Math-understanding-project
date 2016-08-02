package com.mathapollo.test.runner;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.xml.ws.handler.LogicalHandler;

import org.javatuples.Pair;

import com.mathapollo.io.annotation.AnnotationIOReader;
import com.mathapollo.io.annotation.FeatureIO;
import com.mathapollo.io.annotation.IOUtils;

import smile.classification.LogisticRegression;
import smile.swing.table.DoubleArrayCellRenderer;
import smile.validation.Accuracy;
import smile.validation.CrossValidation;
import smile.validation.LOOCV;

import com.mathapollo.io.liblinear.*;

public class Runner_2_Classifier {

	
	
	public static void main(String[] args) throws IOException 
	{				
		// Pair<double[][], int[]> pair = FeatureIO.LoadData_Compose();

		 //Pair<double[][], int[]> pair = FeatureIO.LoadData_Merge();
		 
		 //Pair<double[][], int[]> pair = FeatureIO.LoadData_Event_Distance();
		Pair<double[][], int[]> pair = FeatureIO.LoadData_Event_Intersect();
		// Pair<double[][], int[]> pair = FeatureIO.LoadData_Event_Parallel();
		 //Pair<double[][], int[]> pair = FeatureIO.LoadData_Event_Perpendicular();
		//Pair<double[][], int[]> pair = FeatureIO.LoadData_Event_Tangent();
		 
		
		//IOUtils.PrintDoubleFeatures(pair.getValue0());				
		double[][] x = pair.getValue0();
		int[] y = pair.getValue1();

		int n = x.length;
		int k = 10;
		
        CrossValidation cv = new CrossValidation(n, k);

        double total = 0.0;
        
        IOUtils.FlushToFile(FeatureIO.DebugFile);
        IOUtils.FlushToFile(FeatureIO.ScoreFile);
        
        
        for(int i = 0; i < k; i++)
        {
        	double[][] trainx = smile.math.Math.slice(x, cv.train[i]);
        	int[] trainy = smile.math.Math.slice(y, cv.train[i]);
        	
        	CustomizedSmileLR logit = new CustomizedSmileLR(trainx, trainy);

        	CustomizedSmileLR.Trainer lt = new CustomizedSmileLR.Trainer();

        	double [][] testx = smile.math.Math.slice(x, cv.test[i]);
        	int[] truth       = smile.math.Math.slice(y, cv.test[i]);
        	int[] prediction = new int[testx.length];
        	//double[] score= new double[n];
        	//IOUtils.FlushToFile(FeatureIO.DebugFile);
        	
        	for(int j = 0; j < testx.length; j++)
        	{
        		IOUtils.AppendToFile("************one fold start*********", FeatureIO.DebugFile);
        		IOUtils.AppendToFile("************one fold start*********", FeatureIO.ScoreFile);
        		
        		double[] testxArray = testx[j];
        		int predict = logit.predict(testxArray);
        		
        		prediction[j] = predict;
        		
        		if(prediction[j] != truth[j])
        		{
        			//FeatureIO.TrackError(testxArray, FeatureIO.ComposeFeatureFile);    
        			 //FeatureIO.TrackError(testxArray, FeatureIO.MergeFeatureFile);
        			//FeatureIO.TrackError(testxArray, FeatureIO.EventParallelFeatureFile);
        			//FeatureIO.TrackError(testxArray, FeatureIO.EventDistanceFeatureFile);
        			FeatureIO.TrackError(testxArray, FeatureIO.EventIntersectFeatureFile);
        		}
        		
        		
        		//FeatureIO.TrackScore(testxArray, FeatureIO.ComposeFeatureFile, logit.predict2classProbability(testxArray));
        		String temp_str=testxArray.toString();
        		System.out.print(j+":"+logit.predict2classProbability(testxArray));
        		// print score 
        		
        		
        		
        		
        		
        		
        		
        		
        		IOUtils.AppendToFile("************one fold end*********", FeatureIO.DebugFile);
        		IOUtils.AppendToFile(" ", FeatureIO.DebugFile);
  
        		IOUtils.AppendToFile("************one fold end*********", FeatureIO.ScoreFile);
        		
        		
        		
        		
        		IOUtils.AppendToFile(" ", FeatureIO.ScoreFile);
        	}
        	Accuracy instance = new Accuracy();
        	total += instance.measure(truth, prediction);
        }        
        double accuracy = total / k;        
        System.out.print("prediction accuracy: " + accuracy);
        
	}
}
