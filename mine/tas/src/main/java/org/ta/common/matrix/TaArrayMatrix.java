package org.ta.common.matrix;

public class TaArrayMatrix implements TaMatrix {

	private double[][] values;

	public TaArrayMatrix(int r, int c) {
		this.values = new double[r][c];
	}

	@Override
	public double get(int row, int column) {
		return this.values[row][column];
	}

	@Override
	public double set(int row, int column, double value) {
		//
		double rt = this.values[row][column];
		this.values[row][column] = value;
		return rt;
	}

}
