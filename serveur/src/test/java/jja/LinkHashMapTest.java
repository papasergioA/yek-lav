package jja;

import static org.junit.Assert.*;

import java.net.Socket;
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
		assertEquals(hm.incremente("tt"),"ok: association cree");
		hm.decremente("ter");
		assertEquals(hm.get("ter"),"\"" + "2" + "\"");
		assertEquals(hm.decremente("tt2"),"ok: association cree");
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
		
		assertEquals(hm.get("op"),"erreur: la valeur n'est pas un string");

		assertEquals(hm.setHashMap("ter", "toto", "titi"),"erreur: ce n'est pas une HashMap");
		assertEquals(hm.getHashMap("ter", "toto"),"erreur: ce n'est pas une HashMap");
		
		assertEquals(hm.getAllHashMap("ter"),"erreur: ce n'est pas une HashMap");
		assertEquals(hm.incremente("op"),"erreur: impossible d'incrementer ce type de contenu");
		assertEquals(hm.decremente("op"),"erreur: impossible de decrementer ce type de contenu");

		assertEquals(hm.rpush("az", "tr"),"list cree : valeur ajoutee");
		assertEquals(hm.rpush("az", "dc"),"list existe : valeur ajoutee");
		assertEquals(hm.rpush("op", "tt"),"erreur: ce n'est pas une list");
		
		assertEquals(hm.lpush("ar", "tr"),"list cree : valeur ajoutee");
		assertEquals(hm.lpush("ar", "dc"),"list existe : valeur ajoutee");
		assertEquals(hm.lpush("op", "tt"),"erreur: ce n'est pas une list");
		
		
		assertEquals(hm.rpop("az"),"\"" + "dc" + "\"");
		assertEquals(hm.rpop("az"),"\"" + "tr" + "\"");
		assertEquals(hm.rpop("az"),"\"\"");
		assertEquals(hm.rpop("fd"),"erreur: list n'existe pas");
		assertEquals(hm.rpop("op"),"erreur: ce n'est pas une list");
		
		assertEquals(hm.lpop("ar"),"\"" + "dc" + "\"");
		assertEquals(hm.lpop("ar"),"\"" + "tr" + "\"");
		assertEquals(hm.lpop("ar"),"\"\"");
		assertEquals(hm.lpop("fd"),"erreur: list n'existe pas");
		assertEquals(hm.lpop("op"),"erreur: ce n'est pas une list");
		
		assertEquals(hm.llen("ar"),"\"" + 0 + "\"");
		assertEquals(hm.llen("xw"),"\"0\"");
		assertEquals(hm.llen("op"),"erreur: ce n'est pas une list");
		
		assertEquals(hm.lrange("xw","0","1"),"\"\"");
		hm.lpush("ar", "dc");
		hm.lpush("ar", "ir");
		hm.lpush("ar", "c");
		assertEquals(hm.lrange("ar","0","1"),1 + ") " + "dc" + "\n");
		assertEquals(hm.lrange("ar","3","-1"),1 + ") " + "c" + "\n");
		assertEquals(hm.lrange("ar","a","dqsd"),"erreur: Not a Number");
		assertEquals(hm.lrange("op","3","-1"),"erreur: ce n'est pas une list");
		
		assertEquals(hm.setAdd("nk", "e"),"set cree "+": valeur ajoutee");
		assertEquals(hm.setAdd("nk", "t"),"set existe "+": valeur ajoutee");
		assertEquals(hm.setAdd("op", "e"),"erreur: ce n'est pas un set");
		hm.setAdd("n", "e");
		assertEquals(hm.setRemove("nk", "e"),"ok");
		assertEquals(hm.setRemove("nk", "e"),"erreur: valeur non presente dans le set");
		assertEquals(hm.setRemove("op", "e"),"erreur: ce n'est pas un set");
		assertEquals(hm.setRemove("dsqdq", "e"),"erreur: set n'existe pas");
		
		assertEquals(hm.setMembers("aze"),"\"\"");
		assertEquals(hm.setMembers("nk"),1 + ") " + "t" + "\n");
		assertEquals(hm.setMembers("op"),"erreur: ce n'est pas un set");
		
		assertEquals(hm.setIsMembers("aze",""),"0");
		assertEquals(hm.setIsMembers("nk","t"),"1");
		assertEquals(hm.setIsMembers("nk",""),"0");
		assertEquals(hm.setIsMembers("op",""),"erreur: ce n'est pas un set");
		
		assertEquals(hm.setUnion("aze","adsq"),"\"\"");
		assertEquals(hm.setUnion("nk","n"),1 + ") " + "t" + "\n" + 2 + ") " + "e" + "\n");
		assertEquals(hm.setUnion("op",""),"erreur: ce n'est pas un set");
		
		assertEquals(hm.setNX("ter", "2"),"erreur: clef deja existante");
		assertEquals(hm.setNX("hcf", "45"),"ok: valeur ajoutee");
		
		assertEquals(hm.del("ter"),"ok");
		assertEquals(hm.del("sqdgfghjhg"),"erreur: la cle n'existe pas");
		
	}

	@Test
	public void testClientProc() {
		Socket sock = new Socket();
		App data = new App();
		ClientProcessor c = new ClientProcessor(sock, data);
		assertEquals(c.inconnu(),"Commande inconnu !");
		String[] s = {""};
		assertEquals(c.sunion(s),"erreur: nombre d'arguments invalide! (SUNION keyList1 keyList2)");
		String[] s1 = {"set","e","t"};
		assertEquals(c.sunion(s1),"\"\"");
		assertEquals(c.sismember(s1),"0");
		assertEquals(c.sismember(s),"erreur: nombre d'arguments invalide! (SISMEMBERS keyList value)");
		assertEquals(c.smembers(s),"erreur: nombre d'arguments invalide! (SMEMBERS keyList)");
		String[] s2 = {"set","e"};
		assertEquals(c.smembers(s2),"\"\"");
		assertEquals(c.srem(s),"erreur: nombre d'arguments invalide! (SREM keyList value)");
		assertEquals(c.srem(s1),"erreur: set n'existe pas");
		assertEquals(c.sadd(s),"erreur: nombre d'arguments invalide! (SADD keySet value)");
		assertEquals(c.sadd(s1),"set cree "+": valeur ajoutee");
		assertEquals(c.lrange(s),"erreur: nombre d'arguments invalide! (LRANGE keyList debut fin)");
		String[] s3 = {"set","e","t","e"};
		assertEquals(c.lrange(s3),"erreur: ce n'est pas une list");
		assertEquals(c.llen(s),"erreur: nombre d'arguments invalide! (LLEN keyList)");
		assertEquals(c.llen(s2),"erreur: ce n'est pas une list");
		assertEquals(c.lpop(s),"erreur: nombre d'arguments invalide! (LPOP keyList)");
		assertEquals(c.lpop(s2),"erreur: ce n'est pas une list");
		assertEquals(c.rpop(s),"erreur: nombre d'arguments invalide! (RPOP keyList)");
		assertEquals(c.rpop(s2),"erreur: ce n'est pas une list");
		assertEquals(c.lpush(s),"erreur: nombre d'arguments invalide! (LPUSH keyList value)");
		assertEquals(c.lpush(s1),"erreur: ce n'est pas une list");
		assertEquals(c.rpush(s),"erreur: nombre d'arguments invalide! (RPUSH keyList value)");
		assertEquals(c.rpush(s1),"erreur: ce n'est pas une list");
		assertEquals(c.decr(s),"erreur: nombre d'arguments invalide! (decr key)");
		assertEquals(c.decr(s2),"erreur: impossible de decrementer ce type de contenu");
		assertEquals(c.incr(s),"erreur: nombre d'arguments invalide! (incr key)");
		assertEquals(c.incr(s2),"erreur: impossible d'incrementer ce type de contenu");
		assertEquals(c.hgetall(s),"erreur: nombre d'arguments invalide! (hgetall keyHashMap)");
		assertEquals(c.hgetall(s2),"erreur: ce n'est pas une HashMap");
		assertEquals(c.hget(s),"erreur: nombre d'arguments invalide! (hget keyHashMap key)");
		assertEquals(c.hget(s1),"erreur: ce n'est pas une HashMap");
		assertEquals(c.hset(s), "erreur: nombre d'arguments invalide! (hset keyHashMap key value)");
		assertEquals(c.hset(s3),"erreur: ce n'est pas une HashMap");
		assertEquals(c.get(s),"erreur: nombre d'arguments invalide! (get key)");
		assertEquals(c.get(s2),"erreur: la valeur n'est pas un string");
		assertEquals(c.del(s),"erreur: nombre d'arguments invalide! (del key)");
		assertEquals(c.del(s2),"ok");
		assertEquals(c.setnx(s),"erreur: nombre d'arguments invalide! (setNX key value)");
		assertEquals(c.setnx(s2),"ok: valeur ajoutee");
		assertEquals(c.setnx(s1),"erreur: clef deja existante");
		assertEquals(c.set(s),"erreur: nombre d'arguments invalide! (set key value)");
		assertEquals(c.set(s2),"ok: valeur remplacee");
		assertEquals(c.set(s1),"ok: valeur remplacee");
		
		
		
	}
}
