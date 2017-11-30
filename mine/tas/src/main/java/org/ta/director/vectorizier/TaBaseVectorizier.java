package org.ta.director.vectorizier;

import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.Vector;
import org.ta.trader.hista.tohlcv.TaTohlcvWindow;

public abstract class TaBaseVectorizier implements TaTohlcvVectorizier {

	protected int dimension;

	protected Vector[] unitVectorArray;

	protected TaTohlcvWindow window;

	public TaBaseVectorizier(int dim, TaTohlcvWindow w) {
		this.dimension = dim;
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

}
