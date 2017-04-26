package jja;


import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientConnexion implements Runnable {

	private Socket connexion = null;

	private PrintWriter writer = null;

	private BufferedInputStream reader = null;


	public ClientConnexion(String host, int port) {


		try {

			connexion = new Socket(host, port);

		} catch (UnknownHostException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	public void run() {

		while (true) {

			try {

				writer = new PrintWriter(connexion.getOutputStream(), true);
				reader = new BufferedInputStream(connexion.getInputStream());

				// On envoie la commande au serveur

				String commande = prompt();

				writer.write(commande);
				writer.flush();

				// On attend la réponse
				String response = read();
				System.out.println(response);

			} catch (IOException e1) {

				e1.printStackTrace();

			}
		}
	}

	private String prompt() {
		Scanner sc = new Scanner(System.in);
		System.out.print("Commande : ");
		String str = sc.nextLine();
		return str;
	}


	// Méthode pour lire les réponses du serveur
	private String read() throws IOException {

		String response = "";
		int stream;
		byte[] b = new byte[4096];
		stream = reader.read(b);
		response = new String(b, 0, stream);
		return response;

	}

	public static void main(String[] args) {
		System.out.println("Lancement du client");
		String host = "127.0.0.1";
		int port = 2345;
		Thread t = new Thread(new ClientConnexion(host, port));
		t.start();
	}

}
