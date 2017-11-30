package org.ta.director.hmm;

public interface TaHmmSample {

	public Object getUserObject();
	
	public int[] getObserved();

	public int[] getHidden();
	
}
