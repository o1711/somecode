package org.ta.common.scheduler;

import java.util.Map;

public interface TaSchdulerListener {

	public void onTriggered(String trigger, Map<String, Object> triggerContext);
	
}
