
public class main {

	public static void main(String[] args) {

		String host = "127.0.0.1";
		int port = 2345;
		
		switch (args[0]) {
		case "client":
			System.out.println("Lancement du client");
			Thread t = new Thread(new ClientConnexion(host, port));
			t.start();
			break;
		case "serveur":
			System.out.println("lancement du serveur");

			TimeServer ts = new TimeServer(host, port);
			ts.open();

			System.out.println("Serveur initialisé.");

			break;
		default:
			System.out.println("Erreur: Arguments attendu:\n client\nserveur nbServeurs");
			break;
		}

	}

}
