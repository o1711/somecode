package org.ta.common.util;

public class TaOsUtil {
	
	private static String OSNAME = System.getProperty("os.name");
	
	private static boolean isWindows = OSNAME.toLowerCase().contains("win");
	
	public static boolean isWindows(){
		return isWindows;
	}
}
