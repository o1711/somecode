/**
 * 
 */
package com.graphscape.commons.message.provider.default_;

import java.util.HashMap;
import java.util.Map;

import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.lang.ResolverI;
import com.graphscape.commons.message.MessageI;
import com.graphscape.commons.message.MessageWrapper;
import com.graphscape.commons.util.ClassUtil;
import com.graphscape.commons.util.Path;

/**
 * @author wuzhen
 * 
 */
public class SimpleMessageWrapperResolver implements ResolverI<MessageI, MessageWrapper> {

	// TODO add cache

	private Map<Path, Class> eventTypes = new HashMap<Path, Class>();

	private Class defaultEventClass;

	public SimpleMessageWrapperResolver() {

	}

	public SimpleMessageWrapperResolver(Class defaultType) {
		this.defaultEventClass = defaultType;
	}

	@Override
	public MessageWrapper resolve(MessageI msg) {
		String cname = msg.getHeader(MessageWrapper.HK_WRAPPER_CLASS);
		Class cls = null;
		if (cname == null) {

			Path path = msg.getPath();
			cls = this.resolveEventType(path, false);
			if (cls == null) {
				if (this.defaultEventClass == null) {
					throw new GsException("no event class defined and no default one set for:" + path);
				}
				cls = this.defaultEventClass;
			}
		} else {
			cls = ClassUtil.forName(cname);
		}
		MessageWrapper rt = (MessageWrapper) ClassUtil.newInstance(cls, new Class[] { MessageI.class }, new Object[] { msg });
		return rt;
	}

	public <X extends MessageWrapper> SimpleMessageWrapperResolver addEventType(Path path, Class ecls) {

		this.eventTypes.put(path, ecls);
		return this;

	}

	public <T extends MessageWrapper> Class<T> resolveEventType(Path path, boolean force) {

		for (Map.Entry<Path, Class> en : this.eventTypes.entrySet()) {
			Path p = en.getKey();
			if (p.isSubPath(path)) {// TODO more concrete
				return en.getValue();
			}
		}
		if (force) {
			throw new GsException("no event class defined for:" + path);
		}
		return null;
	}

	public Class getDefaultEventClass() {
		return defaultEventClass;
	}

	public SimpleMessageWrapperResolver defaultEventClass(Class defaultEventClass) {
		this.defaultEventClass = defaultEventClass;
		return this;
	}

}
