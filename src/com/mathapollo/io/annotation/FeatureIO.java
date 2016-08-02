package com.mathapollo.io.annotation;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Triplet;

import com.mathapollo.feature.compose.ComposeFeature;
import com.mathapollo.feature.event.distance.composeFeatureofDistance;
import com.mathapollo.feature.event.intersect.ElementFeatureOfIntersect;
import com.mathapollo.feature.event.parallel.FeatureOfParallel;
import com.mathapollo.feature.event.perpendicular.FeatureOfPerpendicular;
import com.mathapollo.feature.event.tangent.FeatureOfTangentAlignment;
import com.mathapollo.feature.merge.MergeFeature;

public abstract class FeatureIO {

	public static final String ComposeFeatureFile = "data/feature_compose.txt";
	public static final String MergeFeatureFile = "data/feature_merge.txt";
	public static final String EventDistanceFeatureFile = "data/feature_event_distance.txt";
	public static final String EventIntersectFeatureFile = "data/feature_event_intersect.txt";
	public static final String EventParallelFeatureFile = "data/feature_event_parallel.txt";
	public static final String EventPerpendicularFeatureFile = "data/feature_event_perpendicular.txt";
	public static final String EventTangentFeatureFile = "data/feature_event_tangent.txt";

	public static final String DebugFile = "data/error.txt";
	public static final String ScoreFile = "data/score.txt";
	
	
	public static void TrackScore(double[] featureVector, String fileName, double score)
	{
		try {
			FileInputStream fstream = new FileInputStream(fileName);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			
			String strLine;
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				// Print the content on the console
				if (strLine.isEmpty())
					continue;
				strLine.replaceAll("\\s+", "");
				String[] tokens = strLine.split(",");
		
				int currentY = Integer.parseInt(tokens[tokens.length-1]);
				
				String currentXStr = tokens[tokens.length-2];
				currentXStr.replaceAll("\\s+", "");
				currentXStr = currentXStr.substring(1, currentXStr.length()-1);
				
				String[] splitXs = currentXStr.split(";");
				
				double[] currentX = new double[splitXs.length];
				
				for(int i = 0; i < splitXs.length; i++)
				{
					double value = Double.parseDouble(splitXs[i]);
					currentX[i] = value;
				}
				
				boolean sameArray = IOUtils.AreTwoSameDoubleArrays(currentX, featureVector);
				if(sameArray)
				{
					IOUtils.AppendToFile(strLine + "::" + score, ScoreFile);
				}	
			}
			// Close the input stream
			br.close();			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void TrackError(double[] featureVector, String fileName)
	{
		try {
			FileInputStream fstream = new FileInputStream(fileName);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			
			String strLine;
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				// Print the content on the console
				if (strLine.isEmpty())
					continue;
				strLine.replaceAll("\\s+", "");
				String[] tokens = strLine.split(",");
		
				int currentY = Integer.parseInt(tokens[tokens.length-1]);
				
				String currentXStr = tokens[tokens.length-2];
				currentXStr.replaceAll("\\s+", "");
				currentXStr = currentXStr.substring(1, currentXStr.length()-1);
				
				String[] splitXs = currentXStr.split(";");
				
				double[] currentX = new double[splitXs.length];
				
				for(int i = 0; i < splitXs.length; i++)
				{
					double value = Double.parseDouble(splitXs[i]);
					currentX[i] = value;
				}
				
				boolean sameArray = IOUtils.AreTwoSameDoubleArrays(currentX, featureVector);
				if(sameArray)
				{
					IOUtils.AppendToFile(strLine, DebugFile);
				}	
			}
			// Close the input stream
			br.close();			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static Pair<double[][], int[]> LoadData(String fileName)
	{
		try {
			FileInputStream fstream = new FileInputStream(fileName);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

			ArrayList<double[]> currentXs = new ArrayList<double[]>();
			ArrayList<Integer> currentYs = new ArrayList<Integer>();
						
			String strLine;
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				// Print the content on the console
				if (strLine.isEmpty())
					continue;
				strLine.replaceAll("\\s+", "");
				String[] tokens = strLine.split(",");
		
				int currentY = Integer.parseInt(tokens[tokens.length-1]);
				
				String currentXStr = tokens[tokens.length-2];
				currentXStr.replaceAll("\\s+", "");
				currentXStr = currentXStr.substring(1, currentXStr.length()-1);
				
				String[] splitXs = currentXStr.split(";");
				
				double[] currentX = new double[splitXs.length];
				
				for(int i = 0; i < splitXs.length; i++)
				{
					double value = Double.parseDouble(splitXs[i]);
					currentX[i] = value;
				}				
				currentXs.add(currentX);
				currentYs.add(currentY);				
			}
			// Close the input stream
			br.close();
			
			double[][] targetX = new double[currentXs.size()][];
			for (int i = 0; i < targetX.length; i++) {
			   targetX[i] = currentXs.get(i);
			}	
			int[] targetY = new int[currentYs.size()];
			for (int i = 0; i < targetY.length; i++) {
				   targetY[i] = currentYs.get(i);
			}			
			return new Pair<double[][], int[]>(targetX, targetY);
						
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return null;
	}
	
	public static Pair<double[][], int[]> LoadData_Compose()
	{
		return LoadData(ComposeFeatureFile);
	}
	
	public static Pair<double[][], int[]> LoadData_Merge()
	{
		return LoadData(MergeFeatureFile);
	}
	
	public static Pair<double[][], int[]> LoadData_Event_Distance()
	{
		return LoadData(EventDistanceFeatureFile);
	}

	public static Pair<double[][], int[]> LoadData_Event_Perpendicular()
	{
		return LoadData(EventPerpendicularFeatureFile);
	}
	
	public static Pair<double[][], int[]> LoadData_Event_Parallel()
	{
		return LoadData(EventParallelFeatureFile);
	}
	
	public static Pair<double[][], int[]> LoadData_Event_Intersect()
	{
		return LoadData(EventIntersectFeatureFile);
	}
	
	public static Pair<double[][], int[]> LoadData_Event_Tangent()
	{
		return LoadData(EventTangentFeatureFile);
	}
		
	public void Save(String problemIndex, ComposeFeature cf) {
		// problemid,globalIndex1,globalIndex2,feature vector,label
		StringBuffer sb = new StringBuffer();
		sb.append(problemIndex).append(',');

		int wordindex1 = cf.Composepair.concept1.global_index;
		int wordindex2 = cf.Composepair.concept2.global_index;
		String wordIndex1Str = Integer.toString(wordindex1);
		String wordIndex2Str = Integer.toString(wordindex2);
		sb.append(wordIndex1Str).append(',');
		sb.append(wordIndex2Str).append(',');
		sb.append(cf.toString()).append(',');

		// Search Label
		String label = SearchTuple3ComposeAnnotationLabel(problemIndex, wordIndex1Str, wordIndex2Str);
		sb.append(label);

		String data = sb.toString();
		IOUtils.AppendToFile(data, ComposeFeatureFile);
	}

	public void Save(String problemIndex, MergeFeature mf) {
		// problemid,globalIndex1,globalIndex2,feature vector,label
		StringBuffer sb = new StringBuffer();
		sb.append(problemIndex).append(',');

		int wordindex1 = mf.MergePair.concept1.global_index;
		int wordindex2 = mf.MergePair.concept2.global_index;
		String wordIndex1Str = Integer.toString(wordindex1);
		String wordIndex2Str = Integer.toString(wordindex2);
		sb.append(wordIndex1Str).append(',');
		sb.append(wordIndex2Str).append(',');
		sb.append(mf.toString()).append(',');

		// Search Label
		String label = SearchTuple3MergeAnnotationLabel(problemIndex, wordIndex1Str, wordIndex2Str);
		sb.append(label);

		String data = sb.toString();
		IOUtils.AppendToFile(data, MergeFeatureFile);
	}

	public void Save(String problemIndex, composeFeatureofDistance cfd) {
		// problemid,globalIndex1,globalIndex2,feature vector,label
		StringBuffer sb = new StringBuffer();
		sb.append(problemIndex).append(',');

		int globalIndex = cfd.distanceconceptinfo.global_index;
		int wordindex1 = cfd.conceptpairinfo.concept1.global_index;
		int wordindex2 = cfd.conceptpairinfo.concept2.global_index;

		String globalStr = Integer.toString(globalIndex);
		String wordIndex1Str = Integer.toString(wordindex1);
		String wordIndex2Str = Integer.toString(wordindex2);
		sb.append(globalStr).append(',');
		sb.append(wordIndex1Str).append(',');
		sb.append(wordIndex2Str).append(',');
		sb.append(cfd.toString()).append(',');

		// Search Label
		String label = SearchTuple4EventAnnotationLabel(problemIndex, globalStr, wordIndex1Str, wordIndex2Str,
				EventType.distance);
		sb.append(label);

		String data = sb.toString();

		IOUtils.AppendToFile(data, EventDistanceFeatureFile);
	}

	public void Save(String problemIndex, ElementFeatureOfIntersect intersectFeature) {
		// problemid,globalIndex1,globalIndex2,feature vector,label
		StringBuffer sb = new StringBuffer();
		sb.append(problemIndex).append(',');

		int globalIndex = intersectFeature.intersectinfo.global_index;
		int wordindex1 = intersectFeature.conceptpairinfo.concept1.global_index;
		int wordindex2 = intersectFeature.conceptpairinfo.concept2.global_index;

		String globalStr = Integer.toString(globalIndex);
		String wordIndex1Str = Integer.toString(wordindex1);
		String wordIndex2Str = Integer.toString(wordindex2);
		sb.append(globalStr).append(',');
		sb.append(wordIndex1Str).append(',');
		sb.append(wordIndex2Str).append(',');
		sb.append(intersectFeature.toString()).append(',');

		// Search Label
		String label = SearchTuple4EventAnnotationLabel(problemIndex, globalStr, wordIndex1Str, wordIndex2Str,
				EventType.intersect);
		sb.append(label);
		String data = sb.toString();
		IOUtils.AppendToFile(data, EventIntersectFeatureFile);
	}

	public void Save(String problemIndex, FeatureOfParallel parallelFeature) {
		// problemid,globalIndex1,globalIndex2,feature vector,label
		StringBuffer sb = new StringBuffer();
		sb.append(problemIndex).append(',');

		int globalIndex = parallelFeature.parallelinfo.global_index;
		int wordindex1 = parallelFeature.Linepairinfo.concept1.global_index;
		int wordindex2 = parallelFeature.Linepairinfo.concept2.global_index;

		String globalStr = Integer.toString(globalIndex);
		String wordIndex1Str = Integer.toString(wordindex1);
		String wordIndex2Str = Integer.toString(wordindex2);
		sb.append(globalStr).append(',');
		sb.append(wordIndex1Str).append(',');
		sb.append(wordIndex2Str).append(',');
		sb.append(parallelFeature.toString()).append(',');

		// Search Label
		String label = SearchTuple4EventAnnotationLabel(problemIndex, globalStr, wordIndex1Str, wordIndex2Str,
				EventType.parallel);
		sb.append(label);
		String data = sb.toString();
		IOUtils.AppendToFile(data, EventParallelFeatureFile);
	}

	public void Save(String problemIndex, FeatureOfPerpendicular perpendicularFeature) {
		// problemid,globalIndex1,globalIndex2,feature vector,label
		StringBuffer sb = new StringBuffer();
		sb.append(problemIndex).append(',');

		int globalIndex = perpendicularFeature.perpendicularinfo.global_index;
		int wordindex1 = perpendicularFeature.Linepairinfo.concept1.global_index;
		int wordindex2 = perpendicularFeature.Linepairinfo.concept2.global_index;

		String globalStr = Integer.toString(globalIndex);
		String wordIndex1Str = Integer.toString(wordindex1);
		String wordIndex2Str = Integer.toString(wordindex2);
		sb.append(globalStr).append(',');
		sb.append(wordIndex1Str).append(',');
		sb.append(wordIndex2Str).append(',');
		sb.append(perpendicularFeature.toString()).append(',');

		// Search Label
		String label = SearchTuple4EventAnnotationLabel(problemIndex, globalStr, wordIndex1Str, wordIndex2Str,
				EventType.perpendicular);
		sb.append(label);
		String data = sb.toString();
		IOUtils.AppendToFile(data, EventPerpendicularFeatureFile);
	}

	public void Save(String problemIndex, FeatureOfTangentAlignment tangentFeature) {

	}

	public enum EventType {
		distance, perpendicular, parallel, tangent, intersect
	}

	private String SearchTuple4EventAnnotationLabel(String problemIndex, String globalIndex, String word1Index,
			String word2Index, EventType eventType) {

		ArrayList<Quartet<String, String, String, String>> lst = null;

		switch (eventType) {
		case distance:
			lst = AnnotationIOReader.getInstance()._eventDistanceTPInstances;
			break;
		case perpendicular:
			lst = AnnotationIOReader.getInstance()._eventPerpendicularTPInstances;
			break;
		case parallel:
			lst = AnnotationIOReader.getInstance()._eventParallelTPInstances;
			break;
		case intersect:
			lst = AnnotationIOReader.getInstance()._eventIntersectTPInstances;
			break;
		case tangent:
			// lst = AnnotationIOReader.getInstance()._eventTangentTPInstances;
			break;
		}

		if (lst == null)
			return "0";

		for (Quartet<String, String, String, String> temp : lst) {
			if (!temp.getValue0().equals(problemIndex))
				continue;

			String val1 = temp.getValue1().trim();
			String val2 = temp.getValue2().trim();
			String val3 = temp.getValue3().trim();
		

			Set<String> set1 = new HashSet<String>();
			set1.add(val1);
			set1.add(val2);
			set1.add(val3);

			Set<String> set2 = new HashSet<String>();
			set2.add(globalIndex);
			set2.add(word1Index);
			set2.add(word2Index);

			if (set2.equals(set1))
				return "1";
		}
		return "0";
	}

	/*
	 * 
	 * Search Annotation IO any matched True positive label.
	 * 
	 */
	private String SearchTuple3ComposeAnnotationLabel(String problemIndex, String word1Index, String word2Index) {
		ArrayList<Triplet<String, String, String>> lst = AnnotationIOReader.getInstance()._composeTPInstances;

		for (Triplet<String, String, String> temp : lst) {
			if (!temp.getValue0().equals(problemIndex))
				continue;

			String val1 = temp.getValue1();
			String val2 = temp.getValue2();
			
			val1 = val1.trim();
			val2 = val2.trim();
			word1Index = word1Index.trim();
			word2Index = word2Index.trim();

			if (val1.equals(word1Index) && val2.equals(word2Index)) {
				return "1";
			} else if (val1.equals(word2Index) && val2.equals(word1Index)) {
				return "1";
			}
		}

		return "0";
	}

	private String SearchTuple3MergeAnnotationLabel(String problemIndex, String word1Index, String word2Index) {
		ArrayList<Triplet<String, String, String>> lst = AnnotationIOReader.getInstance()._mergeTPInstances;

		for (Triplet<String, String, String> temp : lst) {
			if (!temp.getValue0().equals(problemIndex))
				continue;

			String val1 = temp.getValue1();
			String val2 = temp.getValue2();
			
			val1 = val1.trim();
			val2 = val2.trim();
			word1Index = word1Index.trim();
			word2Index = word2Index.trim();

			if (val1.equals(word1Index) && val2.equals(word2Index)) {
				return "1";
			} else if (val1.equals(word2Index) && val2.equals(word1Index)) {
				return "1";
			}
		}

		return "0";
	}
}