package jja;

import static org.junit.Assert.*;

import java.util.LinkedHashMap;

import org.junit.Test;

public class LinkHashMapTest {

	@Test
	public void testGet() {
		LinkHashMap hm = new LinkHashMap();
		LinkedHashMap h = hm.get();
		assert(h==hm.get());
	}
	

}
