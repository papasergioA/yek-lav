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
	private LinkedHashMap<String, String> hmap;
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
	
	public String recuperer(String key){
		return hmap.get(key);
	}
	
	
	
    public static void main( String[] args )
    {
        App test = new App();
        System.out.println(test.ajouter("coucou", "azerty"));
        System.out.println(test.recuperer("coucou"));
        
        
    }
}
