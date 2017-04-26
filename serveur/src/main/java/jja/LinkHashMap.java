package jja;

import java.util.LinkedHashMap;
import java.util.Map;

public class LinkHashMap {
	private LinkedHashMap<Object,Object> hm;
	
	public LinkedHashMap<Object, Object> get() {
		return hm;
	}

	public void set(LinkedHashMap<Object, Object> hm) {
		this.hm = hm;
	}

	public LinkHashMap(){
		hm = new LinkedHashMap(10){
			private static final int MAX_ENTRIES = 100;

		     protected boolean removeEldestEntry(Map.Entry eldest) {
		        return size() > MAX_ENTRIES;
		     }
		};
	}
	
}
