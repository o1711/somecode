/**
 * Jun 23, 2012
 */
package com.graphscape.gwt.core.provider.default_.factory;

import com.graphscape.gwt.core.provider.default_.json.BooleanJCC;
import com.graphscape.gwt.core.provider.default_.json.DateJCC;
import com.graphscape.gwt.core.provider.default_.json.ErrorInfoJCC;
import com.graphscape.gwt.core.provider.default_.json.ErrorInfosJCC;
import com.graphscape.gwt.core.provider.default_.json.IntegerJCC;
import com.graphscape.gwt.core.provider.default_.json.LongJCC;
import com.graphscape.gwt.core.provider.default_.json.MessageJCC;
import com.graphscape.gwt.core.provider.default_.json.ObjectListJCC;
import com.graphscape.gwt.core.provider.default_.json.ObjectPropertiesJCC;
import com.graphscape.gwt.core.provider.default_.json.StringJCC;
import com.graphscape.gwt.core.support.CodecFactorySupport;

/**
 * @author wu
 * 
 */
public class JsonCodecFactoryC extends CodecFactorySupport {

	public JsonCodecFactoryC() {
		this.add(new ObjectPropertiesJCC(this));
		this.add(new ErrorInfoJCC(this));
		this.add(new ErrorInfosJCC(this));
		this.add(new ObjectListJCC(this));
		this.add(new MessageJCC(this));
		this.add(new IntegerJCC(this));
		this.add(new StringJCC(this));
		this.add(new BooleanJCC(this));
		this.add(new DateJCC(this));
		this.add(new LongJCC(this));

	}

}
