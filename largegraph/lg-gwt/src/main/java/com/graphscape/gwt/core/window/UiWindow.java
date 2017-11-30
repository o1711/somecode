/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 18, 2012
 */
package com.graphscape.gwt.core.window;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.Window;
import com.graphscape.gwt.core.commons.Point;
import com.graphscape.gwt.core.commons.Rectangle;
import com.graphscape.gwt.core.commons.Size;
import com.graphscape.gwt.core.commons.UiPropertiesI;

/**
 * @author wu
 * 
 */
public class UiWindow {

	public static String getParameter(String name, String def) {
		String rt = com.google.gwt.user.client.Window.Location
				.getParameter(name);
		return rt == null ? def : rt;
	}

	public static Rectangle getRectangle() {
		int x = Window.getScrollLeft();
		int y = Window.getScrollTop();
		int w = Window.getClientWidth();
		int h = Window.getClientHeight();
		
		return new Rectangle(Point.valueOf(x, y), Size.valueOf(w, h));
	}
	
	public static Map<String,List<String>> getParameters(){
		Map<String,List<String>> pm = com.google.gwt.user.client.Window.Location.getParameterMap();
		
		return pm;
	}
}
