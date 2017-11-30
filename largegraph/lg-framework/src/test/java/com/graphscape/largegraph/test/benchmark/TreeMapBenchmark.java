package com.graphscape.largegraph.test.benchmark;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import com.graphscape.commons.debug.TimeLoggerI;
import com.graphscape.commons.debug.provider.DefaultTimeLogger;

public class TreeMapBenchmark {

	public static void main(String[] args) throws Exception {
		TimeLoggerI log = new DefaultTimeLogger();
		Random random = new Random();
		int loop = 10000;
		List<Integer> keyList = new ArrayList<Integer>(loop);
		
		Map<Integer,String> map = new TreeMap<Integer,String>();
		log.beforeExecute();
		
		for (int i = 0; i < loop; i++) {
			Integer k = random.nextInt();
			keyList.add(k);			
			String v = String.valueOf(i);
			map.put(k, v);			
		}
		
		log.afterExecute();
		log.addHits(loop-1);
		
		System.out.println("timelog:"+log);
		log.reset();
		
		log.beforeExecute();
		
		for (int i = 0; i < loop; i++) {
			Integer k = keyList.remove(0);
			map.remove(k);			
		}
		log.afterExecute();
		log.addHits(loop-1);
		System.out.println("timelog:"+log);
	}
	

}
