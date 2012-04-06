package app;

import java.io.Serializable;

public class Pair<K,V> implements Serializable {
	private K k;
	private V v;
	
	public Pair(K k, V v) {
		this.k = k;
		this.v = v;
	}
	
	public K getK() {
		return k;
	}
	
	public V getV() { 
		return v;
	}
}
