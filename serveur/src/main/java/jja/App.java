package jja;

import java.util.HashMap;

/**
 * Hello world!
 *
 */
public class App 
{
	private HashMap<String, String> hmap;
	
	public App() {
		hmap = new HashMap<>();
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
