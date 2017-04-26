package jja;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Map.Entry;

public class App {
	private LinkHashMap hmap;

	public App() {
		hmap = new LinkHashMap();
	}

	public String set(String key, String value) {
		String retour = "";
		if (hmap.get().containsKey(key)) {
			retour += "ok: valeur remplacee";
		} else {
			retour += "ok: valeur ajoutee";
		}
		hmap.get().put(key, value);

		return retour;
	}

	public String setNX(String key, String value) {
		if (hmap.get().containsKey(key)) {
			return "erreur: clef deja existante";
		} else {
			hmap.get().put(key, value);
			return "ok: valeur ajoutee";
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

	public String del(Object key) {

		if (hmap.get().containsKey(key)) {
			hmap.get().remove(key);
			return "ok";
		} else {
			return "erreur: la cle n'existe pas";
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
				hmap.get().put(key, "1");
				return "ok: association cree";
			}

			String newVal = "" + (Integer.valueOf(val) + 1);
			hmap.get().put(key, newVal);
			return "ok: valeur mise a jour";
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
				hmap.get().put(key, "-1");
				return "ok: association cree";
			}

			String newVal = "" + (Integer.valueOf(val) - 1);
			hmap.get().put(key, newVal);
			return "ok: valeur mise a jour";

		} catch (ClassCastException e) {
			return "erreur: impossible de decrementer ce type de contenu";
		} catch (NumberFormatException e) {
			return "erreur: Not a Number";
		}
	}

	public String rpush(String keyList, String value) {
		try {
			ArrayDeque<Object> list = (ArrayDeque<Object>) hmap.get().get(keyList);

			String retour = "";

			if (list == null) {
				list = new ArrayDeque<Object>();
				retour += "list cree ";
			} else {
				retour += "list existe ";
			}

			list.addFirst(value);
			retour += ": valeur ajoutee";

			hmap.get().put(keyList, list);

			return retour;
		} catch (ClassCastException e) {
			return "erreur: ce n'est pas une list";
		}
	}

	public String lpush(String keyList, String value) {
		try {
			ArrayDeque<Object> list = (ArrayDeque<Object>) hmap.get().get(keyList);

			String retour = "";

			if (list == null) {
				list = new ArrayDeque<Object>();
				retour += "list cree ";
			} else {
				retour += "list existe ";
			}

			list.addLast(value);
			retour += ": valeur ajoutee";

			hmap.get().put(keyList, list);

			return retour;
		} catch (ClassCastException e) {
			return "erreur: ce n'est pas une list";
		}
	}

	public String rpop(String keyList) {
		try {
			ArrayDeque<Object> list = (ArrayDeque<Object>) hmap.get().get(keyList);

			if (list == null) {
				return "erreur: list n'existe pas";
			}

			String retour = (String) list.pollFirst();
			if (retour == null) {
				return "\"\"";
			} else {
				return "\"" + retour + "\"";
			}
		} catch (ClassCastException e) {
			return "erreur: ce n'est pas une list";
		}
	}

	public String lpop(String keyList) {
		try {
			ArrayDeque<Object> list = (ArrayDeque<Object>) hmap.get().get(keyList);

			if (list == null) {
				return "erreur: list n'existe pas";
			}

			String retour = (String) list.pollLast();
			if (retour == null) {
				return "\"\"";
			} else {
				return "\"" + retour + "\"";
			}
		} catch (ClassCastException e) {
			return "erreur: ce n'est pas une list";
		}
	}

	public String llen(String keyList) {
		try {
			ArrayDeque<Object> list = (ArrayDeque<Object>) hmap.get().get(keyList);

			if (list == null) {
				return "\"0\"";
			}

			return "\"" + list.size() + "\"";

		} catch (ClassCastException e) {
			return "erreur: ce n'est pas une list";
		}
	}
	
	public String lrange(String keyList, String debut, String fin) {
		try {
			ArrayDeque<Object> list = (ArrayDeque<Object>) hmap.get().get(keyList);

			if (list == null) {
				return "\"\"";
			}
			String retour = "";
			Iterator it = list.iterator();
			int deb = Integer.valueOf(debut);
			
			int fi = Integer.valueOf(fin);
			if(fi == -1){
				fi = list.size();
			}else{
				fi = fi % list.size();
			}
			
			int i=1;
			int num = 1;
			String buff = "";
			while(it.hasNext()){
				buff = (String)it.next();

				if(i>=deb && i <= fi){
					retour += num + ") " + buff + "\n";
					num++;
				}
				i++;
			}
			
			return retour;

		} catch (ClassCastException e) {
			return "erreur: ce n'est pas une list";
		}catch (NumberFormatException e) {
			return "erreur: Not a Number";
		}
	}
	
}
