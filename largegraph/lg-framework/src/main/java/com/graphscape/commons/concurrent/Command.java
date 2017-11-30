package com.graphscape.commons.concurrent;

import com.graphscape.commons.lang.Wrapper;

public class Command<T> extends Wrapper<T>{
	
	private int type;
	
	public Command(T t) {
		super(t);
	}

}
