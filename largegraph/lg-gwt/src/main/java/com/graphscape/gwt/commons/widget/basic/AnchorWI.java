package com.graphscape.gwt.commons.widget.basic;

import com.graphscape.gwt.commons.widget.BasicI;

public interface AnchorWI extends BasicI {

	public void click();

	public void setDisplayText(String txt);
	
	public void setDisplayText(String txt, boolean loc);
	
	public void setTextAndTitle(String txt, String title);
	
	public void setImage(String src);

}
