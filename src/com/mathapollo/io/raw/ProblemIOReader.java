package com.mathapollo.io.raw;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ProblemIOReader {

	private static ProblemIOReader instance = null;
	
	private ArrayList<MathProblem> problems;
	
	public ArrayList<MathProblem> GetProblems()
	{
		return problems;
	}
	
	protected ProblemIOReader(){
		problems = new ArrayList<MathProblem>();
		LoadProblems();
	}

	public static ProblemIOReader getInstance(){
		if(instance == null)
		{
			instance = new ProblemIOReader();
		}
		return instance;
	}
	
	public MathProblem Read(String id)
	{
		if(id == null) return null;
		Iterator<MathProblem> iter = problems.iterator();
		
		while(iter.hasNext()){
			MathProblem curr = iter.next();
			if(curr.id.equals(id))
			{
				return curr;
			}
		}
		return null;
	}
	
	private void LoadProblems() {
		String filePath = "MathProblem-Dataset/Problems.json";

		JSONParser parser = new JSONParser();

		try {
			Object obj = parser.parse(new FileReader(filePath));

			JSONArray array = (JSONArray) obj;

			@SuppressWarnings("unchecked")
			Iterator<JSONObject> iterator = array.iterator();
			while (iterator.hasNext()) {
				JSONObject jo = iterator.next();
				if (jo == null)
					throw new Exception("cannot be null");
				MathProblem mp = new MathProblem();

				String id = (String) jo.get("id");
				String problem = (String) jo.get("problem");
				
				problem = problem.replaceAll("[:]", "");

				mp.id = id;
				mp.problem = problem;

				problems.add(mp);
			}
		} catch (Exception e) {

		}

	}
}
