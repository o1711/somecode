package org.ta.test.data;

import java.io.File;

public class TaTestData {

	private static File dataHomeFile = new File(System.getenv("TA_DATA_HOME"));
		
	public static File dataHomeFile(){
		return dataHomeFile;
	}
}
