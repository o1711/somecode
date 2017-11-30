package com.graphscape.commons.lang.support;

import com.graphscape.commons.lang.DebuggerI;
import com.graphscape.commons.lang.ServiceProviderI;
import com.graphscape.commons.lang.provider.default_.DefaultDebugger;
import com.graphscape.commons.lang.provider.default_.NoopDebugger;

public class ServiceProviderSupport implements ServiceProviderI {

	protected DebuggerI debugger;

	public ServiceProviderSupport() {

	}

	public ServiceProviderSupport(boolean debug) {
		if (debug) {
			this.debugger = new DefaultDebugger();
		} else {
			this.debugger = new NoopDebugger();
		}

	}

}
