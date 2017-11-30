package com.graphscape.commons.comet.spi;

import com.graphscape.commons.lang.PropertiesI;

public interface CometAuthProviderI {

	public boolean authorize(String credential, PropertiesI<Object> sessionAttributes);

}
