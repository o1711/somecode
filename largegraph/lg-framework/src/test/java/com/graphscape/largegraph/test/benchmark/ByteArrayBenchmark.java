package com.graphscape.largegraph.test.benchmark;

import com.graphscape.commons.debug.TimeLoggerI;
import com.graphscape.commons.debug.provider.DefaultTimeLogger;

public class ByteArrayBenchmark {

	public static void main(String[] args) throws Exception {
		TimeLoggerI log = new DefaultTimeLogger();
		log.beforeExecute();
		for (int i = 0; i < 1000000; i++) {

			byte[] data = new byte[100];//
			for (int j = 0; j < 100; j++) {
				data[j] = (byte) j;
			}
		}
		log.afterExecute();

		System.out.println(log.getAvg());
	}

}
