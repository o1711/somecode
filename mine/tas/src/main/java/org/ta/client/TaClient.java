package org.ta.client;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.HttpConnectionParams;
import org.ta.common.config.TaException;
import org.ta.common.data.TaDataEncoder;
import org.ta.common.data.TaDataParser;
import org.ta.common.data.TaObjectData;
import org.ta.common.handler.TaHandler;

public class TaClient {

	private HttpClient httpclient;

	private URI uri;

	private TaDataParser parser;

	private TaDataEncoder encoder;

	private String sessionId;

	public TaClient(URI uri) {
		this.uri = uri;
		ClientConnectionManager cm = new PoolingClientConnectionManager();
		this.httpclient = new DefaultHttpClient(cm);
		this.parser = new TaDataParser();
		this.encoder = new TaDataEncoder();
	}

	public void doRequest(TaObjectData reqdata,
			TaHandler<TaObjectData> reshandler) {

		try {
			HttpPost req = new HttpPost(uri);
			req.getParams().setIntParameter(
					HttpConnectionParams.CONNECTION_TIMEOUT, 0);
			req.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT, 0);
			// req.addHeader(AjaxCometServlet.HK_ACTION, "message");
			// req.addHeader(AjaxCometServlet.HK_SESSION_ID, this.sid);

			req.setHeader("Content-Type", "application/json; charset=UTF-8");
			if (this.sessionId != null) {
				reqdata.set("sessionId", this.sessionId);//
			}
			StringWriter writer = new StringWriter();
			this.encoder.write(reqdata, writer);
			String jsonString = writer.toString();
			StringEntity entity = new StringEntity(jsonString);
			req.setEntity(entity);

			// execute is here
			final HttpResponse res = httpclient.execute(req);
			StatusLine sl = res.getStatusLine();
			int scode = sl.getStatusCode();
			if (scode != 200) {
				throw new TaException("status code error,code:" + scode
						+ ",reason:" + sl.getReasonPhrase() + ",uri:"
						+ this.uri);
			}
			// process response,
			InputStream is = res.getEntity().getContent();
			Reader r = new InputStreamReader(is);

			TaObjectData resdata = (TaObjectData) this.parser.parseJson(r);

			reshandler.handle(resdata);//

		} catch (Exception e) {
			throw new TaException(e);
		}
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

}
