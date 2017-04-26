package jja;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App 
{
	private LinkedHashMap<Object, Object> hmap;
	//private HashMap<String, String> hmap; 
	public App() {
		hmap = new LinkedHashMap(10){
			private static final int MAX_ENTRIES = 100;

		     protected boolean removeEldestEntry(Map.Entry eldest) {
		        return size() > MAX_ENTRIES;
		     }
		};
	}
	
	public String ajouter(String key, String value){
		hmap.put(key, value);
		return "ok";
	}	
	
	public String recuperer(Object key){
		return (String)hmap.get(key);
		 
	}
	
	
	
    public static void main( String[] args )
    {
        App test = new App();
        System.out.println(test.ajouter("coucou", "azerty"));
        System.out.println(test.recuperer("coucou"));
        
        
    }

	public String setHashMap(String keyHmap, String key, String value) {
		LinkedHashMap<Object, Object> hm = (LinkedHashMap<Object, Object>)hmap.get(keyHmap);
;
		String retour = "";

		if(hm == null){
			hm =new LinkedHashMap<>();
			retour += "hmap cree ";
		}else{
			retour += "hmap existe ";
		}
		
		if(hm.containsKey(key)){
			retour += ": valeur remplacee";
		}else{
			retour += ": valeur ajoutee";
		}
		
		hm.put(key, value);
		hmap.put(keyHmap, hm );
		
		return retour;
	}
	
	
	public String getHashMap(String keyHmap, String key) {
		LinkedHashMap<Object, Object> hm = (LinkedHashMap<Object, Object>)hmap.get(keyHmap);

		if(hm == null){
			return "erreur: hashMap n'existe pas";
		}
		String retour = (String) hm.get(key);
		
		if(retour == null){
			return "erreur: la cle n'existe pas";
		}
		
		return retour;
	}
	


}
