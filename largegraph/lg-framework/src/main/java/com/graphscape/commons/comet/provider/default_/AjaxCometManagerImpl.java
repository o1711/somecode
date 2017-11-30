/**
 *  Dec 11, 2012
 */
package com.graphscape.commons.comet.provider.default_;

import com.graphscape.commons.comet.spi.CometServiceProviderI;
import com.graphscape.commons.comet.support.CometManagerSupport;

/**
 * @author wuzhen
 *         <p>
 *         1-1 mapping to Servlet
 */
public class AjaxCometManagerImpl extends CometManagerSupport {
	
	public AjaxCometManagerImpl(CometServiceProviderI spi) {
		super(spi);
	}

}
