package jja;

import java.io.IOException;

import java.net.InetAddress;

import java.net.ServerSocket;

import java.net.Socket;

import java.net.UnknownHostException;

public class TimeServer {

	// On initialise des valeurs par défaut

	private int port = 2345;
	private String host = "127.0.0.1";
	private ServerSocket server = null;
	private boolean isRunning = true;

	public TimeServer() {

		try {

			server = new ServerSocket(port, 100, InetAddress.getByName(host));

		} catch (UnknownHostException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	public TimeServer(String pHost, int pPort) {

		host = pHost;

		port = pPort;

		try {

			server = new ServerSocket(port, 100, InetAddress.getByName(host));

		} catch (UnknownHostException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	// On lance notre serveur

	public void open() {

		// Toujours dans un thread à part vu qu'il est dans une boucle infinie

		Thread t = new Thread(new Runnable() {

			public void run() {
				App datacenter = new App();
				
				while (isRunning == true) {

					try {

						// On attend une connexion d'un client
						Socket client = server.accept();

						// Une fois reçue, on la traite dans un thread séparé
						System.out.println("Connexion cliente reçue.");
						
						Thread t = new Thread(new ClientProcessor(client, datacenter));

						t.start();

					} catch (IOException e) {

						e.printStackTrace();

					}

				}

			}

		});

		t.start();

	}


	public static void main(String[] args) {
		System.out.println("lancement du serveur");
		String host = "127.0.0.1";
		int port = 2345;
		TimeServer ts = new TimeServer(host, port);
		ts.open();
	}

}
