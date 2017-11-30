/**
 * Jul 1, 2012
 */
package com.graphscape.gwt.commons.widget.table;

import java.util.List;

import com.graphscape.gwt.core.CompositeI;
import com.graphscape.gwt.core.core.WidgetI;

/**
 * @author wu
 * 
 */
public interface TableI extends CompositeI {

	public static interface HeaderI extends CompositeI {
		public String getName();

		public HeaderI child(WidgetI I);
	}

	public static interface HeadersI extends CompositeI {

		public HeaderI createHeader(String name);

		public List<HeaderI> createHeader(String[] names);

	}

	public static interface CellI extends CompositeI {
		public CellI colspan(int cs);
		public CellI rowspan(int rs);
		public CellI child(WidgetI w);
	}

	public static interface RowI extends CompositeI {
		public CellI createCell();
	}

	public static interface BodyI extends CompositeI {
		public RowI createRow();
	}

	public HeadersI getHeaders();

	public BodyI getBody();
}
