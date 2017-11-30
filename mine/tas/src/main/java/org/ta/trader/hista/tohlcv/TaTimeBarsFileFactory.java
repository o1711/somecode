package org.ta.trader.hista.tohlcv;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.ta.common.config.TaException;
import org.ta.common.util.TaResource;

public class TaTimeBarsFileFactory {

	private Map<TaResource, TaTimeBarsFile> resourceMap = new HashMap<TaResource, TaTimeBarsFile>();

	public TaTimeBarsFileFactory() {
	}

	public TaTimeBarsFile getTimeBarResource(TaResource resource) {
		TaTimeBarsFile rt = this.resourceMap.get(resource);

		if (rt == null) {
			
			InputStream is= resource.getInputStream();
			try {
				rt = new TaTimeBarsFile().load(is);
			} catch (IOException e) {
				throw new TaException(e);
			}

			this.resourceMap.put(resource, rt);//
		}

		return rt;
	}
}
