package org.ta.trader.rating.oaa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.ta.common.config.TaConfig;
import org.ta.common.config.TaConfigurable;
import org.ta.common.config.TaException;
import org.ta.common.handler.TaHandlers;
import org.ta.common.lifecycle.TaSimpleThreadLifeCycle;
import org.ta.trader.TaSymbol;
import org.ta.trader.rating.TaRate;

public class TaOaaRateConnector extends TaSimpleThreadLifeCycle implements
		TaConfigurable {

	private String account;

	private String token;

	private String domain;

	private TaHandlers<TaRate> rateHandlers;

	public TaHandlers<TaRate> getRateHandlers() {
		return rateHandlers;
	}

	public TaOaaRateConnector() {
		super();
		this.rateHandlers = new TaHandlers<TaRate>();
	}

	@Override
	protected void doRun() {
		try {
			this.doExecuteInternal();
		} catch (IOException e) {
			throw new TaException(e);
		}
	}

	
	protected void doExecuteInternal() throws IOException {

		HttpHost proxy = new HttpHost("web-proxy.atl.hp.com", 8080);
		DefaultProxyRoutePlanner rp = new DefaultProxyRoutePlanner(proxy);
		HttpClient httpClient = HttpClientBuilder.create().setRoutePlanner(rp)
				.build();
		try {

			String instruments = "EUR_USD";

			HttpUriRequest httpGet = new HttpGet(domain
					+ "/v1/prices?accountId=" + account + "&instruments="
					+ instruments);
			httpGet.setHeader(new BasicHeader("Authorization", "Bearer "
					+ token));

			log.info("Executing request: " + httpGet.getRequestLine());

			HttpResponse resp;
			try {
				resp = httpClient.execute(httpGet);
			} catch (ClientProtocolException e) {
				throw new TaException(e);
			} catch (IOException e) {
				throw new TaException(e);
			}
			HttpEntity entity = resp.getEntity();
			int statusCode = resp.getStatusLine().getStatusCode();

			if (statusCode != 200 || entity == null) {
				String responseString = EntityUtils.toString(entity, "UTF-8");
				log.error("status code:" + statusCode + ",response string:"
						+ responseString);
				return;
			}

			log.info("status code:" + statusCode);

			InputStream stream = entity.getContent();
			String line;
			BufferedReader br = new BufferedReader(
					new InputStreamReader(stream));

			while (this.running && !this.stopRequired) {
				line = br.readLine();
				if (log.isTraceEnabled()) {
					log.trace("read line:" + line);
				}
				if (line == null) {

					continue;
				}

				Object obj = JSONValue.parse(line);
				JSONObject tick = (JSONObject) obj;

				// unwrap if necessary
				if (tick.containsKey("tick")) {
					tick = (JSONObject) tick.get("tick");
				}

				// ignore heartbeats
				if (!tick.containsKey("instrument")) {
					continue;
				}
				String instrument = tick.get("instrument").toString();
				String time = tick.get("time").toString();
				double bid = Double.parseDouble(tick.get("bid").toString());
				double ask = Double.parseDouble(tick.get("ask").toString());
				TaSymbol sym = TaSymbol.valueOf(instrument);
				TaRate tk = new TaRate(sym, ask, bid);

				this.rateHandlers.handle(tk);//
			}

		} finally {
			httpClient.getConnectionManager().shutdown();
		}
	}

	@Override
	public void configure(TaConfig config) {
		this.domain = config.getProperty("domain", true);
		this.account = config.getProperty("accountId", true);
		this.token = config.getProperty("accessToken", true);
	}

}
