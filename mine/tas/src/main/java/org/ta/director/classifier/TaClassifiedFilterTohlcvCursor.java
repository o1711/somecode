package org.ta.director.classifier;

import org.ta.trader.hista.tohlcv.TaProxyTohlcvCursor;
import org.ta.trader.hista.tohlcv.TaTohlcvCursor;
import org.ta.trader.hista.tohlcv.TaTohlcvWindow;

public class TaClassifiedFilterTohlcvCursor extends TaProxyTohlcvCursor {

	private TaTohlcvClassifier classifier;
	private int classy;
	private TaTohlcvWindow window;
	public TaClassifiedFilterTohlcvCursor(TaTohlcvWindow window,TaTohlcvCursor target, TaTohlcvClassifier clr, int cls) {
		super(target, 0, Long.MAX_VALUE);
		this.classifier = clr;
		this.classy = cls;
		this.window = window;
	}

	@Override
	public boolean next() {
		boolean rt = super.next();
		while(rt){
			window.update(this.get());//
			int cls = this.classifier.classify(0);
			if (cls == this.classy) {
				break;
			} else {
				rt = super.next();
			}			
		}
		
		return rt;


	}

}
