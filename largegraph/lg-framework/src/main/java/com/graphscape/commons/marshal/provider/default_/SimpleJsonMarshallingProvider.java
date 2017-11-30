/**
 * Jun 23, 2012
 */
package com.graphscape.commons.marshal.provider.default_;

import com.google.gson.JsonElement;
import com.graphscape.commons.marshal.provider.default_.json.marshallers.BooleanJSM;
import com.graphscape.commons.marshal.provider.default_.json.marshallers.DateJSM;
import com.graphscape.commons.marshal.provider.default_.json.marshallers.ErrorInfoJSM;
import com.graphscape.commons.marshal.provider.default_.json.marshallers.ErrorInfosJSM;
import com.graphscape.commons.marshal.provider.default_.json.marshallers.IntegerJSM;
import com.graphscape.commons.marshal.provider.default_.json.marshallers.LongJSM;
import com.graphscape.commons.marshal.provider.default_.json.marshallers.MessageJSM;
import com.graphscape.commons.marshal.provider.default_.json.marshallers.ObjectListJSM;
import com.graphscape.commons.marshal.provider.default_.json.marshallers.ObjectPropertiesJSM;
import com.graphscape.commons.marshal.provider.default_.json.marshallers.StringJSM;
import com.graphscape.commons.marshal.support.SimpleMarshallingProviderSupport;

/**
 * @author wu
 * 
 */
public class SimpleJsonMarshallingProvider extends SimpleMarshallingProviderSupport<JsonElement> {
	
	public SimpleJsonMarshallingProvider() {
		this.add(new ErrorInfoJSM(this));
		this.add(new ErrorInfosJSM(this));

		this.add(new ObjectPropertiesJSM(this));
		this.add(new ObjectListJSM(this));
		this.add(new IntegerJSM(this));
		this.add(new StringJSM(this));
		this.add(new BooleanJSM(this));
		this.add(new DateJSM(this));
		this.add(new LongJSM(this));//
		this.add(new MessageJSM(this));

	}

}
