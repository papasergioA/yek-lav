package jja;

import java.util.Map.Entry;

public class App {
	private LinkHashMap hmap;

	public App() {
		hmap = new LinkHashMap();
	}

	public String set(String key, String value) {
		String retour = "ok";
		if (hmap.get().containsKey(key)) {
			retour += ": valeur remplacee";
		} else {
			retour += ": valeur ajoutee";
		}
		hmap.get().put(key, value);

		return retour;
	}

	public String get(Object key) {
		String val = (String) hmap.get().get(key);
		
		if (val == null) {
			return "erreur: la cle n'existe pas";
		}

		return "\"" + val + "\"";
	}

	public String setHashMap(String keyHmap, String key, String value) {
		LinkHashMap hm = (LinkHashMap) hmap.get().get(keyHmap);
		;
		String retour = "";

		if (hm == null) {
			hm = new LinkHashMap();
			retour += "hmap cree ";
		} else {
			retour += "hmap existe ";
		}

		if (hm.get().containsKey(key)) {
			retour += ": valeur remplacee";
		} else {
			retour += ": valeur ajoutee";
		}

		hm.get().put(key, value);
		hmap.get().put(keyHmap, hm);

		return retour;
	}

	public String getHashMap(String keyHmap, String key) {
		LinkHashMap hm = (LinkHashMap) hmap.get().get(keyHmap);

		if (hm == null) {
			return "erreur: hashMap n'existe pas";
		}
		String retour = (String) hm.get().get(key);

		if (retour == null) {
			return "erreur: la cle n'existe pas";
		}

		return "\"" + retour + "\"";
	}

	public String getAllHashMap(String keyHmap) {
		LinkHashMap hm = (LinkHashMap) hmap.get().get(keyHmap);

		if (hm == null) {
			return "erreur: hashMap n'existe pas";
		}
		String retour = "";

		for (Entry<Object, Object> entry : hm.get().entrySet()) {
			String cle = (String) entry.getKey();
			String valeur = (String) entry.getValue();
			retour += "\"" + cle + "\" : \"" + valeur + "\"\n";
		}
		return retour;
	}

	public String incremente(String key) {
		String val = (String) hmap.get().get(key);

		if (val == null) {
			return "erreur: la cle n'existe pas";
		}
		try {
			String newVal = "" + (Integer.valueOf(val) + 1);
			hmap.get().put(key, newVal);
		} catch (Exception e) {
			return "erreur: Not a Number";
		}

		return "ok";
	}

	public String decremente(String key) {
		String val = (String) hmap.get().get(key);

		if (val == null) {
			return "erreur: la cle n'existe pas";
		}
		try {
			String newVal = "" + (Integer.valueOf(val) - 1);
			hmap.get().put(key, newVal);
		} catch (Exception e) {
			return "erreur: Not a Number";
		}
		return "ok";
	}

}
