package com.mathapollo.script;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import edu.illinois.cs.cogcomp.lbj.coref.decoders.BIODecoder;
import edu.illinois.cs.cogcomp.lbj.coref.decoders.BestLinkDecoder;
import edu.illinois.cs.cogcomp.lbj.coref.decoders.ExtendHeadsDecoder;
import edu.illinois.cs.cogcomp.lbj.coref.decoders.MentionDecoder;
import edu.illinois.cs.cogcomp.lbj.coref.io.loaders.DocFromTextLoader;
import edu.illinois.cs.cogcomp.lbj.coref.io.loaders.DocLoader;
import edu.illinois.cs.cogcomp.lbj.coref.ir.Mention;
import edu.illinois.cs.cogcomp.lbj.coref.ir.docs.Doc;
import edu.illinois.cs.cogcomp.lbj.coref.ir.solutions.ChainSolution;
import edu.illinois.cs.cogcomp.lbj.coref.learned.Emnlp8;
import edu.illinois.cs.cogcomp.lbj.coref.learned.MDExtendHeads;
import edu.illinois.cs.cogcomp.lbj.coref.learned.MTypePredictor;
import edu.illinois.cs.cogcomp.lbj.coref.learned.MentionDetectorMyBIOHead;

public class UIUCShallowParser {

	public static void main(String[] args) {
		
		
	    String plainTextFile = "data/test.txt";
		
		// TODO Auto-generated method stub
		 BufferedReader in = null;

		    try { in = new BufferedReader(new FileReader(plainTextFile)); }
		    catch (Exception e)
		    {
		      System.err.println("Can't open '" + plainTextFile + "' for input:");
		      e.printStackTrace();
		      System.exit(1);
		    }

		    String fullText = "";
		    for (String line = readLine(in, plainTextFile); line != null;
		         line = readLine(in, plainTextFile))
		      fullText += line + "\n";

		    // Detecting the mentions in the plain text:
				MentionDecoder mdDec =
		      new ExtendHeadsDecoder(new MDExtendHeads(),
		                             new BIODecoder(new MentionDetectorMyBIOHead()));
				MTypePredictor mTyper = new MTypePredictor();
				DocLoader loader = new DocFromTextLoader(mdDec, mTyper); // From a string
		    Doc doc = loader.loadDoc(fullText);

		    //Setting up the coreference algorithm:
		    Emnlp8 corefClassifier = new Emnlp8();
		    corefClassifier.setThreshold(-8.0);
		    BestLinkDecoder decoder = new BestLinkDecoder(corefClassifier);

		    //Applying coreference to all documents:
		    List<ChainSolution<Mention>> preds =
		      new ArrayList<ChainSolution<Mention>>();

		    ChainSolution<Mention> sol = decoder.decode(doc);
		    preds.add(sol); 
		    doc.setPredEntities(sol);
		    System.out.println(doc.toAnnotatedString(false, false, false, true));
		    
		    //System.in.read();
	}

	
	 /**
	    * Read a line from an input stream, printing an error message and
	    * terminating the program on error.
	    *
	    * @param in       The stream to read from.
	    * @param filename The name of the file that the stream is reading from.
	    * @return A line of text from the input stream without any newline
	    *         character the line may have contained at the end.
	   **/
	  private static String readLine(BufferedReader in, String filename)
	  {
	    String line = null;

	    try { line = in.readLine(); }
	    catch (Exception e)
	    {
	      System.err.println("Can't read from '" + filename + "':");
	      e.printStackTrace();
	      System.exit(1);
	    }

	    return line;
	  }
	
}
