package com.mathapollo.test;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import smile.classification.DecisionTree;
import smile.classification.LogisticRegression;
import smile.data.AttributeDataset;
import smile.data.NominalAttribute;
import smile.data.parser.ArffParser;
import smile.data.parser.DelimitedTextParser;
import smile.validation.LOOCV;

/*
 *
 * Copy from Smile Test Code
 * 
 */
public class SmileTest {

	public SmileTest() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}

	/*
	 *
	 * Test of learn method, of class LogisticRegression.
	 * 
	 */
	@Test
	public void testIris2() {
		System.out.println("Iris binary");
		ArffParser arffParser = new ArffParser();
		arffParser.setResponseIndex(4);
		try {			
			AttributeDataset iris = arffParser.parse(new File("data.smile/iris.arff"));
			double[][] x = iris.toArray(new double[iris.size()][]);
			int[] y = iris.toArray(new int[iris.size()]);

			for (int i = 0; i < y.length; i++) {
				if (y[i] == 2) {
					y[i] = 1;
				} else {
					y[i] = 0;
				}
			}

			int n = x.length;
			LOOCV loocv = new LOOCV(n);
			int error = 0;
			for (int i = 0; i < n; i++) {
				double[][] trainx = smile.math.Math.slice(x, loocv.train[i]);
				int[] trainy = smile.math.Math.slice(y, loocv.train[i]);
				LogisticRegression logit = new LogisticRegression(trainx, trainy);
				if (y[loocv.test[i]] != logit.predict(x[loocv.test[i]]))
					error++;
			}

			System.out.println("Logistic Regression error = " + error);
			assertEquals(3, error);
		} catch (Exception ex) {
			System.err.println(ex);
		}
	}
	
    @Test
    public void testSegment() {
//        System.out.println("Segment");
//        ArffParser arffParser = new ArffParser();
//        arffParser.setResponseIndex(19);
//        try {
//            AttributeDataset train = arffParser.parse(smile.data.parser.IOUtils.getTestDataFile("weka/segment-challenge.arff"));
//            AttributeDataset test = arffParser.parse(smile.data.parser.IOUtils.getTestDataFile("weka/segment-test.arff"));
//
//            double[][] x = train.toArray(new double[train.size()][]);
//            int[] y = train.toArray(new int[train.size()]);
//            double[][] testx = test.toArray(new double[test.size()][]);
//            int[] testy = test.toArray(new int[test.size()]);
//
//            LogisticRegression logit = new LogisticRegression(x, y, 0.05, 1E-3, 1000);
//            
//            int error = 0;
//            for (int i = 0; i < testx.length; i++) {
//                if (logit.predict(testx[i]) != testy[i]) {
//                    error++;
//                }
//            }
//
//            System.out.format("Segment error rate = %.2f%%\n", 100.0 * error / testx.length);
//            assertEquals(48, error);
//        } catch (Exception ex) {
//            System.err.println(ex);
//        }
    }
	
    /**
     * Constructor.
     * 
     * @param x training samples.
     * @param y training labels in [0, k), where k is the number of classes.
     * @param lambda &lambda; &gt; 0 gives a "regularized" estimate of linear
     * weights which often has superior generalization performance, especially
     * when the dimensionality is high.
     * @param tol the tolerance for stopping iterations.
     * @param maxIter the maximum number of iterations.
     */

    /*
	 *
	 * Test of learn method, of class LogisticRegression.
	 *
     */
    @Test
    public void testUSPS() {
        System.out.println("USPS");
        DelimitedTextParser parser = new DelimitedTextParser();
        parser.setResponseIndex(new NominalAttribute("class"), 0);
        try {
            AttributeDataset train = parser.parse(new File("data.smile/zip.train"));
            AttributeDataset test  = parser.parse(new File("data.smile/zip.test"));

            double[][] x = train.toArray(new double[train.size()][]);
            int[] y = train.toArray(new int[train.size()]);
            double[][] testx = test.toArray(new double[test.size()][]);
            int[] testy = test.toArray(new int[test.size()]);
            
            LogisticRegression logit = new LogisticRegression(x, y, 0.3, 1E-3, 1000);
            
            int error = 0;
            for (int i = 0; i < testx.length; i++) {
                if (logit.predict(testx[i]) != testy[i]) {
                    error++;
                }
            }

            System.out.format("USPS error rate = %.2f%%\n", 100.0 * error / testx.length);
            assertEquals(188, error);
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    /*
     * Test of learn method, of class DecisionTree.
     */
    @Test
    public void testDecisionTreeWeather() {
        System.out.println("Weather");
        ArffParser arffParser = new ArffParser();
        arffParser.setResponseIndex(4);
        try {
        	AttributeDataset weather = arffParser.parse(new File("data.smile/weather.nominal.arff"));
            double[][] x = weather.toArray(new double[weather.size()][]);
            int[] y = weather.toArray(new int[weather.size()]);
            int n = x.length;
            LOOCV loocv = new LOOCV(n);
            int error = 0;
            for (int i = 0; i < n; i++) {
                double[][] trainx = smile.math.Math.slice(x, loocv.train[i]);
                int[] trainy = smile.math.Math.slice(y, loocv.train[i]);
                
                DecisionTree tree = new DecisionTree(weather.attributes(), trainx, trainy, 3);
                if (y[loocv.test[i]] != tree.predict(x[loocv.test[i]]))
                    error++;
            }
            
            System.out.println("Decision Tree error = " + error);
            assertEquals(5, error);
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }
        
    /**
     * Test of learn method, of class DecisionTree.
     */
    @Test
    public void testDecisionTreeIris() {
        System.out.println("Iris");
        ArffParser arffParser = new ArffParser();
        arffParser.setResponseIndex(4);
        try {
            AttributeDataset iris = arffParser.parse(new File("data.smile/iris.arff"));
            double[][] x = iris.toArray(new double[iris.size()][]);
            int[] y = iris.toArray(new int[iris.size()]);

            int n = x.length;
            LOOCV loocv = new LOOCV(n);
            int error = 0;
            for (int i = 0; i < n; i++) {
                double[][] trainx = smile.math.Math.slice(x, loocv.train[i]);
                int[] trainy = smile.math.Math.slice(y, loocv.train[i]);
                
                DecisionTree tree = new DecisionTree(iris.attributes(), trainx, trainy, 4);
                //DecisionTree tree = new DecisionTree(attributes, x, y, 350, 2, DecisionTree.SplitRule.ENTROPY);
                //new DecisionTree(x, y, 350, DecisionTree.SplitRule.ENTROPY);
                if (y[loocv.test[i]] != tree.predict(x[loocv.test[i]]))
                    error++;
            }
            
            System.out.println("Decision Tree error = " + error);
            assertEquals(7, error);
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

}
