package com.graphscape.commons.task;

public interface TaskI<T extends TaskContextI> {

	public TaskI<T> getParent();

	public void execute(T t);

}
