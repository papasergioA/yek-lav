package jja;


import java.io.BufferedInputStream;

import java.io.IOException;

import java.io.PrintWriter;

import java.net.Socket;

import java.net.UnknownHostException;

import java.util.Random;
import java.util.Scanner;

public class ClientConnexion implements Runnable {

	private Socket connexion = null;

	private PrintWriter writer = null;

	private BufferedInputStream reader = null;

	// Notre liste de commandes. Le serveur nous répondra différemment selon la
	// commande utilisée.

	private String[] listCommands = { "FULL", "DATE", "HOUR", "NONE" };

	private static int count = 0;

	private String name = "Client-";

	public ClientConnexion(String host, int port) {

		name += ++count;

		try {

			connexion = new Socket(host, port);

		} catch (UnknownHostException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	public void run() {

		// nous n'allons faire que 10 demandes par thread...

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

			try {

				Thread.currentThread().sleep(1000);

			} catch (InterruptedException e) {

				e.printStackTrace();

			}

		}
	}

	private String prompt() {
		Scanner sc = new Scanner(System.in);
		System.out.print("Commande : ");
		String str = sc.nextLine();
		return str;
	}

	// Méthode qui permet d'envoyer des commandeS de façon aléatoire
	private String getCommand() {

		Random rand = new Random();

		return listCommands[rand.nextInt(listCommands.length)];

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
