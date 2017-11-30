/**
 * Dec 9, 2013
 */
package com.graphscape.commons.handling.spi;

import com.graphscape.commons.handling.HandlerResolverI;
import com.graphscape.commons.handling.HandlingContextI;
import com.graphscape.commons.lang.ResolverI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public interface HandlingProviderI<S, T, CTX extends HandlingContextI<S, T>> {

	public ResolverI<S, CTX> getHandlingContextResolver();

	public HandlerResolverI<S, T, CTX> getHandlerResolver();

	public ResolverI<S, ?> getInputDataWrapperResolver();

}
