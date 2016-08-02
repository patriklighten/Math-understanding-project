package com.mathapollo.parsing;

import java.util.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import org.antlr.v4.parse.ANTLRParser.id_return;
import org.apache.log4j.lf5.viewer.TrackingAdjustmentListener;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.experimental.theories.Theories;
import org.matheclipse.core.builtin.function.For;
import org.matheclipse.core.builtin.function.Insert;
import org.stringtemplate.v4.compiler.STParser.mapExpr_return;

import com.mathapollo.parsing.contextual.*;
import LBJ2.nlp.SentenceSplitter;
import LBJ2.nlp.Word;
import LBJ2.nlp.WordSplitter;
import LBJ2.nlp.seg.PlainToTokenParser;
import LBJ2.parse.Parser;
import LBJ2.util.Sort;
import edu.illinois.cs.cogcomp.lbj.chunk.Chunker;
import com.mathapollo.feature.*;
import com.mathapollo.feature.compose.ComposeFeature;
import com.mathapollo.feature.compose.ComposePairsOfCircle;
import com.mathapollo.feature.compose.ComposePairsOfLine;
import com.mathapollo.feature.event.distance.ComposeDistance;
import com.mathapollo.feature.event.distance.composeFeatureofDistance;
import com.mathapollo.feature.event.intersect.AlignIntersects;
import com.mathapollo.feature.event.intersect.ElementFeatureOfIntersect;
import com.mathapollo.feature.event.parallel.AlignParallel;
import com.mathapollo.feature.event.parallel.FeatureOfParallel;
import com.mathapollo.feature.event.perpendicular.AlignPerpendicular;
import com.mathapollo.feature.event.perpendicular.FeatureOfPerpendicular;
import com.mathapollo.feature.merge.MergeFeature;
import com.mathapollo.io.Chunkers;
import com.mathapollo.io.ConceptInfo;
import com.mathapollo.io.ConceptInstances;
import com.mathapollo.io.ProblemModule;
import com.mathapollo.io.WordFeatureInfo;
import com.mathapollo.io.annotation.IOUtils;
import com.mathapollo.io.raw.MathExprProblem;
import com.mathapollo.io.raw.MathExprProblemWord;
import com.mathapollo.io.raw.MathProblem;
import com.mathapollo.io.raw.ProblemIOReader;
import com.mathapollo.knowledge.concept.*;
import com.mathapollo.knowledge.contract.ConceptMock;
import com.mathapollo.parse.expression.ExprPipeline;
import com.mathapollo.parsing.InstatiateConcept.*;

import com.mathapollo.parsing.merge.*;
import com.mathapollo.parsing.EventandRelation.*;

public class Pipeline {

	private static Pipeline _instance = null;
	public int line_index = 0;
	public int Point_index = 0;
	public int Slope_index = 0;
	public int Quant_index = 0;
	public int cd_index = 0;
	public int equation_index = 0;
	public int circle_index = 0;

	private static ConceptMock _conceptBase;

	protected Pipeline() {
		_conceptBase = new ConceptMock();
	}

	public static Pipeline getInstance() {
		if (_instance == null) {
			_instance = new Pipeline();
		}
		return _instance;
	}
	
	public static String tempfilename = "data/temp.txt";

	public void Reset()
	{
		_instance = new Pipeline();
	}

	public void CallPipelinePerProblem(String problemId) {
		// Raw Problem Data Loading
		MathProblem mp = ProblemIOReader.getInstance().Read(problemId);

		// Math Expression Parsing Pre-processing
		MathExprProblem mep = ExprPipeline.getInstance().LoadProblem(mp);
				
		 System.out.println("**********Math Expression Parsing");
		 System.out.println(mep.toString());
		 System.out.println("**********Math Expression Parsing");

		
		ProblemModule pm = null;

		try {
			pm = Pipeline.getInstance().Preprocessed(mep);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("***********print concepts:**********");

		// get expression word index and load the expression to the pm, for example, point value, equation value 
		Pipeline.getInstance().Get_expression_wordindex(pm,mep);
		
		// load event
	    Pipeline.getInstance().loadevent(pm);
		
		Pipeline.getInstance().ExtractConcept(pm);
								
		/* remove the event that is part of a concept or concept is part of event*/
		// for example, intersect point, 
       for(int i=0,j=0,flag=0;i<pm.EventChain.size();i++)
       {
    	   ConceptInfo eventconcept= pm.EventChain.get(i);
    	   if(flag==0)
    	   {
    		   j=0;
    	   }
    	  while(j<pm.ConceptChain.size())
    	   {
    		  ConceptInfo concept=pm.ConceptChain.get(j);
    		  
    		  
    		   if(concept.concept_code.contains(eventconcept.concept_code)&&eventconcept.Firstmention_index==concept.Firstmention_index)
    		   {
    			   
    			   pm.EventChain.remove(i);
    			   j++;
    			   flag=1;
    			   i--;
    			   break;
    		   }
    		   j++;
    		   
    		   
    	   }
       }
		
       for(int i=0,j=0,flag=0;i<pm.ConceptChain.size();i++)
       {
    	ConceptInfo concept=pm.ConceptChain.get(i);
    	 if(flag==0)
    	 {
    		 j=0;
    	 }
    	  while(j<pm.EventChain.size())
    	  {
    		  ConceptInfo eventconcept=pm.EventChain.get(j);
    		  if(eventconcept.concept_code.contains(concept.concept_code)&&concept.Firstmention_index==eventconcept.Firstmention_index)
   		   {
   			   pm.ConceptChain.remove(i);
   			   flag=1;
   			   j++;
   			   i--;
   			   break;
   		   }
    		  
    		  j++;
    	  }
       }
       
       /* get the concept instance*/
       Pipeline.getInstance().ExtractConceptInstance(pm);// extract all
       
       /* get event concept instance*/
       loadevent_instance(pm);
		// get point value chain; 
		
		
		
		
	
		
		// get equation value chain;  
		
		
		// possible concept
		// instance
		InstantiateAll_Instance loadconceptTo_Higherconcept = new InstantiateAll_Instance();
		// map value to point test
		MapValue mapvaluetopt = new MapValue();
		// mapvaluetopt.mapvaluetoPoint(pm);// also load some cd values
		// information for the problem
     
		 pm.PrintChunkerChain();
		// pm.PrintConceptChain();

		// if(Pipeline.annotationMode)
		// {
		// Pipeline.getInstance().AnnotateCurrentProblem(mp.id, pm);
		// }

		//System.out.println("\n");

		ComputeQuantity computequant = new ComputeQuantity();

		for (ConceptInfo concepts : pm.ConceptChain) {
			int quantity = computequant.ComputeQuantity1(pm, concepts);

			System.out.println(concepts.toString() + quantity);
		}

		System.out.println("***********print the unknown concept quantity:**********");
		// System.out.print("\n");
		Pipeline.getInstance().loadUnknowQuantity(pm);
		// *LoadConceptInstance loadinstance= new LoadConceptInstance();
		// load unknown instance
		// *ArrayList<QuantityInfo> unknownquantity=
		// loadinstance.findUnknowAfterImperative(pm);
		/*
		 * for(int i=0;i<unknownquantity.size();i++) {
		 * System.out.println(unknownquantity.get(i).quantitytoken); }
		 */                                                              
            
		
         
		// sort index, give the global index to them:
		Pipeline.getInstance().SortConcept(pm);
		// get_globalindex should be after gethybridconcept 
		// Pipeline.getInstance().Get_globalindex_forConceptinstance(pm);
      
		                                                                                                    
		
		
		// rule based map near by tag and value to concept 
		RulebaseMap(pm);
		
		/* get hybrid concept */
		ArrayList<ConceptInfo> hybridconceptchain= extract_hybridconceptlist(pm);
		pm.HybridConceptChain=new ArrayList<ConceptInfo>();
	    pm.HybridConceptChain=Pipeline.getInstance().extract_hybridconceptlist(pm);
	    pm.HybridConceptChain= hybridconceptchain;
		//incorporate conceptchain with hybrid conceptchain, and sort them 
	    SortHybridConceptlist(pm);// sort and buid the hybridconceptlist need the original conceptchain 
	    
	    /* deep copy the hybridconceptlist to the pm.conceptchain, so that they have less effect on each other*/
	    ArrayList<ConceptInfo> ConceptChain1=new ArrayList<ConceptInfo>();
	    for(ConceptInfo concept:pm.ConceptChain)
	    {
	    	ConceptChain1.add(concept);
	    }
	    
	    for(int i=0,j=0;i<pm.HybridConceptChain.size();i++)
	    {
	    	ConceptInfo hybridconcept= pm.HybridConceptChain.get(i);
	    	while(j<ConceptChain1.size())
	    	{
	    		ConceptInfo conceptinfo= ConceptChain1.get(j);
	    		
	    		if(hybridconcept.concept_code.equals(conceptinfo.concept_code)&&hybridconcept.NNtoken_index==conceptinfo.NNtoken_index)
	    		{
	    			ConceptChain1.get(j).global_index= hybridconcept.global_index;
	    			ConceptChain1.get(j).global_index2= hybridconcept.global_index2;
	    			
	    			j++;
	    			break;
	    		}
	    		
	    		j++;
	    	}
	    }
	    
	    for(int i=0;i<pm.ConceptChain.size();i++ )
	    {
	         pm.ConceptChain.get(i).global_index= ConceptChain1.get(i).global_index;
	         pm.ConceptChain.get(i).global_index2= ConceptChain1.get(i).global_index2;
	         

}
	   // updata event concept 
	    
	   
	    
	    // get the global index for the conceptinstance after building the hybridconcept
	    Pipeline.getInstance().Get_globalindex_forConceptinstance(pm);
	    
	    // pm
		
		
	
	    
	    // compute single concept feature
		SingleFearture singleconceptfeature = new SingleFearture();
		singleconceptfeature.computeFeature(pm);
		
		
		System.out.println();
		System.out.println("**************print pre annotation problem:******************");
		Pipeline.getInstance().printpreannotation(pm);
		System.out.println();
		
		/*
		 * for the compose, the element concept is in first dimension, while for
		 * the event compose the LMC concept is in the first dimension
		 */		
		
		// get distance alignment feature
		ComposeDistance distanceAlignment =  new ComposeDistance(pm);
		
		/* get intersect alignment feature */
		AlignIntersects intersectAlignment = new AlignIntersects(pm);
		// get perpendicular alignment feature
		AlignPerpendicular perpendicularAlignment = new AlignPerpendicular(pm);		
		AlignParallel parallelAlignment = new AlignParallel(pm);
		
		//TODO Pan: tangent
		
		// load lower concept to higher concept
		// Pipeline.getInstance().LoadquantityToconcept(pm); // instantiate the
		// concept instance
		// // before load them to higher
		// // concept
		// Pipeline.LoadConceptTOHigherConcept(pm); // this function is supposed
		// to replace the following functions

//		computeFeature computefeature = new computeFeature();
//		// MergeFeature mergeFeature1=new MergeFeature();
//		MapFeature mapFeature1 = new MapFeature();
//		ComposeFeature composeFeature1 = new ComposeFeature();
		
		
		
		Pipeline.getInstance().ComputeFeatures(mp, pm);

		// mergeFeature1=computefeature.computeMergefeature(pm,pm.ConceptInstances.LineInstances.get(0).Line_ConceptInfo,pm.ConceptInstances.LineInstances.get(1).Line_ConceptInfo);
		// mapFeature1=computefeature.computemapfeature(pm,
		// pm.ConceptInstances.LineInstances.get(0).Line_ConceptInfo,
		// pm.ConceptInstances.LineInstances.get(2).Line_ConceptInfo);
		// composeFeature1=computefeature.composefeaturecomputing(pm,
		// pm.ConceptInstances.LineInstances.get(0).Line_ConceptInfo,
		// pm.ConceptInstances.PointInstances.get(0).Point_ConceptInfo);
		// Pipeline.MergeLine(pm);

		// Pipeline.printline_lmc(pm.ConceptInstances.LineInstances.get(0));

		// loadconceptTo_Higherconcept.loadPointsInstance(pm);
		// loadconceptTo_Higherconcept.loadSlopesInstance(pm);
		// loadconceptTo_Higherconcept.LoadEquationInstance(pm);	
	}

	
	public void Get_expression_wordindex(ProblemModule pm, MathExprProblem mep)
	{
		int word_index=0;
		int j=0;
		String str="";
		for(MathExprProblemWord mathword:mep.words)
		{
			str="";
			
		 if(mathword.IsMathExpression==true&&mathword.MathInstance!=null)
		 {
		  while(j<pm.FeatureChain.size())   
		  {
			 WordFeatureInfo wordinfo=pm.FeatureChain.get(j);
			  j++;
		    if(!mathword.Token.contains(wordinfo.token))
		    {
		    	str="";
		    }
			if( str.equals(""))
			{
				if(mathword.Token.charAt(0)==wordinfo.token.charAt(0))
			   {
					str+=wordinfo.token;
			   
			   word_index=wordinfo.token_index;
                }
			}
			else if( mathword.Token.contains(wordinfo.token))
			{
			   str+=wordinfo.token;
			  
			}
			
		    // add the concept value to the problem module 
			if(mathword.Token.equals(str))
			{
			   mathword.wordinfo_index=word_index;
			   if(mathword.concept_code!=null)
			   {
				   if(mathword.concept_code.equals("point_value"))
				   pm.PointValueChain.add(mathword);
				   else if(mathword.concept_code.equals("line_value"))
				   {
					   pm.EquationValueChain.add(mathword);
				   }
				   else if(mathword.concept_code.equals("circle_value"))
				   {
					   pm.EquationValueChain.add(mathword);
				   }
				   
			   }
			 
				   str="";
			   break;
			}
		     
		  }
		 }
		}
	}
	
	
	
	
	
	public void Train()
	{
		TrainComposeClassifier();
		TrainMergeClassifier();
		TrainEventClassifier();
	}
	
	private void TrainComposeClassifier()
	{
		
	}
	
	private void TrainMergeClassifier()
	{
		
	}
	
	private void TrainEventClassifier()
	{
		
	}
		
	public void Test()
	{
		
	}

	// deep copy of an class instance concept2 to concept1
	public void copy_concept(ConceptInfo concept1, ConceptInfo concept2) {
		concept1.chunk_info = concept2.chunk_info;
		concept1.concept_code = concept2.concept_code;
		concept1.concept_tag = concept2.concept_tag;
		concept1.conceptFeature = concept2.conceptFeature;
		concept1.global_index = concept2.global_index;
		concept1.index = concept2.index;
		concept1.NNPOS = concept2.NNPOS;
		concept1.NNtoken_index = concept2.NNtoken_index;
		concept1.Firstmention_index=concept2.Firstmention_index;
		concept1.global_index2=concept2.global_index2;

	}
	
	public void PrintPreAnnotateProblem(ProblemModule pm) {
		int concept_count = 0;

		ArrayList<String> wordlist = new ArrayList<String>();
		for (WordFeatureInfo temp_word : pm.FeatureChain) {
			wordlist.add(temp_word.token);
		}

		for (ConceptInfo temp_concept : pm.ConceptChain) {
			String tempstring = "[" + temp_concept.global_index + "]";

			wordlist.add(temp_concept.NNtoken_index + temp_concept.global_index, tempstring);
		}

		for (String str : wordlist) {
			System.out.print(str + " ");
		}

		System.out.println();
	}

	public void printpreannotation(ProblemModule pm) {
		int concept_count = 0;

		ArrayList<String> wordlist = new ArrayList<String>();
		for (WordFeatureInfo temp_word : pm.FeatureChain) {
			wordlist.add(temp_word.token);
		}

		for (ConceptInfo temp_concept : pm.HybridConceptChain) {
			String tempstring = "[" + temp_concept.global_index + "]";

			wordlist.add(temp_concept.NNtoken_index + temp_concept.global_index, tempstring);

		}

		for (String str : wordlist) {
			System.out.print(str + " ");
		}

		System.out.println();
	}

	/*
	 * 1. UIUC Shallow Parsing
	 */
	public ProblemModule Preprocessed(MathExprProblem mep) throws Exception {

		String problem = mep.problem.problem;

		// 0.Flush temp file
		try {
			IOUtils.FlushToFile(problem, tempfilename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ProblemModule pm = new ProblemModule();
		// InstantiateAll_Instance InstantiateConcept= new
		// InstantiateAll_Instance(pm);
		// TODO Auto-generated method stub
		int chunk_index = 0;// count the chunk

		// load information
		Chunker chunker = new Chunker();
		// coreference
		// CorefPlainText coreplaintexttest= new CorefPlainText();
		// SentenceSplitter sentence=(new SentenceSplitter(filename));
		// File file = new File(filename); //
		ArrayList<WordFeatureInfo> wordfeatureinfo = new ArrayList<WordFeatureInfo>();
		WordFeatureInfo temp_wordinfo = new WordFeatureInfo();
		int sentence_index = 0;
		int token_index = 0;
		String temp_partofspeech = "";
		String temp_token = "";
		Chunkers temp_chunk = new Chunkers();
		ArrayList<Chunkers> Chunker_Chain = new ArrayList<Chunkers>();
		int is_a_Chunk = 0; // flag 0 no chunk so need not load chunk, otherwise
							// load a chunk
		String temp_chunkPos = "";

		SentenceSplitter ss = new SentenceSplitter(tempfilename);
		WordSplitter ws = new WordSplitter(ss);
		Parser parser = new PlainToTokenParser(ws);

		String previous = "";
		// parsing
		for (Word w = (Word) parser.next(); w != null; w = (Word) parser.next()) {
			String prediction = chunker.discreteValue(w);
			if (prediction.startsWith("B-")
					|| prediction.startsWith("I-") && !previous.endsWith(prediction.substring(2))) {
				// System.out.print("[" + chunk_index + prediction.substring(2)
				// + " ");
				is_a_Chunk = 1;
				temp_chunkPos = prediction.substring(2);
			}
			// System.out.print("(" + w.partOfSpeech + " " + w.form + ") ");
			// record the token
			temp_partofspeech = w.partOfSpeech;
			temp_token = w.form;
       
			// record the token and its posinformation
            
			// Align with Math Expression Parsing
                
			boolean partOfMath = ExprPipeline.getInstance().IsPartOfMath(temp_token);
			if (partOfMath) {
			   
				// System.out.print(" POM "); //Part of Math
			}
			// record the token and its posinformation

			temp_wordinfo = new WordFeatureInfo(temp_token, temp_partofspeech, token_index, sentence_index);
			token_index += 1;

			wordfeatureinfo.add(temp_wordinfo);

			// record chunker chain information only if there is a chunker
			if (is_a_Chunk == 1)
				temp_chunk.words_info.add(temp_wordinfo);

			// refresh the temp_wordinfo and sentence index
			temp_wordinfo = new WordFeatureInfo();
			// update the sentence index
			if (temp_partofspeech.equals(".") || temp_token.equals("?"))// new
																		// sentence
			{
				sentence_index += 1;
			}
			if (!prediction.equals("O") && (w.next == null || chunker.discreteValue(w.next).equals("O")
					|| chunker.discreteValue(w.next).startsWith("B-")
					|| !chunker.discreteValue(w.next).endsWith(prediction.substring(2)))) {
				// end of a chunk
				// if there is a chunker, load the chunk to chunk chain
				if (is_a_Chunk == 1) {
					temp_chunk.chunk_index = chunk_index;
					temp_chunk.chunk_Pos = temp_chunkPos;
					temp_chunk.sentence_index = sentence_index;
					// update the Chunker_chain at the end of a chunk
					Chunker_Chain.add(temp_chunk);
					is_a_Chunk = 0;
				}
				temp_chunk = new Chunkers();
				chunk_index += 1;
				// System.out.print("] ");
			}
			// if (w.next == null)
			// System.out.println();

			previous = prediction;
		}
		pm.FeatureChain = wordfeatureinfo;
		pm.ChunkerChain = Chunker_Chain;

		return pm;
	}

	public void ExtractConcept(ProblemModule pm) {
		for (Chunkers ch : pm.ChunkerChain) {
			// pattern match chunker with knowledge base to detect concept.
			// string matching

			String pattern = _conceptBase.ContainToken(ch.GetTokens());

			// record the NN part of the concept, in general a concept is NP,
			// and contain a NN or NNS,
			if (pattern != null) {
				pattern = pattern.toLowerCase();
				// find the last subpattern
				String[] subpatterns = pattern.split(" ");
				String last_subpattern = subpatterns[subpatterns.length - 1];
                String first_subpattern= subpatterns[0]; 
				ConceptInfo ci = new ConceptInfo();
				ci.chunk_info = ch;
				ci.concept_code = pattern;
				ci.if_event_concept=0;
				// ci.conceptFeature=new conceptFeatureNode(pattern);
				// find the location of the first word of concept
				int index_of_firstmentioned = 0;
				int index_of_lastmentioned = 0;
				for (WordFeatureInfo word0 : ch.words_info) {
					if (word0.token.toLowerCase().contains(first_subpattern)) {
						index_of_firstmentioned = word0.token_index;
						ci.Firstmention_index= index_of_firstmentioned;
						break;
					}
				}
				for (WordFeatureInfo word0 : ch.words_info) {
					if (word0.token.toLowerCase().contains(last_subpattern)) {
						index_of_lastmentioned = word0.token_index;
                         break; 
					}
				}
				

				for (WordFeatureInfo word1 : ch.words_info) {
					if (word1.token_index == index_of_lastmentioned) {
						if (word1.POS_Tag.contains("NN"))//
						{
						
							ci.NNPOS = word1.POS_Tag;// to judge if the NN is
						// NNtoken_index is the last mention of the concept 
							// singular or plural
							ci.NNtoken_index = word1.token_index;// spatial
																	// distribution
																	// of the
																	// token,
																	// word

						} else// some times the concept is verb
						{
							ci.NNPOS = word1.POS_Tag;// to judge if the NN is
														// singular or plural
							ci.NNtoken_index = word1.token_index;// spatial
																	// distribution
																	// of the
																	// token,
																	// word
						}

					}
				}
				ArrayList<MathExprProblemWord> temp_expressionlist= new ArrayList<MathExprProblemWord>();
						
				for( MathExprProblemWord mathword: pm.EquationValueChain)
				{
					temp_expressionlist.add(mathword);
				}
				for( MathExprProblemWord mathword: pm.PointValueChain)
				{
					temp_expressionlist.add(mathword);
				}
				
				//temp_expressionlist.addAll(pm.EquationValueChain);
				
				if (pm.FeatureChain.size() > index_of_lastmentioned + 1)
					if (ci.chunk_info.words_info.contains(pm.FeatureChain.get(index_of_lastmentioned + 1))) {
						if(ci.NNPOS.charAt(ci.NNPOS.length()-1)!='S')
						{
							String temp_str=pm.FeatureChain.get(index_of_lastmentioned + 1).token; 
							
							if((temp_str.charAt(0)>='A'&&temp_str.charAt(0)<='Z')||(temp_str.charAt(0)>='a'&&temp_str.charAt(0)<='z'))
							{
								int index= pm.FeatureChain.get(index_of_lastmentioned + 1).token_index;
								int existflag= 0; 
								for(MathExprProblemWord mathword: temp_expressionlist)
								{
									if(mathword.wordinfo_index==index)
									{
										existflag=1;
										break;
									}
										
								}
								
								for(ConceptInfo eventconcept:pm.EventChain)
								{
									if(eventconcept.NNtoken_index-index_of_lastmentioned==1)
									{
										existflag=1;
										break;
									}
								}
								
								
								if( existflag==0)
								{
								ci.concept_tag = pm.FeatureChain.get(index_of_lastmentioned + 1).token;
								
								ci.NNtoken_index= pm.FeatureChain.get(index_of_lastmentioned + 1).token_index;
								
								
								pm.TagList.add(pm.FeatureChain.get(index_of_lastmentioned + 1));
							  
								}
								
								existflag= 0; 
								}
							//else
								//ci.concept_tag="";	
						}
					}
				pm.ConceptChain.add(ci);
			}

		}
	}

	// extract variable 
   public  ArrayList<ConceptInfo> extract_hybridconceptlist(ProblemModule pm)
   {
	   ArrayList<ConceptInfo> hybrid_conceptlist= new ArrayList<ConceptInfo>();
	   ArrayList<ConceptInfo> taglist=new ArrayList<ConceptInfo>();
	   int start_index=0;
	   ConceptInfo temp_concept=new ConceptInfo();
	   int existflag=0;
	   for(ConceptInfo concept:pm.ConceptChain)
	   {
		   // range of word the chunk that concept in covers
		   int wordindex1=concept.chunk_info.words_info.get(0).token_index;
		   int wordindex2=concept.chunk_info.words_info.get(concept.chunk_info.words_info.size()-1).token_index;
		   
		   if(concept.concept_tag!=null&&concept.chunk_info.chunk_index+1<pm.ChunkerChain.size())
		   {
		      start_index=pm.ChunkerChain.get(concept.chunk_info.chunk_index+1).words_info.get(0).token_index;
		      for( int i=start_index;i<pm.FeatureChain.size();i++)
		      {
		    	  WordFeatureInfo temp_word=pm.FeatureChain.get(i);
		    	  // if the word token is equal to the concept_tag && the word is not before any concept
		    	  if(pm.FeatureChain.get(i).token.equals(concept.concept_tag))
		    	  {
		    		 if(pm.FeatureChain.get(i).token_index>wordindex2)
		    		 if(if_thetag_CDof_concept(pm, temp_word.token_index)==0)
		    		 {
		    			 temp_concept= new ConceptInfo();
		    			 temp_concept.concept_code=concept.concept_code+"_tag";// for example: circle_tag
		    			 temp_concept.concept_tag=concept.concept_tag;
		    			 temp_concept.NNtoken_index=temp_word.token_index;
		    			 
		    			 // if there is not same tag concept in the taglist 
		    			 for(ConceptInfo conceptinfo:taglist)
		    			 {
		    				 if(conceptinfo.NNtoken_index==temp_word.token_index)
		    					 existflag=1;
		    			 }
		    			 
		    			 // pm.TagList is non value Tag, and the tag is just is near a concept 
		    			if(pm.TagList.contains(temp_word))
		    			  existflag=1;
		    			 if(existflag==0)
		    			 {
		    			 taglist.add(temp_concept);
		    			 pm.TagList.add(temp_word);
		    			 }
		    			 
		    			 existflag=0;
		    		    
		    		 }
		    		                                                                    
		    	  }
		    		  
		      }
		   }
   
   }
	   for(MathExprProblemWord mathword:pm.PointValueChain)
	   {
		   start_index=mathword.wordinfo_index;
		      for( int i=start_index;i<pm.FeatureChain.size();i++)
		      {
		    	  WordFeatureInfo temp_word=pm.FeatureChain.get(i);
		    	  // if the word token is equal to the concept_tag && the word is not before any concept
		    	  if(pm.FeatureChain.get(i).token.equals(mathword.tag)&&pm.FeatureChain.get(i).token_index>mathword.wordinfo_index)
		    	  {
		    		 if(if_thetag_CDof_concept(pm, temp_word.token_index)==0)
		    		 {
		    			 temp_concept= new ConceptInfo();
		    			 temp_concept.concept_code="point_tag";// for example: circle_tag
		    			 temp_concept.concept_tag=mathword.tag;
		    			 temp_concept.NNtoken_index=temp_word.token_index;
		    			 // if there is not same tag concept in the taglist 
		    			 for(ConceptInfo conceptinfo:taglist)
		    			 {
		    				 if(conceptinfo.NNtoken_index==temp_word.token_index)
		    					 existflag=1;
		    			 }
		    			 // if the tag is just the tag of a point value, point A(2,6), or it has been in the pm.TagList 
		    			 if(mathword.wordinfo_index==temp_word.token_index&&pm.TagList.contains(temp_word))
		    				 existflag=1;
		    			 if(existflag==0)
		    			 {
		    			
		    			 taglist.add(temp_concept);
		    			// pm.TagList.add(temp_word);   
		    			 }
		    			 
		    			 existflag=0;
		    			 
		    		 }
		    		                                                                    
		    	  }
		    		  
		      }
	   }
	   
	  
	   for(MathExprProblemWord mathword:pm.PointValueChain)
	   {
		   temp_concept=new ConceptInfo();
		   temp_concept.concept_code="point_value"; 
		   temp_concept.concept_tag=mathword.tag;
		   temp_concept.NNtoken_index=mathword.wordinfo_index;
		   if(mathword.if_mapped==0)
		   {
		      hybrid_conceptlist.add(temp_concept); 
		   }
	   }
	   
	   for(MathExprProblemWord mathword:pm.EquationValueChain)
	   {
		   temp_concept=new ConceptInfo();
		   temp_concept.concept_code=mathword.concept_code;
		   temp_concept.concept_tag=mathword.tag;
		   temp_concept.NNtoken_index=mathword.wordinfo_index;
		   if(mathword.if_mapped==0)
			   {
			   hybrid_conceptlist.add(temp_concept);
			   
			   }
	   }
	   
	  // hybrid_conceptlist.addAll(pm.ConceptChain);
	   hybrid_conceptlist.addAll(taglist);
	   
	   // get  chunkinfo for concepts in the hybrid_conceptlist
	   Collections.sort(hybrid_conceptlist, ConceptInfo.conceptIndexcomparator);
	   
	   for(ConceptInfo concept: hybrid_conceptlist)
	   {
		   
		   int i=0;
		   int startindex1=0;
		   int endindex1=0;
		   
	    while(i<pm.ChunkerChain.size())
	   {
		   startindex1=pm.ChunkerChain.get(i).words_info.get(0).token_index;
		   endindex1= startindex1+pm.ChunkerChain.get(i).words_info.size()-1;
		   if(concept.NNtoken_index>=startindex1&&concept.NNtoken_index<=endindex1)
		   {
			   concept.chunk_info=pm.ChunkerChain.get(i);
			   
			  // i=pm.ChunkerChain.size();
			   i=concept.chunk_info.chunk_index;
			   break;
		   }
		   else // if the word not in any chunk
		   {
			   if(i<pm.ChunkerChain.size()-1)
			   {
				   if(concept.NNtoken_index>endindex1&&concept.NNtoken_index<pm.ChunkerChain.get(i+1).words_info.get(0).token_index)
				   {
					   concept.chunk_info=pm.ChunkerChain.get(i+1);
					   i=concept.chunk_info.chunk_index;
					   break;
				   }
			   }
		   }
		   i++;
	   }
	   
	   
	   }
	   return hybrid_conceptlist;
   }
	
      public int if_thetag_CDof_concept(ProblemModule pm, int index)
      {
    	  int flag=0;
    	  for(ConceptInfo concept:pm.ConceptChain)
    	  {
    		  int wordindex1=concept.chunk_info.words_info.get(0).token_index;
    		  int wordindex2=concept.chunk_info.words_info.get(concept.chunk_info.words_info.size()-1).token_index;
    		  if(concept.if_event_concept==0&&concept.Firstmention_index-index==1&&concept.chunk_info.words_info.get(0).token_index==index)
    		  {
    			  // if the token is in the same chunk with the concept, it is possible the CD of concept only if it located in the same chunk with the concept 
    			
    				flag=1;
    			
    			  break;
    			
    		  }
    		  // if it is an immediate tag of a concept, we also not to record it
    		  if(concept.if_event_concept==0&&(concept.NNtoken_index-index==1||concept.NNtoken_index-index==0)&&index>=wordindex1&&index<=wordindex2)
    		  {
    			  flag=1;
    			  break;
    		  }
    		  
    	  }
    	  return flag;
      }
	
      
	/*
	 * public ConceptInfo Circle_ConceptInfo; public Pointconcept
	 * centerpoint=null; public Value radius=null; public
	 * ArrayList<Pointconcept> on_points=null;//points on the circle
	 * 
	 * 
	 * public Equation equation=null; public Equation standerd_form=null; public
	 * Circleconcept() {
	 * 
	 * }
	 */

	// extract all of possible concept instance from the problem before the
	// merge same instance
	public void ExtractConceptInstance(ProblemModule pm) {
		int circleindex = 0;
		int lineindex = 0;
		int pointindex = 0;
		// int centerindex=0;
		// int oringinindex=0;
		int radiusindex = 0;
		int equationindex = 0;
		int slopeindex = 0;

		ArrayList<ConceptInfo> conceptlist = new ArrayList<ConceptInfo>();
		ComputeQuantity computeQuant = new ComputeQuantity();
		ConceptInstances conceptInstans = new ConceptInstances();
		for (ConceptInfo conceptinfo : pm.ConceptChain) {
			if (conceptinfo.concept_code.equals("circle")) {

				int number = computeQuant.ComputeQuantity1(pm, conceptinfo);
				for (int i = 0; i < number; i++) {
					ConceptInfo newcircleinfo = new ConceptInfo();
					copy_concept(newcircleinfo, conceptinfo);
					newcircleinfo.index = circleindex;

					// add to a new concept list
					conceptlist.add(newcircleinfo);
					conceptlist.get(conceptlist.size() - 1).index = circleindex;
					Circleconcept circleinst = new Circleconcept();
					circleinst.Circle_ConceptInfo = newcircleinfo;
					circleinst.Circle_Index = circleindex;
					// circleinst.Circle_ConceptInfo.concept_code

					circleindex++;
					conceptInstans.CircleInstances.add(circleinst);

				}

			}
			if (conceptinfo.concept_code.equals("line")||conceptinfo.concept_code.equals("y-axis")||conceptinfo.concept_code.equals("x-axis")||conceptinfo.concept_code.equals("chord")||conceptinfo.concept_code.equals("line segment")) {
				int number = computeQuant.ComputeQuantity1(pm, conceptinfo);
				for (int i = 0; i < number; i++) {
					ConceptInfo newlineinfo = new ConceptInfo();
					copy_concept(newlineinfo, conceptinfo);
					newlineinfo.index = lineindex;

					// add the concept info to a new concept list
					conceptlist.add(newlineinfo);

					Lineconcept lineInst = new Lineconcept();
					lineInst.Line_ConceptInfo = newlineinfo;
					lineInst.Line_Index = lineindex;

					conceptlist.get(conceptlist.size() - 1).index = lineindex;
					lineindex++;
					conceptInstans.LineInstances.add(lineInst);

				}

			}

			// including, point, midpoint, central point, for this case, the
			// point(s) may be single or multiple point,
			// so that we infer the number of point instance
			if (conceptinfo.concept_code.contains("point") && !conceptinfo.concept_code.contains("cent")||conceptinfo.concept_code.contains("endpoint")||conceptinfo.concept_code.contains("y-intercept")||conceptinfo.concept_code.contains("x-intercept")) {
				// including the multiple points case
				int number = computeQuant.ComputeQuantity1(pm, conceptinfo);

				for (int i = 0; i < number; i++) {
					ConceptInfo newpointinfo = new ConceptInfo();
					copy_concept(newpointinfo, conceptinfo);
					newpointinfo.index = pointindex;

					// add to new conceptlist
					conceptlist.add(newpointinfo);

					Pointconcept pointinst = new Pointconcept();
					pointinst.Point_ConceptInfo = newpointinfo;
					pointinst.Point_Index = pointindex;

					conceptlist.get(conceptlist.size() - 1).index = pointindex;
					pointindex++;
					conceptInstans.PointInstances.add(pointinst);

				}
			// add point label 
				
				
				
			}
			// origin, center also consider as point, but center always merge
			// with other point instance, like point B, origin
			// conceptinfo. conceptcode including the specific information
			if ((conceptinfo.concept_code.contains("center") || conceptinfo.concept_code.contains("central"))
					&& !conceptinfo.concept_code.contains("form")) {
				// for centers or central points case,we assume the number is
				// two, however the rules for it is rather complex, the number
				// of the points are decide by the compose( distance between,
				// centers of circles )
				int number = 1;
				if (conceptinfo.NNPOS.equals("NNS"))
					number = 2;
				for (int i = 0; i < number; i++) {
					ConceptInfo newinnfo = new ConceptInfo();
					copy_concept(newinnfo, conceptinfo);
					newinnfo.index = pointindex;// center is belong to point, so
												// we use point index to index
												// it

					// add to a conceptlist
					conceptlist.add(newinnfo);

					Pointconcept pointinst = new Pointconcept();
					pointinst.Point_ConceptInfo = newinnfo;
					pointinst.Point_Index = pointindex;

					// because center is belong to the point, so that we use
					// pointindex to index the point
					conceptlist.get(conceptlist.size() - 1).index = pointindex;
					pointindex++;
					conceptInstans.PointInstances.add(pointinst);
				}

			}
			// origin has the value information
			if (conceptinfo.concept_code.toLowerCase().equals("origin")) {
				// the origin always refer to only one origin, so the number of
				// the conept is one
				// including the multiple points case
				// int number=computePointQuant.ComputeQuantity1(pm,
				// conceptinfo);
				conceptinfo.index = pointindex;
				// add to new conceptlist

				conceptlist.add(conceptinfo);

				Pointconcept pointinst = new Pointconcept();
				pointinst.Point_ConceptInfo = conceptinfo;

				pointinst.X.unknown_variable = "known";
				pointinst.X.value = 0;
				pointinst.Y.value = 0;

				pointinst.Point_Index = pointindex;
				pointindex++;

				// conceptlist.get(conceptlist.size()-1).index
				conceptInstans.PointInstances.add(pointinst);

			}

			if (conceptinfo.concept_code.toLowerCase().equals("radius")) {
				int number = computeQuant.ComputeQuantity1(pm, conceptinfo);
				for (int i = 0; i < number; i++) {
					ConceptInfo newinfo = new ConceptInfo();
					copy_concept(newinfo, conceptinfo);
					newinfo.index = radiusindex;

					// add to concept list
					conceptlist.add(newinfo);

					Radiusconcept radius = new Radiusconcept();
					radius.radiusindex = radiusindex;

					conceptlist.get(conceptlist.size() - 1).index = radiusindex;
					radiusindex++;
					radius.radiusconceptinfo = newinfo;

					conceptInstans.RadiusInstances.add(radius);
				}
			}
			if (conceptinfo.concept_code.toLowerCase().contains("form")) {
				int number = computeQuant.ComputeQuantity1(pm, conceptinfo);

				for (int i = 0; i < number; i++) {
					ConceptInfo newinfo = new ConceptInfo();
					copy_concept(newinfo, conceptinfo);
					newinfo.index = equationindex;

					conceptlist.add(newinfo);

					Equation equainst = new Equation();
					equainst.Equation_ConceptInfo = newinfo;
					equainst.Equation_Index = equationindex;

					conceptlist.get(conceptlist.size() - 1).index = equationindex;
					equationindex++;

					conceptInstans.EquationInstances.add(equainst);
				}

			}
			if (conceptinfo.concept_code.toLowerCase().contains("equation")) {
				int number = computeQuant.ComputeQuantity1(pm, conceptinfo);
				for (int i = 0; i < number; i++) {
					ConceptInfo newinfo = new ConceptInfo();
					copy_concept(newinfo, conceptinfo);
					newinfo.index = equationindex;
					// add to concept list
					conceptlist.add(newinfo);

					Equation equainst = new Equation();
					equainst.Equation_ConceptInfo = conceptinfo;
					equainst.Equation_Index = equationindex;

					conceptlist.get(conceptlist.size() - 1).index = equationindex;
					equationindex++;
					conceptInstans.EquationInstances.add(equainst);
				}
			}
			if (conceptinfo.concept_code.toLowerCase().equals("slope")) {
				int number = computeQuant.ComputeQuantity1(pm, conceptinfo);
				for (int i = 0; i < number; i++) {
					ConceptInfo newinfo = new ConceptInfo();
					copy_concept(newinfo, conceptinfo);
					newinfo.index = slopeindex;
					// add to conceptlist
					conceptlist.add(newinfo);

					Slope slopeinstance = new Slope();
					slopeinstance.slope_info = conceptinfo;
					slopeinstance.Slope_Index = slopeindex;

					conceptlist.get(conceptlist.size() - 1).index = slopeindex;
					slopeindex++;

					conceptInstans.SlopeInstances.add(slopeinstance);
				}
			}
			// other concepts 
		
             
		} // for collect the concept in the problem

		
		pm.ConceptInstances = conceptInstans;
		pm.ConceptChain = new ArrayList<ConceptInfo>();
		pm.ConceptChain.addAll(conceptlist);
	}
	// load cd train, cd train is known value quantity, beside the equation
	// value

	// index all the concepts,geometry and event ralation concepts, so that we      
	// can sort them and compute the distance in concept                        
	                      
	public ArrayList<ConceptInfo> SortConcept(ProblemModule pm) 
	{
		// because the conceptinfo copy here is shallow copy, so the change of
		// global index will be reflected in the original class instance
		ArrayList<ConceptInfo> GeoConceptlist = pm.ConceptChain;
		ArrayList<ConceptInfo> RelationList = pm.EventChain;
		ArrayList<ConceptInfo> SortedConceptList = new ArrayList<ConceptInfo>();
		SortedConceptList.addAll(GeoConceptlist);
		SortedConceptList.addAll(RelationList);
		Collections.sort(SortedConceptList, ConceptInfo.conceptIndexcomparator);
                                                                                
		for (int i = 0; i < SortedConceptList.size(); i++) {
			SortedConceptList.get(i).global_index = i;

		}

		pm.ConceptChain = new ArrayList<ConceptInfo>();
		pm.ConceptChain = SortedConceptList;
		// loadevent_instance(pm);
		return SortedConceptList;
	}
	
    public ArrayList<ConceptInfo> GeneralSortConcept(ArrayList<ConceptInfo> conceptlist)
    {
    	ArrayList<ConceptInfo> SortedConceptList = conceptlist;
    	Collections.sort(SortedConceptList, ConceptInfo.conceptIndexcomparator);
    	
    	// add the global index 
    	for (int i = 0; i < SortedConceptList.size(); i++) {
			SortedConceptList.get(i).global_index = i;

		}
    	
    	int globalindex2=0;

    	     // add global index2 information 
    		for(int i = 0; i < SortedConceptList.size(); i++)
    		{
    			if(i==0)
    			{
    				SortedConceptList.get(i).global_index2=0;
    			}
    			else
    			{
    				if(conceptlist.get(i-1).NNtoken_index!=conceptlist.get(i).NNtoken_index)
    				{
    					globalindex2++;
    					SortedConceptList.get(i).global_index2=globalindex2;
    				}
    			}
    		}
    	
    	
    	return SortedConceptList;
    }
    
     public ArrayList<ConceptInfo> SortHybridConceptlist(ProblemModule pm)
     {
    	 
    	 ArrayList<ConceptInfo> hybridconceptchain = pm.HybridConceptChain;
    	 ArrayList<ConceptInfo> ConceptChain= new ArrayList<ConceptInfo>();
    	 
    	 // deep copy the conceptchain to the ConceptChain:
    	 for(ConceptInfo concept:pm.ConceptChain)
    	 {
    		 ConceptChain.add(concept);
    	 }
    	 
    	 hybridconceptchain.addAll(ConceptChain);
    	 hybridconceptchain= GeneralSortConcept(hybridconceptchain);
    	 
    	 // becuase the hybridconceptchain is a shallow copy of pm.HybridConceptChain, so that if we clear the data in pm it also be clear 
    	 //pm.HybridConceptChain= new ArrayList<ConceptInfo>();
    	 pm.HybridConceptChain= hybridconceptchain;
    	
    	 return hybridconceptchain;
    	 
    	 
    	 
     }
    
     // we will use the hybridconceptlist instead of conceptlist 
	public static void Get_globalindex_forConceptinstance(ProblemModule pm) {
		int circleindex = 0;
		int lineindex = 0;
		int pointindex = 0;
		// int centerindex=0;
		// int oringinindex=0;
		int radiusindex = 0;
		int equationindex = 0;
		int slopeindex = 0;
		int globalindex2=0;
		// we use hybridconceptlist to replace the conceptchain; 
		//ArrayList<ConceptInfo> conceptschain = pm.ConceptChain;
		ArrayList<ConceptInfo> conceptschain=pm.HybridConceptChain;
		for (int i = 0; i < conceptschain.size(); i++) {
			ConceptInfo conceptinfo = conceptschain.get(i);
			
			if (conceptinfo.concept_code.equals("circle")) {

				pm.ConceptInstances.CircleInstances.get(circleindex).Circle_ConceptInfo.global_index = i;
				pm.ConceptInstances.CircleInstances.get(circleindex).Circle_ConceptInfo.global_index2=globalindex2;
				pm.ConceptInstances.CircleInstances.get(circleindex).Circle_ConceptInfo.index = circleindex;
				//pm.ConceptChain.get(i).index = pm.ConceptInstances.CircleInstances.get(circleindex).Circle_Index;
				pm.HybridConceptChain.get(i).index = pm.ConceptInstances.CircleInstances.get(circleindex).Circle_Index;
				circleindex++;
			}

			if (conceptinfo.concept_code.equals("line")) {
				pm.ConceptInstances.LineInstances.get(lineindex).Line_ConceptInfo.global_index = i;
				pm.ConceptInstances.LineInstances.get(lineindex).Line_ConceptInfo.global_index2 = globalindex2;
				pm.ConceptInstances.LineInstances.get(lineindex).Line_ConceptInfo.index = lineindex;
				//pm.ConceptChain.get(i).index = pm.ConceptInstances.LineInstances.get(lineindex).Line_Index;
				pm.HybridConceptChain.get(i).index = pm.ConceptInstances.LineInstances.get(lineindex).Line_Index;
				lineindex++;
			}

			// including, point, midpoint, central point, for this case, the
			// point(s) may be single or multiple point,
			// so that we infer the number of point instance
			if (conceptinfo.concept_code.contains("point") && !conceptinfo.concept_code.contains("cent")&&!conceptinfo.concept_code.contains("tag")&&!conceptinfo.concept_code.contains("value")) {
				pm.ConceptInstances.PointInstances.get(pointindex).Point_ConceptInfo.global_index = i;
				pm.ConceptInstances.PointInstances.get(pointindex).Point_ConceptInfo.global_index2 = globalindex2;
				pm.ConceptInstances.PointInstances.get(pointindex).Point_ConceptInfo.index = pointindex;
				//pm.ConceptChain.get(i).index = pm.ConceptInstances.PointInstances.get(pointindex).Point_Index;
				pm.HybridConceptChain.get(i).index = pm.ConceptInstances.PointInstances.get(pointindex).Point_Index;
				
				pointindex++;

			}

			if (conceptinfo.concept_code.toLowerCase().equals("origin")) {
				pm.ConceptInstances.PointInstances.get(pointindex).Point_ConceptInfo.global_index = i;
				pm.ConceptInstances.PointInstances.get(pointindex).Point_ConceptInfo.global_index2 = globalindex2;
				pm.ConceptInstances.PointInstances.get(pointindex).Point_ConceptInfo.index = pointindex;
				//pm.ConceptChain.get(i).index = pm.ConceptInstances.PointInstances.get(pointindex).Point_Index;
			    pm.HybridConceptChain.get(i).index = pm.ConceptInstances.PointInstances.get(pointindex).Point_Index;
				pointindex++;
			}

			if (conceptinfo.concept_code.toLowerCase().equals("radius")) {
				pm.ConceptInstances.RadiusInstances.get(radiusindex).radiusconceptinfo.global_index = i;
				pm.ConceptInstances.RadiusInstances.get(radiusindex).radiusconceptinfo.global_index2 = globalindex2;
				pm.ConceptInstances.RadiusInstances.get(radiusindex).radiusconceptinfo.index = radiusindex;
				// pm.ConceptChain.get(i).index = pm.ConceptInstances.RadiusInstances.get(radiusindex).radiusindex;
				pm.HybridConceptChain.get(i).index = pm.ConceptInstances.RadiusInstances.get(radiusindex).radiusindex;
				radiusindex++;
			}
			if (conceptinfo.concept_code.toLowerCase().contains("form")) {
				pm.ConceptInstances.EquationInstances.get(equationindex).Equation_ConceptInfo.global_index = i;
				pm.ConceptInstances.EquationInstances.get(equationindex).Equation_ConceptInfo.global_index2 = globalindex2;
				pm.ConceptInstances.EquationInstances.get(equationindex).Equation_ConceptInfo.index = equationindex;
				// pm.ConceptChain.get(i).index = pm.ConceptInstances.EquationInstances.get(equationindex).Equation_Index;
				pm.HybridConceptChain.get(i).index = pm.ConceptInstances.EquationInstances.get(equationindex).Equation_Index;
				
				equationindex++;
			}
			if (conceptinfo.concept_code.toLowerCase().contains("equation")) {
				pm.ConceptInstances.EquationInstances.get(equationindex).Equation_ConceptInfo.global_index = i;
				pm.ConceptInstances.EquationInstances.get(equationindex).Equation_ConceptInfo.global_index2 = globalindex2;
				pm.ConceptInstances.EquationInstances.get(equationindex).Equation_ConceptInfo.index = equationindex;
				// pm.ConceptChain.get(i).index = pm.ConceptInstances.EquationInstances.get(equationindex).Equation_Index;
				pm.HybridConceptChain.get(i).index = pm.ConceptInstances.EquationInstances.get(equationindex).Equation_Index;
				
				equationindex++;
			}
			if (conceptinfo.concept_code.toLowerCase().equals("slope")) {
				pm.ConceptInstances.SlopeInstances.get(slopeindex).slope_info.global_index = i;
				pm.ConceptInstances.SlopeInstances.get(slopeindex).slope_info.global_index2 = globalindex2;
				pm.ConceptInstances.SlopeInstances.get(slopeindex).slope_info.index = slopeindex;
				// pm.ConceptChain.get(i).index = slopeindex;
				pm.HybridConceptChain.get(i).index = slopeindex;
				slopeindex++;

			}

			if ((conceptinfo.concept_code.contains("center") || conceptinfo.concept_code.contains("central"))
					&& !conceptinfo.concept_code.contains("form")) {

				pm.ConceptInstances.PointInstances.get(pointindex).Point_ConceptInfo.global_index = i;
				pm.ConceptInstances.PointInstances.get(pointindex).Point_ConceptInfo.global_index2 = globalindex2;
				pm.ConceptInstances.PointInstances.get(pointindex).Point_ConceptInfo.index = pointindex;
				// pm.ConceptChain.get(i).index = pm.ConceptInstances.PointInstances.get(pointindex).Point_Index;
				pm.HybridConceptChain.get(i).index = pm.ConceptInstances.PointInstances.get(pointindex).Point_Index;
				
				pointindex++;

			}
			if(i<conceptschain.size()-1)
				
			if(conceptschain.get(i).chunk_info.chunk_index!=conceptschain.get(i+1).chunk_info.chunk_index)
				globalindex2=globalindex2+1; 
			conceptinfo = new ConceptInfo();

		}
	}
     
	
	// map the untag concept with the tag or value nearby 
	public void RulebaseMap(ProblemModule pm)
	{
		for(int i=0;i<pm.ConceptChain.size();i++)
		{
			int j=0;// 
			ConceptInfo concept=pm.ConceptChain.get(i);
			// if the concept is plural, then it should but may have not tag yet 
			if(concept.NNPOS.charAt(concept.NNPOS.length()-1)=='S'&&concept.concept_tag==null)
			{
				// first case (1) lines A and B
				if(pm.FeatureChain.size()>concept.NNtoken_index+2&&pm.FeatureChain.get(concept.NNtoken_index+2).token.equals("and"))
				{
					if(pm.ConceptChain.get(i+1).global_index2==concept.global_index2)
					{
						if(!pm.TagList.contains(pm.FeatureChain.get(concept.NNtoken_index+1))&&!pm.TagList.contains(pm.FeatureChain.get(concept.NNtoken_index+1)))
								{
							pm.TagList.add(pm.FeatureChain.get(concept.NNtoken_index+1));
							pm.TagList.add(pm.FeatureChain.get(concept.NNtoken_index+3));
						pm.ConceptChain.get(i).concept_tag=pm.FeatureChain.get(concept.NNtoken_index+1).token;
						pm.ConceptChain.get(i).NNtoken_index=pm.FeatureChain.get(concept.NNtoken_index+1).token_index;
					
						pm.ConceptChain.get(i+1).concept_tag=pm.FeatureChain.get(concept.NNtoken_index+3).token;
						pm.ConceptChain.get(i+1).NNtoken_index=pm.FeatureChain.get(concept.NNtoken_index+3).token_index;
						
					    i++;
								}
					}
				}
				
			
				// second case (2) lines A, B
				else if(pm.FeatureChain.size()>concept.NNtoken_index+2&&pm.FeatureChain.get(concept.NNtoken_index+2).token.equals(","))
				{
					if(pm.ConceptChain.get(i+1).global_index2==concept.global_index2)
					{
						if(!pm.TagList.contains(pm.FeatureChain.get(concept.NNtoken_index+1))&&!pm.TagList.contains(pm.FeatureChain.get(concept.NNtoken_index+1)))
						{
							pm.TagList.add(pm.FeatureChain.get(concept.NNtoken_index+1));
							pm.TagList.add(pm.FeatureChain.get(concept.NNtoken_index+3));
							pm.ConceptChain.get(i).concept_tag=pm.FeatureChain.get(concept.NNtoken_index+1).token;
							pm.ConceptChain.get(i).NNtoken_index=pm.FeatureChain.get(concept.NNtoken_index+1).token_index;
						   
							pm.ConceptChain.get(i+1).concept_tag=pm.FeatureChain.get(concept.NNtoken_index+3).token;
							 pm.ConceptChain.get(i+1).NNtoken_index=pm.FeatureChain.get(concept.NNtoken_index+3).token_index;
							i++;
						}
					}
				}
				
			    // third case (3) points A(0,1) and B(2,6) 
				else if(concept.concept_code.equals("point"))
				{
					if(pm.ConceptChain.get(i+1).global_index2==concept.global_index2)
				    {
						int wordindex=concept.NNtoken_index;
				  
				       for( j=0;j<pm.PointValueChain.size();j++)
				    {
				    	   MathExprProblemWord mathword=pm.PointValueChain.get(j);
				    	if(mathword.if_mapped==0&&concept.NNtoken_index-mathword.wordinfo_index==-1)
				    	{
				    		if(pm.ConceptChain.size()>i+1&&pm.PointValueChain.size()>j+1)
				    		{
				    			
				    			pm.ConceptChain.get(i).concept_tag=mathword.tag;
				    			pm.ConceptChain.get(i).NNtoken_index=mathword.wordinfo_index;
				    			
				    		pm.ConceptChain.get(i+1).concept_tag=pm.PointValueChain.get(j+1).tag;
				    		pm.ConceptChain.get(i+1).NNtoken_index=pm.PointValueChain.get(j+1).wordinfo_index;
				    		
				    		// note that: add the mathword's first word in the taglist so that it will not influence the correctness for finding tag latter
				    		pm.TagList.add(pm.FeatureChain.get(pm.PointValueChain.get(j).wordinfo_index));
				    		pm.TagList.add(pm.FeatureChain.get(pm.PointValueChain.get(j+1).wordinfo_index));
				    	   
				    		pm.PointValueChain.get(j).if_mapped=1;
				    		pm.PointValueChain.get(j+1).if_mapped=1;
				    		i++;
				    		j++;
				    		}
				    	}
				    }
				       
				    }
				}
				// the concept may be line, circle or equation that followed by an equation 
				else {
					int m=0;
			        int n=0;
					for(m=0;m<pm.EquationValueChain.size();m++)
					{
						MathExprProblemWord mathword= pm.EquationValueChain.get(m);
						// inorder to be robust, reguardless any kind of concept.concept_code
						if(mathword.if_mapped==0&&mathword.wordinfo_index-concept.NNtoken_index==1)
						{
							
								if(pm.ConceptChain.size()>i+1&&pm.EquationValueChain.size()>m+1)
								{
									if(pm.ConceptChain.get(i+1).NNtoken_index==pm.ConceptChain.get(i).NNtoken_index)
								{
									pm.ConceptChain.get(i).concept_tag=mathword.tag;
					    			pm.ConceptChain.get(i).NNtoken_index=mathword.wordinfo_index;
					    			
					    		pm.ConceptChain.get(i+1).concept_tag=pm.EquationValueChain.get(m+1).tag;
					    		pm.ConceptChain.get(i+1).NNtoken_index=pm.EquationValueChain.get(m+1).wordinfo_index;
					    		
					    		// note that: add the mathword's first word in the taglist so that it will not influence the correctness for finding tag latter
					    		pm.TagList.add(pm.FeatureChain.get(pm.EquationValueChain.get(m).wordinfo_index));
					    		pm.TagList.add(pm.FeatureChain.get(pm.EquationValueChain.get(m+1).wordinfo_index));
					    	   
					    		pm.EquationValueChain.get(m).if_mapped=1;
					    		pm.EquationValueChain.get(m+1).if_mapped=1;
					    		i++;
					    		m++;
								}
									else
									{
										pm.ConceptChain.get(i).concept_tag=mathword.tag;
						    			pm.ConceptChain.get(i).NNtoken_index=mathword.wordinfo_index;
						    			
						    		// pm.ConceptChain.get(i+1).concept_tag=pm.EquationValueChain.get(j+1).tag;
						    		// pm.ConceptChain.get(i+1).NNtoken_index=pm.EquationValueChain.get(j+1).wordinfo_index;
						    		
						    		// note that: add the mathword's first word in the taglist so that it will not influence the correctness for finding tag latter
						    		pm.TagList.add(pm.FeatureChain.get(pm.EquationValueChain.get(m).wordinfo_index));
						    		//pm.TagList.add(pm.FeatureChain.get(pm.EquationValueChain.get(j+1).wordinfo_index));
						    	   
						    		pm.EquationValueChain.get(m).if_mapped=1;
						    		//pm.EquationValueChain.get(j+1).if_mapped=1;
									}
								
							}
								
							
							
							
						}
						
						
						
						
					}
					
				}
			}
			
			// if the concept is not plural but may not have tag, then we see if there is a concept value next to the concept
			else
			{
				//if the concept is point 
				if(concept.concept_tag==null&&concept.concept_code.equals("point"))
				{
				for(int j1=0;j1<pm.PointValueChain.size();j1++)
				{
					MathExprProblemWord mathword=pm.PointValueChain.get(j1);
					if(mathword.wordinfo_index-concept.NNtoken_index==1&&mathword.if_mapped==0)
					{
						concept.concept_tag=mathword.tag;
						concept.NNtoken_index=mathword.wordinfo_index;
						pm.PointValueChain.get(j1).if_mapped=1;
	
					}
				}
				}
				
				// else if the concept is line 
				else if(concept.concept_tag==null&&concept.concept_code.equals("line"))
				{
					for(int j1=0;j1<pm.EquationValueChain.size();j1++)
					{
						MathExprProblemWord mathword1=pm.EquationValueChain.get(j1);
						if(mathword1.wordinfo_index-concept.NNtoken_index==1&&mathword1.if_mapped==0&&mathword1.concept_code.equals("line_value"))
						{
							concept.concept_tag=mathword1.tag;
							concept.NNtoken_index=mathword1.wordinfo_index;
							mathword1.if_mapped=1;
							pm.EquationValueChain.get(j1).if_mapped=1;
							
						}
						
					}
					
				}
				
				else if(concept.concept_tag==null&&concept.concept_code.equals("circle"))
				{
					for(int j1=0;j1<pm.EquationValueChain.size();j1++)
					{
						MathExprProblemWord mathword1=pm.EquationValueChain.get(j1);
						if(mathword1.wordinfo_index-concept.NNtoken_index==1&&mathword1.if_mapped==0&&mathword1.concept_code.equals("circle_value"))
						{
							concept.concept_tag=mathword1.tag;
							concept.NNtoken_index=mathword1.wordinfo_index;
							pm.EquationValueChain.get(j1).if_mapped=1;
		                    
						}
						
					}
					
				}
				
				// else if the concept is equation 
				
				else if(concept.concept_tag==null&&concept.concept_code.equals("equation"))
				{
					for(int j1=0;j1<pm.EquationValueChain.size();j1++)
					{
						MathExprProblemWord mathword1=pm.EquationValueChain.get(j1);
						if(mathword1.wordinfo_index-concept.NNtoken_index==1&&mathword1.if_mapped==0)
						{
							concept.concept_tag=mathword1.tag;
							concept.NNtoken_index=mathword1.wordinfo_index;
							pm.EquationValueChain.get(j1).if_mapped=1;
							
		
						}
						
					}
				}
				
			}
			
			
		}
	}  
	
	// load unknown quantities to the problem model
	public void loadUnknowQuantity(ProblemModule pm) {

		LoadConceptInstance loadinstance = new LoadConceptInstance();
		ArrayList<QuantityInfo> unknownlist = loadinstance.findUnknConceptAfterWhat(pm);
		System.out.println("unknownn quantities after what:");
		for (QuantityInfo quantity : unknownlist) {
			// if(quantity!=null)
			if (quantity.quantitytoken != null)
				System.out.println(quantity.quantitytoken);
			pm.unknownquantities.add(quantity);
		}

		unknownlist = new ArrayList<QuantityInfo>();
		unknownlist = loadinstance.findUnknowAfterImperative(pm);
		System.out.println("unknown quantity after imperative word:");
		for (QuantityInfo quantity : unknownlist) {

			System.out.println(quantity.quantitytoken);
			pm.unknownquantities.add(quantity);
		}

	}

	public void loadevent(ProblemModule pm) {
		LoadEvent load_event = new LoadEvent();
		load_event.LoadEvent(pm);
		//load_event.getEventInstance(pm);
		for (ConceptInfo event : pm.EventChain) {
			System.out.println(event.concept_code);
		}
	}

	public void loadevent_instance(ProblemModule pm) {
		LoadEvent load_event = new LoadEvent();
		load_event.getEventInstance(pm);
		for (ConceptInfo event : pm.EventChain) {
			System.out.println(event.concept_code);
		}
	}
	



	public void ComputeFeatures(MathProblem mp, ProblemModule pm) {
		
		/********************* Compose ***********************/

		ComposePairsOfLine composeofline = new ComposePairsOfLine();
		ComposePairsOfCircle composeofcircle = new ComposePairsOfCircle();

		composeofline = Pipeline.getInstance().compute_ComposeLinefeature(pm);
		composeofcircle = Pipeline.getInstance().compute_ComposeCirFeature(pm);

		composeofline.Save(mp.id);
		composeofcircle.Save(mp.id);

		/********************* Merge ***********************/

		ArrayList<MergeFeature> mergefeatures = new ArrayList<MergeFeature>();
		mergefeatures.addAll(Pipeline.getInstance().computefeatures(pm));
		for (MergeFeature mf : mergefeatures) {			
			mf.Save(mp.id);			
		}
		
		/*********************** Event ************************/
		// event compose
		ComposeDistance distanceAlignment = new ComposeDistance(pm);
		distanceAlignment.Save(mp.id);
		
		// get intersect alignment feature
		AlignIntersects intersectAlignment = new AlignIntersects(pm);
		intersectAlignment.Save(mp.id);

		// get perpendicular alignment feature
		AlignPerpendicular perpendicularAlignment = new AlignPerpendicular(pm);
		perpendicularAlignment.Save(mp.id);

		AlignParallel parallelAlignment = new AlignParallel(pm);
		parallelAlignment.Save(mp.id);
	}

	// public static ArrayList<E>
	public ComposePairsOfLine compute_ComposeLinefeature(ProblemModule pm) {
		computeFeature cptft = new computeFeature();
		MachingConcepts matchconcept = new MachingConcepts();
		ComposePairsOfLine composeline = matchconcept.ComposeOfLines(pm);
		
		// allocate the space for the feature vector
		composeline.Feature_Eq_Lines = new ComposeFeature[pm.ConceptInstances.EquationInstances
				.size()][pm.ConceptInstances.LineInstances.size()];
		composeline.Feature_Pt_Lines = new ComposeFeature[pm.ConceptInstances.PointInstances
				.size()][pm.ConceptInstances.LineInstances.size()];
		composeline.Feature_Sl_Lines = new ComposeFeature[pm.ConceptInstances.SlopeInstances
				.size()][pm.ConceptInstances.LineInstances.size()];

		// compute <equation,line> pairs feature
		for (int i = 0; i < composeline.Equation_lines.length; i++)
			for (int j = 0; j < composeline.Equation_lines[0].length; j++) {

				ConceptInfo concept1 = composeline.Equation_lines[i][j].concept1;
				ConceptInfo concept2 = composeline.Equation_lines[i][j].concept2;

				composeline.Feature_Eq_Lines[i][j] = new ComposeFeature();
				composeline.Feature_Eq_Lines[i][j] = cptft.composefeaturecomputing(pm, concept1, concept2);

			}
		// compute <point,line> pairs feature
		for (int i = 0; i < composeline.Points_lines.length; i++)
			for (int j = 0; j < composeline.Points_lines[0].length; j++) {
				ConceptInfo concept1 = composeline.Points_lines[i][j].concept1;
				ConceptInfo concept2 = composeline.Points_lines[i][j].concept2;
				// allocate compose feature space for compose line's feature
				// instance
				composeline.Feature_Pt_Lines[i][j] = new ComposeFeature();
				composeline.Feature_Pt_Lines[i][j] = cptft.composefeaturecomputing(pm, concept1, concept2);

			}
		// compute <slope,line> pairs feature
		for (int i = 0; i < composeline.Slopes_lines.length; i++)
			for (int j = 0; j < composeline.Slopes_lines[0].length; j++) {
				ConceptInfo concept1 = composeline.Slopes_lines[i][j].concept1;
				ConceptInfo concept2 = composeline.Slopes_lines[i][j].concept2;
				// allocate compose feature space for compose line's feature
				// instance
				composeline.Feature_Sl_Lines[i][j] = new ComposeFeature();
				composeline.Feature_Sl_Lines[i][j] = cptft.composefeaturecomputing(pm, concept1, concept2);

			}
		return composeline;

	}

	public ComposePairsOfCircle compute_ComposeCirFeature(ProblemModule pm) {
		computeFeature cptft = new computeFeature();
		MachingConcepts matchconcept = new MachingConcepts();
		ComposePairsOfCircle composeofcir = matchconcept.composeofCircle(pm);
		// allocate the feature space for the compose pairs
		composeofcir.Feature_Rd_Cir = new ComposeFeature[pm.ConceptInstances.RadiusInstances
				.size()][pm.ConceptInstances.CircleInstances.size()];
		composeofcir.Feature_Pt_Cir = new ComposeFeature[pm.ConceptInstances.PointInstances
				.size()][pm.ConceptInstances.CircleInstances.size()];
		composeofcir.Feature_Eq_Cir = new ComposeFeature[pm.ConceptInstances.EquationInstances
				.size()][pm.ConceptInstances.CircleInstances.size()];

		// compute <radius,circle> pairs' feature
		for (int i = 0; i < composeofcir.Radius_Circles.length; i++) {
			for (int j = 0; j < composeofcir.Radius_Circles[0].length; j++) {
				ConceptInfo concept1 = composeofcir.Radius_Circles[i][j].concept1;
				ConceptInfo concept2 = composeofcir.Radius_Circles[i][j].concept2;

				composeofcir.Feature_Rd_Cir[i][j] = new ComposeFeature();
				composeofcir.Feature_Rd_Cir[i][j] = cptft.composefeaturecomputing(pm, concept1, concept2);
			}
		}
		// compute <point,circle> pairs' feature
		for (int i = 0; i < composeofcir.Points_Circles.length; i++) {
			for (int j = 0; j < composeofcir.Points_Circles[0].length; j++) {
				ConceptInfo concept1 = composeofcir.Points_Circles[i][j].concept1;
				ConceptInfo concept2 = composeofcir.Points_Circles[i][j].concept2;

				composeofcir.Feature_Pt_Cir[i][j] = new ComposeFeature();
				composeofcir.Feature_Pt_Cir[i][j] = cptft.composefeaturecomputing(pm, concept1, concept2);
			}
		}
		// compute<equation,circle> pairs' feature
		for (int i = 0; i < composeofcir.Equations_Circles.length; i++) {
			for (int j = 0; j < composeofcir.Equations_Circles[0].length; j++) {
				ConceptInfo concept1 = composeofcir.Equations_Circles[i][j].concept1;
				ConceptInfo concept2 = composeofcir.Equations_Circles[i][j].concept2;
				composeofcir.Feature_Eq_Cir[i][j] = new ComposeFeature();

				composeofcir.Feature_Eq_Cir[i][j] = cptft.composefeaturecomputing(pm, concept1, concept2);
			}
		}
		return composeofcir;

	}

	public ArrayList<MergeFeature> computefeatures(ProblemModule pm) {
		// features for merge concepts
		// merge circle features
		ArrayList<MergeFeature> mergefeatureslist = new ArrayList<MergeFeature>();
		computeFeature computefeature = new computeFeature();
		ArrayList<MergeFeature> mergecirlceList = new ArrayList<MergeFeature>();
		MergeFeature temp_mergefeature = new MergeFeature();
		ArrayList<Circleconcept> CircleInstances = pm.ConceptInstances.CircleInstances;
		for (int i = 0; i < CircleInstances.size(); i++) {
			for (int j = i + 1; j < CircleInstances.size(); j++) {
				temp_mergefeature = new MergeFeature();

				temp_mergefeature = computefeature.computeMergefeature(pm, CircleInstances.get(i).Circle_ConceptInfo,
						CircleInstances.get(j).Circle_ConceptInfo);
				temp_mergefeature.index_of_conceptPair[0] = i;
				temp_mergefeature.index_of_conceptPair[0] = j;
				mergecirlceList.add(temp_mergefeature);
			}
              
		}
		mergefeatureslist.addAll(mergecirlceList);
		// merge equation instance
		ArrayList<MergeFeature> mergeequationList = new ArrayList<MergeFeature>();
		ArrayList<Equation> equationlist = pm.ConceptInstances.EquationInstances;
		for (int i = 0; i < equationlist.size(); i++)
			for (int j = i + 1; j < equationlist.size(); j++) {
				
				if(equationlist.get(i).Equation_ConceptInfo.concept_code.equals(equationlist.get(j).Equation_ConceptInfo.concept_code))
				
					{temp_mergefeature = new MergeFeature();

				temp_mergefeature = computefeature.computeMergefeature(pm, equationlist.get(i).Equation_ConceptInfo,
						equationlist.get(j).Equation_ConceptInfo);
				temp_mergefeature.index_of_conceptPair[0] = i;
				temp_mergefeature.index_of_conceptPair[1] = j;
				mergeequationList.add(temp_mergefeature);
					}
			}

		mergefeatureslist.addAll(mergeequationList);
		// merge line instance
		ArrayList<MergeFeature> mergeLinneList = new ArrayList<MergeFeature>();
		ArrayList<Lineconcept> lineconceptlist = pm.ConceptInstances.LineInstances;
		for (int i = 0; i < lineconceptlist.size(); i++)
			for (int j = i + 1; j < lineconceptlist.size(); j++) {
				if(lineconceptlist.get(i).Line_ConceptInfo.concept_code.equals(lineconceptlist.get(j).Line_ConceptInfo.concept_code))
				{
					temp_mergefeature = new MergeFeature();
			

				temp_mergefeature = computefeature.computeMergefeature(pm, lineconceptlist.get(i).Line_ConceptInfo,
						lineconceptlist.get(j).Line_ConceptInfo);
				temp_mergefeature.index_of_conceptPair[0] = i;
				temp_mergefeature.index_of_conceptPair[1] = j;
				mergeLinneList.add(temp_mergefeature);
				}
				
			}

		mergefeatureslist.addAll(mergeLinneList);

		// merge point instance
		ArrayList<MergeFeature> Mergepointfeatures = new ArrayList<MergeFeature>();
		ArrayList<Pointconcept> pointlist = pm.ConceptInstances.PointInstances;
		for (int i = 0; i < pointlist.size(); i++)
			for (int j = i + 1; j < pointlist.size(); j++) {
				if(pointlist.get(i).Point_ConceptInfo.concept_code.equals(pointlist.get(j).Point_ConceptInfo.concept_code))
				{
					temp_mergefeature = new MergeFeature();
				

				temp_mergefeature = computefeature.computeMergefeature(pm, pointlist.get(i).Point_ConceptInfo,
						pointlist.get(j).Point_ConceptInfo);
				temp_mergefeature.index_of_conceptPair[0] = i;
				temp_mergefeature.index_of_conceptPair[1] = j;
				Mergepointfeatures.add(temp_mergefeature);
				}
			}

		mergefeatureslist.addAll(Mergepointfeatures);
		
		// merge radius instance
		ArrayList<MergeFeature> mergeRadiusFeatures = new ArrayList<MergeFeature>();
		ArrayList<Radiusconcept> radiuslist = pm.ConceptInstances.RadiusInstances;
		for (int i = 0; i < radiuslist.size(); i++)
			for (int j = i + 1; j < radiuslist.size(); j++) {
			
				temp_mergefeature = new MergeFeature();
				temp_mergefeature = computefeature.computeMergefeature(pm, radiuslist.get(i).radiusconceptinfo,
						radiuslist.get(j).radiusconceptinfo);
				temp_mergefeature.index_of_conceptPair[0] = i;
				temp_mergefeature.index_of_conceptPair[1] = j;

				mergeRadiusFeatures.add(temp_mergefeature);

			}

		mergefeatureslist.addAll(mergeRadiusFeatures);
		// merge slope instance
		ArrayList<Slope> slopes = pm.ConceptInstances.SlopeInstances;
		ArrayList<MergeFeature> mergeslopefeatures = new ArrayList<MergeFeature>();
		for (int i = 0; i < slopes.size(); i++)
			for (int j = i + 1; j < slopes.size(); j++) {
				temp_mergefeature = new MergeFeature();
				temp_mergefeature = computefeature.computeMergefeature(pm, slopes.get(i).slope_info,
						slopes.get(j).slope_info);
				temp_mergefeature.index_of_conceptPair[0] = i;
				temp_mergefeature.index_of_conceptPair[1] = j;
				mergeslopefeatures.add(temp_mergefeature);
			}

		mergefeatureslist.addAll(mergeslopefeatures);

		return mergefeatureslist;

	}

	public void LoadConceptTOHigherConcept(ProblemModule pm) {
		InstantiateAll_Instance loadconceptTo_Higherconcept = new InstantiateAll_Instance();

		loadconceptTo_Higherconcept.loadPointsInstance(pm);
		loadconceptTo_Higherconcept.LoadEquationInstance(pm);
		loadconceptTo_Higherconcept.loadSlopesInstance(pm);

	}

	public void LoadquantityToconcept(ProblemModule pm) {
		LoadConceptInstance loadconceptinstance = new LoadConceptInstance();
		loadconceptinstance.loadQuantityToConcept(pm);
	}

	// assume all the lines is refer to a same line instance
	public void MergeLine(ProblemModule pm) {
		MergeConceptInstance mergeconcept = new MergeConceptInstance();
		// assume all the line instance are refer to one
		ArrayList<Lineconcept> mergeLinelist = pm.ConceptInstances.LineInstances;
		Lineconcept lineinstance = mergeLinelist.get(0);
		for (int i = 1; i < mergeLinelist.size(); i++) {
			lineinstance = mergeconcept.mergeLineConcept(lineinstance, mergeLinelist.get(i));

		}
		// assume all the lines are same instance
		pm.ConceptInstances.LineInstances.clear();
		pm.ConceptInstances.LineInstances.add(lineinstance);

	}

	// print line Local maximum concept
	public void printline_lmc(Lineconcept lineInstance) {
		PrintLMC printlmc = new PrintLMC();
		printlmc.printlineconcept(lineInstance);
	}

}
