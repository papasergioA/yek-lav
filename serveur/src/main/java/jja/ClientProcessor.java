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
					if (rep.length == 2) {
						toSend = data.set(rep[1], "");
					} else if (rep.length == 3) {
						toSend = data.set(rep[1], rep[2]);
					} else {
						toSend = "erreur: nombre d'arguments invalide! (set key value)";
					}
					break;
				case "SETNX":
					if (rep.length == 2) {
						toSend = data.set(rep[1], "");
					} else if (rep.length == 3) {
						toSend = data.setNX(rep[1], rep[2]);
					} else {
						toSend = "erreur: nombre d'arguments invalide! (setNX key value)";
					}
					break;

				case "DEL":
					if (rep.length == 2) {
						toSend = data.del(rep[1]);
					} else {
						toSend = "erreur: nombre d'arguments invalide! (del key)";
					}
					break;

				case "GET":
					if (rep.length == 2) {
						toSend = data.get(rep[1]);
					} else {
						toSend = "erreur: nombre d'arguments invalide! (get key)";
					}
					break;
				case "HSET":
					if (rep.length == 4) {
						toSend = data.setHashMap(rep[1], rep[2], rep[3]);
					} else {
						toSend = "erreur: nombre d'arguments invalide! (hset keyHashMap key value)";
					}
					break;
				case "HGET":
					if (rep.length == 3) {
						toSend = data.getHashMap(rep[1], rep[2]);
					} else {
						toSend = "erreur: nombre d'arguments invalide! (hget keyHashMap key)";
					}
					break;
				case "HGETALL":
					if (rep.length == 2) {
						toSend = data.getAllHashMap(rep[1]);
					} else {
						toSend = "erreur: nombre d'arguments invalide! (hgetall keyHashMap)";
					}
					break;
				case "INCR":
					if (rep.length == 2) {
						toSend = data.incremente(rep[1]);
					} else {
						toSend = "erreur: nombre d'arguments invalide! (incr key)";
					}
					break;
				case "DECR":
					if (rep.length == 2) {
						toSend = data.decremente(rep[1]);
					} else {
						toSend = "erreur: nombre d'arguments invalide! (decr key)";
					}
					break;
				case "RPUSH":
					if (rep.length == 3) {
						toSend = data.rpush(rep[1], rep[2]);
					} else {
						toSend = "erreur: nombre d'arguments invalide! (RPUSH keyList value)";
					}
					break;
				case "LPUSH":
					if (rep.length == 3) {
						toSend = data.lpush(rep[1], rep[2]);
					} else {
						toSend = "erreur: nombre d'arguments invalide! (LPUSH keyList value)";
					}
					break;
				case "RPOP":
					if (rep.length == 2) {
						toSend = data.rpop(rep[1]);
					} else {
						toSend = "erreur: nombre d'arguments invalide! (RPOP keyList)";
					}
					break;
				case "LPOP":
					if (rep.length == 2) {
						toSend = data.lpop(rep[1]);
					} else {
						toSend = "erreur: nombre d'arguments invalide! (LPOP keyList)";
					}
					break;
				case "LLEN":
					if (rep.length == 2) {
						toSend = data.llen(rep[1]);
					} else {
						toSend = "erreur: nombre d'arguments invalide! (LLEN keyList)";
					}
					break;
				case "LRANGE":
					if (rep.length == 4) {
						toSend = data.lrange(rep[1], rep[2], rep[3]);
					} else {
						toSend = "erreur: nombre d'arguments invalide! (LRANGE keyList debut fin)";
					}
					break;
				case "SADD":
					if (rep.length == 3) {
						toSend = data.setAdd(rep[1], rep[2]);
					} else {
						toSend = "erreur: nombre d'arguments invalide! (SADD keySet value)";
					}
					break;
				case "SREM":
					if (rep.length == 3) {
						toSend = data.setRemove(rep[1], rep[2]);
					} else {
						toSend = "erreur: nombre d'arguments invalide! (SREM keyList value)";
					}
					break;
				case "SMEMBERS":
					if (rep.length == 2) {
						toSend = data.setMembers(rep[1]);
					} else {
						toSend = "erreur: nombre d'arguments invalide! (SMEMBERS keyList)";
					}
					break;
				case "SISMEMBER":
					if (rep.length == 3) {
						toSend = data.setIsMembers(rep[1],rep[2]);
					} else {
						toSend = "erreur: nombre d'arguments invalide! (SISMEMBERS keyList value)";
					}
					break;
				case "SUNION":
					if (rep.length == 3) {
						toSend = data.setUnion(rep[1],rep[2]);
					} else {
						toSend = "erreur: nombre d'arguments invalide! (SUNION keyList1 keyList2)";
					}
					break;
				default:
					toSend = "Commande inconnu !";
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
