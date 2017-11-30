package org.ta.director.classifier;

import org.apache.mahout.math.Vector;
import org.ta.director.vectorizier.TaTohlcvVectorizier;
import org.ta.director.vectorizier.TaV2Vectorizier;
import org.ta.trader.hista.tohlcv.TaTohlcvWindow;

public class TaC1Classifier implements TaTohlcvClassifier {
	private TaTohlcvVectorizier vectorizier;

	private int totalClassifications;//

	private int density = 2;

	public TaC1Classifier(int density,TaTohlcvWindow window) {
		this.density = density;
		this.vectorizier = new TaV2Vectorizier(window);
		this.totalClassifications = (int) Math.pow(density, this.vectorizier.getDimension());
	}

	@Override
	public int getTotalClassifications() {
		return this.totalClassifications;
	}

	@Override
	public int classify(int idx) {
		Vector v1 = this.vectorizier.vectorize(idx);
		v1 = v1.normalize();// TODO is it good? is there a more reasonable
							// approach such as based on the average valti.

		int rt = 0;

		double step = 2.0D / density;
		for (int i = 0; i < this.vectorizier.getDimension(); i++) {
			double vI = v1.get(i);
			double factor = vI + 1D;
			int factorI = (int) (factor / step);
			if (factorI == density) {
				factorI--;
			}
			rt += Math.pow(density, i) * factorI;

		}

		return rt;
	}

}
