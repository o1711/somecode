package org.ta.director.vectorizier;

import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.Vector;
import org.ta.trader.hista.tohlcv.TaTohlcv;
import org.ta.trader.hista.tohlcv.TaTohlcvWindow;

public class TaV1Vectorizier implements TaTohlcvVectorizier {

	private int dimension = 3;

	private Vector[] unitVectorArray;
	
	private TaTohlcvWindow window;
	
	public TaV1Vectorizier(TaTohlcvWindow w) {
		this.window = w;
		this.unitVectorArray = new Vector[this.dimension];
		for (int i = 0; i < this.dimension; i++) {
			this.unitVectorArray[i] = new DenseVector(this.dimension);
			this.unitVectorArray[i].set(i, 1D);//
		}
	}

	@Override
	public int getDimension() {
		return this.dimension;
	}

	@Override
	public Vector getUnitVectors(int dim) {
		return this.unitVectorArray[dim];//
	}

	@Override
	public Vector vectorize(int idx) {
		Vector rt = new DenseVector(this.dimension);
		TaTohlcv toh = window.get(idx);//
		int i = 0;
		double open = toh.getOpen();
		double high = toh.getHigh();
		double low = toh.getLow();
		double close = toh.getClose();
		boolean up = close > open;
		double s1 = up ? low : high;
		double s2 = up ? high : low;
		double range = high - low;
		rt.set(i++, (s1 - open)/range);
		rt.set(i++, (close-open)/range);
		rt.set(i++, (close - s2)/range);
		rt = rt.normalize();
		return rt;
	}

}
