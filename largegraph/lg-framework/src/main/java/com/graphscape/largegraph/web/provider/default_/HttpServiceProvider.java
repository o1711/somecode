/**
 * Dec 12, 2013
 */
package com.graphscape.largegraph.web.provider.default_;

import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.impl.DefaultFileSystemManager;

import com.graphscape.commons.comet.provider.default_.AjaxCometHttpRequestHandler;
import com.graphscape.commons.configuration.ConfigurationI;
import com.graphscape.commons.container.support.ContainerAwareConfigurableSupport;
import com.graphscape.commons.http.HttpRequestHandlerI;
import com.graphscape.commons.http.HttpRequestI;
import com.graphscape.commons.http.provider.netty.handler.FileRequestHandler;
import com.graphscape.commons.http.provider.netty.handler.MessageCallbackRequestHandler;
import com.graphscape.commons.http.spi.HttpServiceProviderI;
import com.graphscape.commons.http.support.SimpleDispatchingRequestHandler;
import com.graphscape.commons.lang.CallbackI;
import com.graphscape.commons.message.MessageI;
import com.graphscape.commons.session.SessionManagerI;
import com.graphscape.commons.util.Path;
import com.graphscape.commons.util.TimeAndUnit;
import com.graphscape.largegraph.server.LargeGraphServerI;
import com.graphscape.largegraph.server.MessageQueueManagerI;
import com.graphscape.largegraph.web.spi.WebServiceProviderI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class HttpServiceProvider extends ContainerAwareConfigurableSupport implements HttpServiceProviderI {

	private static Path MSGS = Path.valueOf("msgs");

	private static Path DEFAULT = MSGS.getSubPath("default");

	public static Path AJAX = Path.valueOf("comet/ajax");

	public static Path FILES = Path.valueOf("files");

	private CallbackI<MessageI, MessageI> messageCallback;

	private FileSystemManager fsMgr;

	private SimpleDispatchingRequestHandler dispatchingHandler;

	private WebServiceProviderI spi;

	SessionManagerI sessionManager;

	MessageQueueManagerI queueManager;

	public HttpServiceProvider(WebServiceProviderI spi, SessionManagerI sm, MessageQueueManagerI qm) {
		this.spi = spi;
		this.queueManager = qm;
		this.sessionManager = sm;
	}

	@Override
	public void config(ConfigurationI cfg) {
		super.config(cfg);
		String timeoutS = cfg.getProperty("maxMessageProcessingTime");
		TimeAndUnit timeout = TimeAndUnit.parse(timeoutS);

		this.fsMgr = new DefaultFileSystemManager();//todo
		LargeGraphServerI lgs = this.spi.getLargeGraphServer();
		this.messageCallback = new LargeGraphMessageCallback(lgs, timeout);

		this.dispatchingHandler = new SimpleDispatchingRequestHandler(Path.ROOT);
		this.dispatchingHandler.addHandler(FILES, new FileRequestHandler(fsMgr));

		this.dispatchingHandler.addHandler(MSGS, new MessageCallbackRequestHandler(this.messageCallback));
		this.dispatchingHandler.addHandler(AJAX, new AjaxCometHttpRequestHandler(
				new LargeGraphCometServiceProvider(lgs, this.queueManager, this.sessionManager, this.messageCallback,this.spi.getAuthProvider())));

	}

	@Override
	public HttpRequestHandlerI resolveHttpRequestHandler(HttpRequestI req) {
		return this.dispatchingHandler;
	}

}
