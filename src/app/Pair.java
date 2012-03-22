package app;

public class Pair<K,V> {
	K k;
	V v;
	public Pair(K k, V v) {
		this.k = k;
		this.v = v;
	}
	K getK() {
		return k;
	}
	V getV() { 
		return v;
	}
}
