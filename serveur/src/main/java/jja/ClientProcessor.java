package jja;

import java.io.BufferedInputStream;

import java.io.IOException;

import java.io.PrintWriter;

import java.net.Socket;

import java.net.SocketException;

public class ClientProcessor implements Runnable {

	private Socket sock;
	private App data;
	private PrintWriter writer = null;

	private BufferedInputStream reader = null;

	public ClientProcessor(Socket pSock, App datacenter) {

		sock = pSock;
		data = datacenter;
	}

	// Le traitement lancé dans un thread séparé

	public void run() {

		System.err.println("Lancement du traitement de la connexion cliente");

		// tant que la connexion est active, on traite les demandes

		while (!sock.isClosed()) {

			try {

				writer = new PrintWriter(sock.getOutputStream());
				reader = new BufferedInputStream(sock.getInputStream());

				// On attend la demande du client
				String response = read();

				String toSend = "";
				String[] rep = response.split(" ");

				switch (rep[0].toUpperCase()) {

				case "SET":
					toSend = set(rep);
					break;
				case "SETNX":
					toSend = setnx(rep);
					break;

				case "DEL":
					toSend = del(rep);
					break;

				case "GET":
					toSend = get(rep);
					break;
				case "HSET":
					toSend = hset(rep);
					break;
				case "HGET":
					toSend = hget(rep);
					break;
				case "HGETALL":
					toSend = hgetall(rep);
					break;
				case "INCR":
					toSend = incr(rep);
					break;
				case "DECR":
					toSend = decr(rep);
					break;
				case "RPUSH":
					toSend = rpush(rep);
					break;
				case "LPUSH":
					toSend = lpush(rep);
					break;
				case "RPOP":
					toSend = rpop(rep);
					break;
				case "LPOP":
					toSend = lpop(rep);
					break;
				case "LLEN":
					toSend = llen(rep);
					break;
				case "LRANGE":
					toSend = lrange(rep);
					break;
				case "SADD":
					toSend = sadd(rep);
					break;
				case "SREM":
					toSend = srem(rep);
					break;
				case "SMEMBERS":
					toSend = smembers(rep);
					break;
				case "SISMEMBER":
					toSend = sismember(rep);
					break;
				case "SUNION":
					toSend = sunion(rep);
					break;
				default:
					toSend = inconnu();
					break;
				}

				// On envoie la réponse au client
				writer.write(toSend);
				writer.flush();

			} catch (SocketException e) {
				System.err.println("LA CONNEXION A ETE INTERROMPUE ! ");
				break;
			} catch (IOException e) {
				e.printStackTrace();
			} catch (StringIndexOutOfBoundsException e) {
				System.err.println("LA CONNEXION A ETE INTERROMPUE ! ");
				break;
			}
		}
	}

	public String inconnu() {
		String toSend;
		toSend = "Commande inconnu !";
		return toSend;
	}

	public String sunion(String[] rep) {
		String toSend;
		if (rep.length == 3) {
			toSend = data.setUnion(rep[1],rep[2]);
		} else {
			toSend = "erreur: nombre d'arguments invalide! (SUNION keyList1 keyList2)";
		}
		return toSend;
	}

	public String sismember(String[] rep) {
		String toSend;
		if (rep.length == 3) {
			toSend = data.setIsMembers(rep[1],rep[2]);
		} else {
			toSend = "erreur: nombre d'arguments invalide! (SISMEMBERS keyList value)";
		}
		return toSend;
	}

	public String smembers(String[] rep) {
		String toSend;
		if (rep.length == 2) {
			toSend = data.setMembers(rep[1]);
		} else {
			toSend = "erreur: nombre d'arguments invalide! (SMEMBERS keyList)";
		}
		return toSend;
	}

	public String srem(String[] rep) {
		String toSend;
		if (rep.length == 3) {
			toSend = data.setRemove(rep[1], rep[2]);
		} else {
			toSend = "erreur: nombre d'arguments invalide! (SREM keyList value)";
		}
		return toSend;
	}

	public String sadd(String[] rep) {
		String toSend;
		if (rep.length == 3) {
			toSend = data.setAdd(rep[1], rep[2]);
		} else {
			toSend = "erreur: nombre d'arguments invalide! (SADD keySet value)";
		}
		return toSend;
	}

	public String lrange(String[] rep) {
		String toSend;
		if (rep.length == 4) {
			toSend = data.lrange(rep[1], rep[2], rep[3]);
		} else {
			toSend = "erreur: nombre d'arguments invalide! (LRANGE keyList debut fin)";
		}
		return toSend;
	}

	public String llen(String[] rep) {
		String toSend;
		if (rep.length == 2) {
			toSend = data.llen(rep[1]);
		} else {
			toSend = "erreur: nombre d'arguments invalide! (LLEN keyList)";
		}
		return toSend;
	}

	public String lpop(String[] rep) {
		String toSend;
		if (rep.length == 2) {
			toSend = data.lpop(rep[1]);
		} else {
			toSend = "erreur: nombre d'arguments invalide! (LPOP keyList)";
		}
		return toSend;
	}

	public String rpop(String[] rep) {
		String toSend;
		if (rep.length == 2) {
			toSend = data.rpop(rep[1]);
		} else {
			toSend = "erreur: nombre d'arguments invalide! (RPOP keyList)";
		}
		return toSend;
	}

	public String lpush(String[] rep) {
		String toSend;
		if (rep.length == 3) {
			toSend = data.lpush(rep[1], rep[2]);
		} else {
			toSend = "erreur: nombre d'arguments invalide! (LPUSH keyList value)";
		}
		return toSend;
	}

	public String rpush(String[] rep) {
		String toSend;
		if (rep.length == 3) {
			toSend = data.rpush(rep[1], rep[2]);
		} else {
			toSend = "erreur: nombre d'arguments invalide! (RPUSH keyList value)";
		}
		return toSend;
	}

	public String decr(String[] rep) {
		String toSend;
		if (rep.length == 2) {
			toSend = data.decremente(rep[1]);
		} else {
			toSend = "erreur: nombre d'arguments invalide! (decr key)";
		}
		return toSend;
	}

	public String incr(String[] rep) {
		String toSend;
		if (rep.length == 2) {
			toSend = data.incremente(rep[1]);
		} else {
			toSend = "erreur: nombre d'arguments invalide! (incr key)";
		}
		return toSend;
	}

	public String hgetall(String[] rep) {
		String toSend;
		if (rep.length == 2) {
			toSend = data.getAllHashMap(rep[1]);
		} else {
			toSend = "erreur: nombre d'arguments invalide! (hgetall keyHashMap)";
		}
		return toSend;
	}

	public String hget(String[] rep) {
		String toSend;
		if (rep.length == 3) {
			toSend = data.getHashMap(rep[1], rep[2]);
		} else {
			toSend = "erreur: nombre d'arguments invalide! (hget keyHashMap key)";
		}
		return toSend;
	}

	public String hset(String[] rep) {
		String toSend;
		if (rep.length == 4) {
			toSend = data.setHashMap(rep[1], rep[2], rep[3]);
		} else {
			toSend = "erreur: nombre d'arguments invalide! (hset keyHashMap key value)";
		}
		return toSend;
	}

	public String get(String[] rep) {
		String toSend;
		if (rep.length == 2) {
			toSend = data.get(rep[1]);
		} else {
			toSend = "erreur: nombre d'arguments invalide! (get key)";
		}
		return toSend;
	}

	public String del(String[] rep) {
		String toSend;
		if (rep.length == 2) {
			toSend = data.del(rep[1]);
		} else {
			toSend = "erreur: nombre d'arguments invalide! (del key)";
		}
		return toSend;
	}

	public String setnx(String[] rep) {
		String toSend;
		if (rep.length == 2) {
			toSend = data.set(rep[1], "");
		} else if (rep.length == 3) {
			toSend = data.setNX(rep[1], rep[2]);
		} else {
			toSend = "erreur: nombre d'arguments invalide! (setNX key value)";
		}
		return toSend;
	}

	public String set(String[] rep) {
		String toSend;
		if (rep.length == 2) {
			toSend = data.set(rep[1], "");
		} else if (rep.length == 3) {
			toSend = data.set(rep[1], rep[2]);
		} else {
			toSend = "erreur: nombre d'arguments invalide! (set key value)";
		}
		return toSend;
	}

	// La méthode que nous utilisons pour lire les réponses
	private String read() throws IOException {
		String response = "";
		int stream;
		byte[] b = new byte[4096];
		stream = reader.read(b);
		response = new String(b, 0, stream);
		return response;

	}

}
