/**
 * Jul 1, 2012
 */
package com.graphscape.gwt.core.spi;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.graphscape.gwt.core.ContainerI;

/**
 * @author wu
 * 
 */
public interface GwtSPI {
	public static class Factory {
		private ContainerI container;

		private List<GwtSPI> spiList = new ArrayList<GwtSPI>();

		private static Factory ME = new Factory();

		private Factory() {
			this.container = GWT.create(ContainerI.class);
		}

		public static Factory create() {
			return new Factory();//
		}

		public static Factory get() {
			return ME;
		}

		public ContainerI getContainer() {
			return this.container;
		}

		public Factory active(GwtSPI[] spis) {
			for (int i = 0; i < spis.length; i++) {
				this.active(spis[i]);
			}
			return this;
		}

		public Factory active(GwtSPI spi) {
			spiList.add(spi);

			spi.active(this.container);
			return this;
		}

	}

	public void active(ContainerI c);

}
