package jja;

import java.io.BufferedInputStream;

import java.io.IOException;

import java.io.PrintWriter;

import java.net.InetSocketAddress;

import java.net.Socket;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.DateFormat;

import java.util.Date;

public class ClientProcessor implements Runnable {

	private Socket sock;
	private PrintWriter writer = null;

	private BufferedInputStream reader = null;

	public ClientProcessor(Socket pSock) {

		sock = pSock;
	}

	// Le traitement lancé dans un thread séparé

	public void run() {

		System.err.println("Lancement du traitement de la connexion cliente");

		boolean closeConnexion = false;

		// tant que la connexion est active, on traite les demandes

		while (!sock.isClosed()) {

			try {

				// Ici, nous n'utilisons pas les mêmes objets que
				// précédemment

				// Je vous expliquerai pourquoi ensuite

				writer = new PrintWriter(sock.getOutputStream());

				reader = new BufferedInputStream(sock.getInputStream());

				// On attend la demande du client
				String response = read();

				InetSocketAddress remote = (InetSocketAddress) sock.getRemoteSocketAddress();

				// On affiche quelques infos, pour le débuggage

				String debug = "";

				debug = "Thread : " + Thread.currentThread().getName() + ". ";

				debug += "Demande de l'adresse : " + remote.getAddress().getHostAddress() + ".";

				debug += " Sur le port : " + remote.getPort() + ".\n";

				debug += "\t -> Commande reçue : " + response + "\n";

				System.err.println("\n" + debug);

				// On traite la demande du client en fonction de la commande
				// envoyée

				String toSend = "";
				String[] rep = response.split(" ");

				for (int i = 0; i < rep.length; i++) {
					System.out.println(i + " " + rep[i]);
				}

				// se connecter au bon datacenter
				Socket sockdatacenter = null;
				if (rep.length > 1) {
					sockdatacenter = connexionDatacenter(rep[1]);
				} else {
					sockdatacenter = connexionDatacenter();
				}
				PrintWriter writerdatacenter = new PrintWriter(sockdatacenter.getOutputStream());
				BufferedInputStream readerdatacenter = new BufferedInputStream(sockdatacenter.getInputStream());
				// On envoie la question au bon datacenter

				writerdatacenter.write(toSend);
				// Il FAUT IMPERATIVEMENT UTILISER flush()

				// Sinon les données ne seront pas transmises au client

				// et il attendra indéfiniment

				writerdatacenter.flush();
				String responsedatacenter = read();

				// on ferme la connexion
				writerdatacenter = null;
				readerdatacenter = null;
				sockdatacenter.close();

				// on renvoie la r�ponse au client
				writer.write(responsedatacenter);

				if (closeConnexion) {

					System.err.println("COMMANDE CLOSE DETECTEE ! ");

					writer = null;

					reader = null;

					sock.close();

					break;

				}

			} catch (SocketException e) {

				System.err.println("LA CONNEXION A ETE INTERROMPUE ! ");

				break;

			} catch (IOException e) {

				e.printStackTrace();

			}

		}

	}

	private Socket connexionDatacenter() {

		// renvoyer un serveur par d�faut??

		try {
			return new Socket("127.0.0.2", 2346);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private Socket connexionDatacenter(String string) {

		// trouver le bon serveur en fonction du hash

		try {
			return new Socket("127.0.0.2", 2346);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
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
