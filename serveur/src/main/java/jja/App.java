package jja;

import java.util.Map.Entry;

public class App {
	private LinkHashMap hmap;

	public App() {
		hmap = new LinkHashMap();
	}

	public String set(String key, String value) {
		try {
			String retour = "ok";
			if (hmap.get().containsKey(key)) {
				retour += ": valeur remplacee";
			} else {
				retour += ": valeur ajoutee";
			}
			hmap.get().put(key, value);

			return retour;
		} catch (ClassCastException e) {
			return "erreur: la valeur n'est pas un string";
		}
	}

	public String get(Object key) {
		try {
			String val = (String) hmap.get().get(key);

			if (val == null) {
				return "erreur: la cle n'existe pas";
			}

			return "\"" + val + "\"";
		} catch (ClassCastException e) {
			return "erreur: la valeur n'est pas un string";
		}
	}

	public String setHashMap(String keyHmap, String key, String value) {
		try {
			LinkHashMap hm = (LinkHashMap) hmap.get().get(keyHmap);

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
		} catch (ClassCastException e) {
			return "erreur: ce n'est pas une HashMap";
		}
	}

	public String getHashMap(String keyHmap, String key) {
		try {
			LinkHashMap hm = (LinkHashMap) hmap.get().get(keyHmap);

			if (hm == null) {
				return "erreur: hashMap n'existe pas";
			}
			String retour = (String) hm.get().get(key);

			if (retour == null) {
				return "erreur: la cle n'existe pas";
			}

			return "\"" + retour + "\"";
		} catch (ClassCastException e) {
			return "erreur: ce n'est pas une HashMap";
		}
	}

	public String getAllHashMap(String keyHmap) {
		try {
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
		} catch (ClassCastException e) {
			return "erreur: ce n'est pas une HashMap";
		}
	}

	public String incremente(String key) {
		try {
			String val = (String) hmap.get().get(key);

			if (val == null) {
				return "erreur: la cle n'existe pas";
			}

			String newVal = "" + (Integer.valueOf(val) + 1);
			hmap.get().put(key, newVal);
			return "ok";
		} catch (ClassCastException e) {
			return "erreur: impossible d'incrementer ce type de contenu";
		} catch (NumberFormatException e) {
			return "erreur: Not a Number";
		}

	}

	public String decremente(String key) {
		try {
			String val = (String) hmap.get().get(key);

			if (val == null) {
				return "erreur: la cle n'existe pas";
			}

			String newVal = "" + (Integer.valueOf(val) - 1);
			hmap.get().put(key, newVal);
			return "ok";

		} catch (ClassCastException e) {
			return "erreur: impossible de decrementer ce type de contenu";
		} catch (NumberFormatException e) {
			return "erreur: Not a Number";
		}
	}

}
