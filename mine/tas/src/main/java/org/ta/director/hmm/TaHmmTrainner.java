package org.ta.director.hmm;

import org.apache.mahout.classifier.sequencelearning.hmm.HmmModel;
import org.apache.mahout.math.DenseMatrix;
import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.Matrix;

public class TaHmmTrainner {

	protected TaHmmSampleCursor cursor;

	protected double pseudoCount;

	protected int outputStates;

	protected int hiddenStates;

	public TaHmmTrainner(TaHmmSampleCursor cursor) {
		this.cursor = cursor;
	}

	public TaHmmTrainner pseudoCount(double pc) {
		this.pseudoCount = pc;
		return this;
	}

	public TaHmmTrainner hiddenStates(int pc) {
		this.hiddenStates = pc;
		return this;
	}

	public TaHmmTrainner outputStates(int pc) {
		this.outputStates = pc;
		return this;
	}

	public HmmModel start() {
		// make sure the pseudo count is not zero
		pseudoCount = pseudoCount == 0 ? Double.MIN_VALUE : pseudoCount;

		// initialize parameters
		DenseMatrix transitionMatrix = new DenseMatrix(hiddenStates,
				hiddenStates);
		DenseMatrix emissionMatrix = new DenseMatrix(hiddenStates, outputStates);
		DenseVector initialProbabilities = new DenseVector(hiddenStates);

		// assign pseudo count to avoid zero probabilities
		transitionMatrix.assign(pseudoCount);
		emissionMatrix.assign(pseudoCount);
		initialProbabilities.assign(pseudoCount);

		// now loop over the sequences to count the number of transitions

		while (this.cursor.next()) {
			// fetch the current set of sequences
			int[] hiddenSequence = this.cursor.get().getHidden();
			int[] observedSequence = this.cursor.get().getObserved();
			// increase the count for initial probabilities
			initialProbabilities.setQuick(hiddenSequence[0],
					initialProbabilities.getQuick(hiddenSequence[0]) + 1);
			countTransitions(transitionMatrix, emissionMatrix,
					observedSequence, hiddenSequence);
		}

		// make sure that probabilities are normalized
		double isum = 0; // sum of initial probabilities
		for (int i = 0; i < hiddenStates; i++) {
			isum += initialProbabilities.getQuick(i);
			// compute sum of probabilities for current row of transition matrix
			double sum = 0;
			for (int j = 0; j < hiddenStates; j++) {
				sum += transitionMatrix.getQuick(i, j);
			}
			// normalize current row of transition matrix
			for (int j = 0; j < hiddenStates; j++) {
				transitionMatrix.setQuick(i, j, transitionMatrix.getQuick(i, j)
						/ sum);
			}
			// compute sum of probabilities for current row of emission matrix
			sum = 0;
			for (int j = 0; j < outputStates; j++) {
				sum += emissionMatrix.getQuick(i, j);
			}
			// normalize current row of emission matrix
			for (int j = 0; j < outputStates; j++) {
				emissionMatrix.setQuick(i, j, emissionMatrix.getQuick(i, j)
						/ sum);
			}
		}
		// normalize the initial probabilities
		for (int i = 0; i < hiddenStates; ++i) {
			initialProbabilities.setQuick(i, initialProbabilities.getQuick(i)
					/ isum);
		}

		// return a new model using the parameter estimates
		return new HmmModel(transitionMatrix, emissionMatrix,
				initialProbabilities);
	}

	private static void countTransitions(Matrix transitionMatrix,
			Matrix emissionMatrix, int[] observedSequence, int[] hiddenSequence) {
		emissionMatrix
				.setQuick(hiddenSequence[0], observedSequence[0],
						emissionMatrix.getQuick(hiddenSequence[0],
								observedSequence[0]) + 1);
		for (int i = 1; i < observedSequence.length; ++i) {
			transitionMatrix.setQuick(hiddenSequence[i - 1], hiddenSequence[i],
					transitionMatrix.getQuick(hiddenSequence[i - 1],
							hiddenSequence[i]) + 1);
			emissionMatrix.setQuick(hiddenSequence[i], observedSequence[i],
					emissionMatrix.getQuick(hiddenSequence[i],
							observedSequence[i]) + 1);
		}
	}
}
