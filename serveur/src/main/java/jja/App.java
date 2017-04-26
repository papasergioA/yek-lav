package jja;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;

/**
 * Hello world!
 *
 */
public class App implements Runnable{
	private HashMap<String, String> hmap;
	private boolean isRunning = true;
	private int port;
	private String host;
	private ServerSocket server = null;

	public App(int startDataCenter, int startPortDataCenter) {
		hmap = new HashMap<>();
		host = "127.0.0." + startDataCenter;
		port = startPortDataCenter;
		
		
		try {

			server  = new ServerSocket(port, 100, InetAddress.getByName(host));

		} catch (UnknownHostException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
		
	}

	public String ajouter(String key, String value) {
		System.out.println(host + " dans ajouter");
		hmap.put(key, value);
		return "ok";
	}

	public String recuperer(String key) {
		System.out.println(host + " dans recup");
		return hmap.get(key);
	}

	public void open() {

		// Toujours dans un thread à part vu qu'il est dans une boucle infinie

		while (isRunning == true) {

			try {

				// On attend une connexion d'un client

				Socket sock = server.accept();
				PrintWriter writer = new PrintWriter(sock.getOutputStream());
				BufferedInputStream reader = new BufferedInputStream(sock.getInputStream());

				// On attend la demande du client

				String response = read(reader);

				InetSocketAddress remote = (InetSocketAddress) sock.getRemoteSocketAddress();
				
				
				String[] rep = response.split(" ");

				for (int i = 0; i < rep.length; i++) {
					System.out.println(i + " " + rep[i]);
				}
				
				String toSend = "";
				switch (rep[0].toUpperCase()) {

				case "SET":

					toSend = ajouter(rep[1], rep[2]);

					break;

				case "GET":

					toSend = recuperer(rep[1]);

					break;
				default:

					toSend = "Commande inconnu !";

					break;

				}

				System.out.println("Connexion DataCenter recue.");

				

			} catch (IOException e) {
				e.printStackTrace();
			}

		}


	}
	
	private String read(BufferedInputStream reader) throws IOException {

		String response = "";

		int stream;

		byte[] b = new byte[4096];

		stream = reader.read(b);

		response = new String(b, 0, stream);

		return response;

	}
	public void close() {

		isRunning = false;

	}

	public static void main(String[] args) {
		App test = new App(2, 2346);
		System.out.println(test.ajouter("coucou", "azerty"));
		System.out.println(test.recuperer("coucou"));

	}

	@Override
	public void run() {
		open();
		
	}
}
