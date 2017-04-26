package jja;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.junit.Test;

public class LinkHashMapTest {

	@Test
	public void testGet() {
		LinkHashMap hm = new LinkHashMap();
		LinkedHashMap h = hm.get();
		for(int i=0;i<101;i++)
			hm.get().put(i, i);
		assertEquals(hm.get(),h);
	}
	
	@Test
	public void testApp() {
		App hm = new App();
		assertEquals(hm.set("ter", "2"),"ok: valeur ajoutee");
		assertEquals(hm.get("ter"),"\"" + "2" + "\"");
		assertEquals(hm.get("ttt"),"erreur: la cle n'existe pas");
		hm.incremente("ter");
		assertEquals(hm.get("ter"),"\"" + "3" + "\"");
		assertEquals(hm.incremente("tt"),"erreur: la cle n'existe pas");
		hm.decremente("ter");
		assertEquals(hm.get("ter"),"\"" + "2" + "\"");
		assertEquals(hm.decremente("tt"),"erreur: la cle n'existe pas");
		assertEquals(hm.set("ter", "f"),"ok: valeur remplacee");
		assertEquals(hm.decremente("ter"),"erreur: Not a Number");
		assertEquals(hm.incremente("ter"),"erreur: Not a Number");
		assertEquals(hm.setHashMap("op", "i", "jgj"),"hmap cree : valeur ajoutee");
		assertEquals(hm.setHashMap("op", "i", "dsqd"),"hmap existe : valeur remplacee");
		assertEquals(hm.getAllHashMap("op"),"\"" + "i" + "\" : \"" + "dsqd" + "\"\n");
		assertEquals(hm.getAllHashMap("o"),"erreur: hashMap n'existe pas");
		assertEquals(hm.getHashMap("op", "i"),"\"" + "dsqd" + "\"");
		assertEquals(hm.getHashMap("op", "t"),"erreur: la cle n'existe pas");
		assertEquals(hm.getHashMap("a", "df"),"erreur: hashMap n'existe pas");
		
		//assertEquals(hm.set("op", "f"),"erreur: la valeur n'est pas un string");
		//assertEquals(hm.get("op"),"erreur: la valeur n'est pas un string");

		//assertEquals(hm.setHashMap("ter", "toto", "titi"),"erreur: ce n'est pas une HashMap");
		//assertEquals(hm.getHashMap("ter", "toto"),"erreur: la valeur n'est pas un string");
		
		//assertEquals(hm.getAllHashMap("ter"),"erreur: ce n'est pas une HashMap");
		//assertEquals(hm.incremente("op"),"erreur: impossible d'incrementer ce type de contenu");
		//assertEquals(hm.decremente("op"),"erreur: impossible d'incrementer ce type de contenu");


		
		
		
	}
}
