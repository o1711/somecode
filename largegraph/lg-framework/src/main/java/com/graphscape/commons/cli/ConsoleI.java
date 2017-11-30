package com.graphscape.commons.cli;

import java.util.List;

import com.graphscape.commons.lang.HandlerI;
import com.graphscape.commons.lang.HasAttributeI;
import com.graphscape.commons.lang.LifeCycleI;

/**
 * 
 * @author wuzhen
 * 
 */
public interface ConsoleI extends LifeCycleI, HasAttributeI {

	public ConsoleI prompt(String prompt);

	public ConsoleI pushReader(ConsoleReaderI cr);

	public ConsoleI pushReader(ConsoleReaderI cr, boolean popWhenClosed);

	public ConsoleI pushWriter(ConsoleWriterI cw);

	public ConsoleWriterI popWriter();
	
	public ConsoleWriterI peekWriter();

	public List<CommandType> getCommandList();
	
	public CommandType getCommand(String name);
	//is print one more line after command print end.
	public void printLine(boolean printLine);
	
	public void addCommandPostInterceptor(HandlerI<CommandAndLine> handlerI);
}
