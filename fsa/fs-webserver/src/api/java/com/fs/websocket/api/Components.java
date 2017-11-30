/**
 *  
 */
package com.fs.websocket.api;

import com.fs.commons.api.client.AClientI;
import com.fs.commons.api.client.BClientFactoryI;
import com.fs.commons.api.lang.ClassUtil;

/**
 * @deprecated
 * @author wu <br>
 *         TODO component factory.
 */
public class Components {

	public static BClientFactoryI.ProtocolI WEBSOCKET = new BClientFactoryI.ProtocolI() {

		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return "websocket";
		}

		@Override
		public Class<? extends AClientI> getClientClass() {
			return ClassUtil.forName("com.fs.websocket.impl.mock.MockWSClientImpl");
		}
	};
}
