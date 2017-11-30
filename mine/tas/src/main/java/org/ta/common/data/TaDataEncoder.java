package org.ta.common.data;

import java.io.IOException;
import java.io.Writer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class TaDataEncoder {

	public void write(TaData data, Writer writer) throws IOException {
		JSONObject jso = (JSONObject) this.convert(data);
		jso.writeJSONString(writer);
	}

	private Object convert(TaData data) {

		if (data instanceof TaObjectData) {
			TaObjectData odata = (TaObjectData) data;
			JSONObject rt = new JSONObject();
			for (String k : odata.keySet()) {
				TaData valueI = odata.get(k);
				Object value = this.convert(valueI);
				rt.put(k, value);
			}
			return rt;
		} else if (data instanceof TaArrayData) {
			TaArrayData adata = (TaArrayData)data;
			JSONArray rt = new JSONArray();
			for(int i=0;i<adata.size();i++){
				
				Object oI = this.convert(adata.get(i));
				rt.add(oI);//
			}
			return rt;
		} else if (data instanceof TaStringData) {
			return ((TaStringData) data).getStringValue();
		} else {
			return null;
		}

	}

}
