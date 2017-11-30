/**
 * Dec 18, 2013
 */
package com.graphscape.commons.message.spi.support;

import com.graphscape.commons.handling.HandlingContextI;
import com.graphscape.commons.handling.spi.HandlingProviderI;
import com.graphscape.commons.message.MessageI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public abstract class MessageServiceProviderSupport<MC extends HandlingContextI<MessageI, MessageI>>
		implements HandlingProviderI<MessageI, MessageI, MC> {

}
