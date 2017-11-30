package org.ta.director.vectorizier;

import org.apache.mahout.math.Vector;

public interface TaTohlcvVectorizier {

	public int getDimension();

	public Vector getUnitVectors(int dim);

	public Vector vectorize(int idx);


}
