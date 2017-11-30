package com.graphscape.commons.record;

import com.graphscape.commons.file.DumpableI;
import com.graphscape.commons.lang.ResourceI;

public interface IndexI<K, V> extends DumpableI, ResourceI {

	public V put(K k, V v);

	public V remove(K k);
	
	public V get(K k);
	
	public void clear();

}
