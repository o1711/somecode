/**
 * 
 */
package com.graphscape.commons.client.support;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import com.graphscape.commons.message.MessageI;
import com.graphscape.commons.message.provider.default_.DefaultMessage;
import com.graphscape.commons.util.Path;
import com.graphscape.commons.util.TimeAndUnit;

/**
 * @author wuzhen
 * 
 */
public class HeartBeat {
	// each send message will set a timer for certain time later to send the
	// heart beat message.

	protected TimerTask heartBeatTask;
	
	protected Timer timer = new Timer();

	protected TimeAndUnit heartBeatInterval = TimeAndUnit.valueOf(1, TimeUnit.MINUTES);
	
	protected MessageClientSupport client;
	
	protected Path heartBeatMessagePath;
	
	public HeartBeat(Path path){
		this.heartBeatMessagePath = path;
	}
	
	public void reset(MessageClientSupport clientS) {

		// cancel current task if not send for now.
		if (this.heartBeatTask != null) {
			this.heartBeatTask.cancel();
		}

		this.heartBeatTask = new TimerTask() {

			@Override
			public void run() {
				HeartBeat.this.sendHeartBeartMessage();
			}
		};

		this.timer.schedule(this.heartBeatTask,this.heartBeatInterval.toMillis());
	}

	public void sendHeartBeartMessage() {

		MessageI rt = new DefaultMessage(null,heartBeatMessagePath);// not
		// response?
		// rt.setHeader(MessageI.HK_SILENCE, "true");
		this.client.doSendMessage(rt);
	}
}
