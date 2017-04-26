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
	private LinkHashMap hmap;
	//private HashMap<String, String> hmap; 
	public App() {
		hmap = new LinkHashMap();
	}
	
	public String ajouter(String key, String value){
		hmap.get().put(key, value);
		return "ok";
	}	
	
	public String recuperer(Object key){
		return (String)hmap.get().get(key);
		 
	}
	
	
	
    public static void main( String[] args )
    {
        App test = new App();
        System.out.println(test.ajouter("coucou", "azerty"));
        System.out.println(test.recuperer("coucou"));
        
        
    }

	public String setHashMap(String keyHmap, String key, String value) {
		LinkHashMap hm = (LinkHashMap)hmap.get().get(keyHmap);
;
		String retour = "";

		if(hm == null){
			hm =new LinkHashMap();
			retour += "hmap cree ";
		}else{
			retour += "hmap existe ";
		}
		
		if(hm.get().containsKey(key)){
			retour += ": valeur remplacee";
		}else{
			retour += ": valeur ajoutee";
		}
		
		hm.get().put(key, value);
		hmap.get().put(keyHmap, hm );
		
		return retour;
	}
	
	
	public String getHashMap(String keyHmap, String key) {
		LinkHashMap hm = (LinkHashMap)hmap.get().get(keyHmap);

		if(hm == null){
			return "erreur: hashMap n'existe pas";
		}
		String retour = (String) hm.get().get(key);
		
		if(retour == null){
			return "erreur: la cle n'existe pas";
		}
		
		return retour;
	}
	


}
