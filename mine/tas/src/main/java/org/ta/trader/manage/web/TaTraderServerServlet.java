package org.ta.trader.manage.web;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ta.common.config.TaConfig;
import org.ta.common.config.TaException;
import org.ta.common.config.TaXmlConfigParser;
import org.ta.common.data.TaDataEncoder;
import org.ta.common.data.TaDataParser;
import org.ta.common.data.TaObjectData;
import org.ta.common.util.TaFileUtil;
import org.ta.trader.manage.TaManageServerImpl;

public class TaTraderServerServlet extends HttpServlet {

	public static final String TA_TRADINGSERVER_CONFIG = "tradingServerConfig";

	private static final Logger LOG = LoggerFactory
			.getLogger(TaTraderServerServlet.class);

	private TaManageServerImpl trader;

	private TaDataParser parser;

	private TaDataEncoder encoder;

	public TaTraderServerServlet() {
	}

	@Override
	public void destroy() {
		super.destroy();
	}

	@Override
	public void init() throws ServletException {
		super.init();
		String p = this.getInitParameter(TA_TRADINGSERVER_CONFIG);
		if (p == null) {
			throw new TaException("parameters:" + TA_TRADINGSERVER_CONFIG
					+ " not found.");
		}
		TaConfig c = TaXmlConfigParser.parse(p);
		this.trader = c.newInstance(TaManageServerImpl.class);
		this.parser = new TaDataParser();
		this.encoder = new TaDataEncoder();
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		long startTime = System.currentTimeMillis();
		Reader reader = req.getReader();
		res.setContentType("application/json; charset=UTF-8");
		Writer writer = res.getWriter();
		this.doService(reader, writer);
		res.setStatus(HttpServletResponse.SC_OK);
		long takeTime = System.currentTimeMillis() - startTime;
		if (LOG.isDebugEnabled()) {
			LOG.debug("take time:" + takeTime);
		}
	}
	
	private void doService(Reader reader, Writer writer) {
		if (LOG.isTraceEnabled()) {
			String str = TaFileUtil.read(reader);
			LOG.trace("request:" + str);
			reader = new StringReader(str);
		}

		TaObjectData data = (TaObjectData) this.parser.parseJson(reader);

		TaObjectData data2 = this.trader.process(data);
		if (LOG.isTraceEnabled()) {
			StringWriter writer2 = new StringWriter();
			try {
				this.encoder.write(data2, writer2);
			} catch (IOException e) {
				throw new TaException(e);
			}
			LOG.trace("response:" + writer2.toString());
		}
		try {
			this.encoder.write(data2, writer);
		} catch (IOException e) {
			throw new TaException(e);//
		}
	}

}
