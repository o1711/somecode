package org.ta.director.hmm;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.mahout.classifier.sequencelearning.hmm.HmmEvaluator;
import org.apache.mahout.classifier.sequencelearning.hmm.HmmModel;
import org.apache.mahout.math.DenseMatrix;
import org.apache.mahout.math.Matrix;

public class TaHmmEvaluator {
	private static class OutputSummaryComparator implements Comparator<OutputSummary> {

		@Override
		public int compare(OutputSummary o1, OutputSummary o2) {
			return (int) ((o2.getSuccessRate() - o1.getSuccessRate()) * 1000);
		}

	}

	private static class OutputSummaryComparator2 implements Comparator<OutputSummary> {

		@Override
		public int compare(OutputSummary o1, OutputSummary o2) {
			return (int) ((o2.getSuccess() - o1.getSuccess()));
		}

	}

	private static class OutputSummary {
		public int outputState;
		public int total;
		public int errors;

		public Map<Integer, AtomicInteger> errorHiddenSummaryMap = new HashMap<Integer, AtomicInteger>();

		public Map<Integer, AtomicInteger> successHiddenSummaryMap = new HashMap<Integer, AtomicInteger>();

		public OutputSummary(int output) {
			this.outputState = output;
		}

		public void add(boolean isLast, int observed, int expectedHidden, int actualHidden,Object userObject) {
			this.total++;
			if (expectedHidden != actualHidden) {
				AtomicInteger it = this.errorHiddenSummaryMap.get(actualHidden);
				if (it == null) {
					it = new AtomicInteger(0);
					this.errorHiddenSummaryMap.put(actualHidden, it);
				}
				it.addAndGet(1);
				
				this.errors++;
				if(actualHidden == 1 || actualHidden == 1){
					System.out.println("error:"+userObject+",expected:"+expectedHidden+",actualHidden:"+actualHidden);
				}
			} else {
				AtomicInteger it = this.successHiddenSummaryMap.get(actualHidden);
				if (it == null) {
					it = new AtomicInteger(0);
					this.successHiddenSummaryMap.put(actualHidden, it);
				}
				it.addAndGet(1);
				if(actualHidden == 1 || actualHidden == 1){
					System.out.println("good:"+userObject+",hidden:"+actualHidden);
				}
			}
		}

		public double getSuccessRate() {
			if (total == 0) {
				return 0;
			}
			return this.getSuccess() / (double) this.total;
		}

		public int getSuccess() {
			return this.total - this.errors;
		}

		@Override
		public String toString() {
			return String.format("%4d\t%.3f=%4d/%4d", this.outputState, this.getSuccessRate(),
					this.getSuccess(), this.total)
					+ ",errorHiddens:"
					+ this.errorHiddenSummaryMap
					+ ",succHiddens:"
					+ this.successHiddenSummaryMap;
		}

	}

	private HmmModel model;

	private OutputSummary[] outputSummaryArray;

	private boolean onlyRecordLast = true;
	
	private int[] hiddenStateScope = new int[]{1,2};

	public static TaHmmEvaluator newInstance(HmmModel model) {
		return new TaHmmEvaluator(model);
	}

	private TaHmmEvaluator(HmmModel model) {
		this.model = model;
		int outputs = this.model.getNrOfOutputStates();
		this.outputSummaryArray = new OutputSummary[outputs];
		for (int i = 0; i < outputs; i++) {
			this.outputSummaryArray[i] = new OutputSummary(i);
		}
	}

	public void evaluate(TaHmmSampleCursor sc) {

		int idx = 0;

		Matrix actualMatrix = new DenseMatrix(sc.getTotalOutputStates(), sc.getTotalHiddenStates());

		while (sc.next()) {
			int[] obs = sc.get().getObserved();
			int[] expHids = sc.get().getHidden();
			int[] actHids = HmmEvaluator.decode(model, obs, false);
			Object uo = sc.get().getUserObject();
			for (int i = 0; i < expHids.length; i++) {
				int row = obs[i];
				int column = actHids[i];

				boolean isLast = i == expHids.length - 1;
				if (!this.onlyRecordLast || this.onlyRecordLast && isLast) {
					
					this.outputSummaryArray[obs[i]].add(isLast,obs[i], expHids[i], actHids[i],uo);

				}

				actualMatrix.set(row, column, actualMatrix.get(row, column) + 1);
			}

			idx++;
		}
		// System.out.println("actualMatrix:" + actualMatrix);
		Arrays.sort(this.outputSummaryArray, new OutputSummaryComparator());
		for (int i = 0; i < this.outputSummaryArray.length; i++) {
			OutputSummary os = this.outputSummaryArray[i];
			if (os.getSuccess() == 0) {
				break;
			}
			System.out.println(this.outputSummaryArray[i].toString());//
		}
	}

}
