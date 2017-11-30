package org.ta.director.study;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.mahout.classifier.sgd.AdaptiveLogisticRegression;
import org.apache.mahout.classifier.sgd.AdaptiveLogisticRegression.Wrapper;
import org.apache.mahout.classifier.sgd.CrossFoldLearner;
import org.apache.mahout.classifier.sgd.CsvRecordFactory;
import org.apache.mahout.ep.State;
import org.apache.mahout.math.RandomAccessSparseVector;
import org.apache.mahout.math.Vector;

public class TaTrainingPhase {

	public static void main(String[] args) throws Exception {
		new TaTrainingPhase().start();
	}

	public void start() throws IOException {

		String inputFile = "target/google.csv";
		String outputFile = "target/model";
		AdaptiveLogisticModelParameters lmp = new AdaptiveLogisticModelParameters();
		int passes = 50;
		// boolean showperf;
		// int skipperfnum = 99;
		AdaptiveLogisticRegression model;
		CsvRecordFactory csv = lmp.getCsvRecordFactory();
		model = lmp.createAdaptiveLogisticRegression();
		State<Wrapper, CrossFoldLearner> best;
		for (int pass = 0; pass < passes; pass++) {
			BufferedReader in = new BufferedReader(new FileReader(inputFile));
			// read variable names
			csv.firstLine(in.readLine());
			String line = in.readLine();
			int lineCount = 2;
			while (line != null) {
				// for each new line, get target and predictors
				Vector input = new RandomAccessSparseVector(
						lmp.getNumFeatures());
				int targetValue = csv.processLine(line, input);
				// update model
				model.train(targetValue, input);

				line = in.readLine();
				lineCount++;
			}
			in.close();
		}
		best = model.getBest();
		CrossFoldLearner learner = null;
		if (best != null) {
			learner = best.getPayload().getLearner();
		}
		OutputStream modelOutput = new FileOutputStream(outputFile);
		try {
			lmp.saveTo(modelOutput);
		} finally {
			modelOutput.close();
		}
	}
}
