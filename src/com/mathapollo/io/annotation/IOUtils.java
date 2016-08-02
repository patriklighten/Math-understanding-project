package com.mathapollo.io.annotation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class IOUtils {
		
	public static boolean AreTwoSameDoubleArrays(double[] array1, double[] array2)
	{
		if(array1.length!=array2.length)
			return false;
		for(int i = 0; i < array1.length; i++)
		{
			if(Math.abs((array1[i] - array2[i])) > 0.00000001) return false;			
		}
		return true;
	}

	public static void PrintDoubleFeatures(double[][] features)
	{
		for(double[] internal : features)
		{
			for(double d : internal)
			{
				System.out.print(d + ",");
			}
			System.out.println();
		}
	}
	

	public static void FlushToFile(String str, String fileName) throws IOException {
		BufferedWriter output = null;
		try {
			File file = new File(fileName);
			output = new BufferedWriter(new FileWriter(file));
			// output.flush();
			output.write(str);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			output.close();
		}
	}
	
	public static void FlushToFile(String fileName) throws IOException {
		BufferedWriter output = null;
		try {
			File file = new File(fileName);
			output = new BufferedWriter(new FileWriter(file));
			output.flush();
			//output.write(str);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			output.close();
		}
	}
	
	public static void AppendToFile(String oneAnnotation, String fileName) 
	{
		try {
			FileWriter file = new FileWriter(fileName, true);
			file.append(oneAnnotation);
			file.append('\n');
			file.close();
		} catch (IOException e) {
		}
	}
		
}
