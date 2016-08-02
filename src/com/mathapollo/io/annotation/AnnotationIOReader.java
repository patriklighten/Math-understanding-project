package com.mathapollo.io.annotation;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.javatuples.Quartet;
import org.javatuples.Triplet;

import com.mathapollo.io.raw.MathProblem;
import com.mathapollo.parsing.Pipeline;

public class AnnotationIOReader {

	private static AnnotationIOReader instance = null;

	public ArrayList<Triplet<String, String, String>> _composeTPInstances;
	public ArrayList<Triplet<String, String, String>> _mergeTPInstances;

	public ArrayList<Quartet<String, String, String, String>> _eventDistanceTPInstances;
	public ArrayList<Quartet<String, String, String, String>> _eventPerpendicularTPInstances;
	public ArrayList<Quartet<String, String, String, String>> _eventParallelTPInstances;
	public ArrayList<Quartet<String, String, String, String>> _eventIntersectTPInstances;
	public ArrayList<Quartet<String, String, String, String>> _eventTangentTPInstances;
	
	
	public static AnnotationIOReader getInstance() {
		if (instance == null) {
			instance = new AnnotationIOReader();
		}
		return instance;
	}

	//private ArrayList<MathProblem> problems;
	
	public ArrayList<String> AnnotatedProblemIndexLst;

	public static String ConceptAnnotationFile = "data/MathAnnotation.txt";
	public static String ProblemAnnotationFile = "data/PreAnnotationProblems.txt";

	public static final String ComposeStart = "##compose";
	public static final String MergeStart = "##merge";
	public static final String EventStart = "##event";
	public static final String DistanceStart = "#EventDistance";
	public static final String PerpendicularStart = "#EventPerpendicular";
	public static final String ParallelStart = "#EventParallel";
	public static final String IntersectStart = "#EventIntersect";
	public static final String TangentStart   = "#EventTangent";

	public String CurrentState;

	protected AnnotationIOReader() {
		
		AnnotatedProblemIndexLst = new ArrayList<String>(); 
		
		_composeTPInstances = new ArrayList<Triplet<String, String, String>>();
		_mergeTPInstances = new ArrayList<Triplet<String, String, String>>();
		_eventDistanceTPInstances = new ArrayList<Quartet<String, String, String, String>>();
		_eventPerpendicularTPInstances = new ArrayList<Quartet<String, String, String, String>>();
		_eventParallelTPInstances = new ArrayList<Quartet<String, String, String, String>>();
		_eventIntersectTPInstances = new ArrayList<Quartet<String, String, String, String>>();
	}

	public void LoadAnnotatedProblems() {
		try {
			FileInputStream fstream = new FileInputStream(ProblemAnnotationFile);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

			String strLine;
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				// Print the content on the console
				if (strLine.isEmpty())
					continue;
				
				int colonIndex = strLine.indexOf(":");
				String problemIndex = strLine.substring(0, colonIndex);
				problemIndex.replaceAll("\\s+", "");
				AnnotatedProblemIndexLst.add(problemIndex);
			}
			// Close the input stream
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void LoadConceptAnnotation() throws Exception {
		try {
			FileInputStream fstream = new FileInputStream(ConceptAnnotationFile);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

			String strLine;
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				// Print the content on the console
				if (strLine.isEmpty())
					continue;
				if (ChangeState(strLine))
					continue;

				switch (CurrentState) {
				case ComposeStart:
					RecordComposeFeature(strLine);
					break;
				case MergeStart:
					RecordMergeFeature(strLine);
					break;
				case EventStart:
					break;
				case DistanceStart:
					RecordEventDistanceFeature(strLine);
					break;
				case PerpendicularStart:
					RecordEventPerpendicularFeature(strLine);
					break;
				case ParallelStart:
					RecordEventParallelFeature(strLine);
					break;
				case IntersectStart:
					RecordEventIntersectFeature(strLine);
					break;
				}

				// System.out.println(strLine);
			}
			// Close the input stream
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean ContainComment(String input) {
		Pattern p = Pattern.compile("#", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(input);
		return m.find();
	}

	private void RecordComposeFeature(String input) throws Exception {
		Triplet<String, String, String> annotate3 = Record3(input);
		if (annotate3 != null)
		{
			_composeTPInstances.add(annotate3);			
		}
	}

	private void RecordMergeFeature(String input) throws Exception {
		Triplet<String, String, String> annotate3 = Record3(input);
		if(annotate3 != null)
		{
			_mergeTPInstances.add(annotate3);			
		}
	}

	/*
	 * Example 0003,0,1
	 */
	private Triplet<String, String, String> Record3(String input) throws Exception {
		String parseStr = input;

		if (ContainComment(input)) // remove comment
		{
			int index = input.indexOf("#");
			if (index == 0)
				return null;
			parseStr = input.substring(0, index - 1);
		}

		parseStr.replaceAll("\\s+", "");
		String[] lst = parseStr.split(",");
		if (lst.length != 3)
			throw new Exception("the length of annotation must be 3");

		Triplet<String, String, String> annotate3 = new Triplet<String, String, String>(lst[0], lst[1], lst[2]);
		return annotate3;
	}

	private void RecordEventDistanceFeature(String input) throws Exception {
		Quartet<String, String, String, String> annotate4 = Record4(input);
		if(annotate4 != null)
		{
			_eventDistanceTPInstances.add(annotate4);			
		}
	}

	private void RecordEventPerpendicularFeature(String input) throws Exception {
		Quartet<String, String, String, String> annotate4 = Record4(input);
		if(annotate4 != null)
		{
			_eventPerpendicularTPInstances.add(annotate4);			
		}
	}

	private void RecordEventParallelFeature(String input) throws Exception {
		Quartet<String, String, String, String> annotate4 = Record4(input);
		if(annotate4 != null)
		{
			_eventParallelTPInstances.add(annotate4);			
		}
	}

	private void RecordEventIntersectFeature(String input) throws Exception {
		Quartet<String, String, String, String> annotate4 = Record4(input);
		if(annotate4 != null)
		{
			_eventIntersectTPInstances.add(annotate4);			
		}
	}

	/*
	 * Example 0003,0,1
	 */
	private Quartet<String, String, String, String> Record4(String input) throws Exception {
		String parseStr = input;

		if (ContainComment(input)) // remove comment
		{
			int index = input.indexOf("#");
			if (index == 0)
				return null;
			parseStr = input.substring(0, index - 1);
		}

		parseStr.replaceAll("\\s+", "");
		String[] lst = parseStr.split(",");
		if (lst.length != 4) {
			throw new Exception("the length of annotation must be 3");
		}

		Quartet<String, String, String, String> annotate4 = new Quartet<String, String, String, String>(lst[0], lst[1],
				lst[2], lst[3]);
		return annotate4;
	}

	public void PrintConceptAnnotationStatistics() {
		System.out.println("compose: " + _composeTPInstances.size());
		System.out.println("merge: " + _mergeTPInstances.size());
		System.out.println("event distance: " + _eventDistanceTPInstances.size());
		System.out.println("event perpendicular: " + _eventPerpendicularTPInstances.size());
		System.out.println("event parallel: " + _eventParallelTPInstances.size());
		System.out.println("event intersect: " + _eventIntersectTPInstances.size());
	}
	
	public void PrintProblemAnnotationStatistics(){
		System.out.println("annotated problems: " + AnnotatedProblemIndexLst.size());
	}

	public boolean ChangeState(String strLine) {
		if (strLine.startsWith(ComposeStart)) {
			CurrentState = ComposeStart;
		} else if (strLine.startsWith(MergeStart)) {
			CurrentState = MergeStart;
		} else if (strLine.startsWith(EventStart)) {
			CurrentState = EventStart;
		} else if (strLine.startsWith(DistanceStart)) {
			CurrentState = DistanceStart;
		} else if (strLine.startsWith(PerpendicularStart)) {
			CurrentState = PerpendicularStart;
		} else if (strLine.startsWith(ParallelStart)) {
			CurrentState = ParallelStart;
		} else if (strLine.startsWith(IntersectStart)) {
			CurrentState = IntersectStart;
		} else {
			return false;
		}
		return true;
	}
	
	/*
	 * Main entry to generate the training and testing data toward the classifier
	 */
	public void CombineAnnotationAndFeature() throws Exception
	{
		if(AnnotatedProblemIndexLst.size() == 0)
		{
			LoadConceptAnnotation();		
			LoadAnnotatedProblems();			
		}
			
		ArrayList<String> preAnnotatedproblems = AnnotationIOReader.getInstance().AnnotatedProblemIndexLst;
		
		IOUtils.FlushToFile(FeatureIO.ComposeFeatureFile);
		IOUtils.FlushToFile(FeatureIO.MergeFeatureFile);
		IOUtils.FlushToFile(FeatureIO.EventDistanceFeatureFile);
		IOUtils.FlushToFile(FeatureIO.EventIntersectFeatureFile);
		IOUtils.FlushToFile(FeatureIO.EventParallelFeatureFile);
		IOUtils.FlushToFile(FeatureIO.EventPerpendicularFeatureFile);
		IOUtils.FlushToFile(FeatureIO.EventTangentFeatureFile);
					
		for(String annotateProblemIndex : preAnnotatedproblems)
		{
  			Pipeline.getInstance().CallPipelinePerProblem(annotateProblemIndex);
  			Pipeline.getInstance().Reset();		        	  
		}
	}
	
}
