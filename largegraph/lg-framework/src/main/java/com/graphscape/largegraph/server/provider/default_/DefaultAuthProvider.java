/**
 * Dec 16, 2013
 */
package com.graphscape.largegraph.server.provider.default_;

import com.graphscape.commons.comet.spi.CometAuthProviderI;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.lang.PropertiesI;
import com.graphscape.largegraph.core.GraphI;
import com.graphscape.largegraph.core.LargeGraphI;

/**
 * @author wuzhen0808@gmail.com TODO move to LargeGraphI core module,based on
 *         Vertex and Edge..
 */
public class DefaultAuthProvider implements CometAuthProviderI {

	private LargeGraphI lg;

	public DefaultAuthProvider(LargeGraphI lg) {
		this.lg = lg;
	}

	@Override
	public boolean authorize(String credential, PropertiesI<Object> pts) {
		// String user = mc.getEvent().getHeader("user", true);
		// String pass = mc.getEvent().getHeader("pass", true);
		int idx = credential.indexOf(':');
		int idx2 = credential.indexOf('@');

		String user = credential.substring(0, idx);
		String password = credential.substring(idx + 1, idx2 == -1 ? credential.length() : idx2);

		String gid = idx2 == -1 ? null : credential.substring(idx + 1, idx2);
		GraphI g = null;
		if (gid == null) {
			g = lg.getRootGraph();
			gid = g.getId();
		} else {
			g = lg.getGraph(gid);
			if (g == null) {
				return false;
			}
		}
		pts.setProperty("graphId", gid);
		pts.setProperty("user", user);

		return true;
	}

}
