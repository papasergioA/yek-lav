
public class main {

	public static void main(String[] args) {

		switch (args[0]) {
		case "client":
			System.out.println("Lancement du client");
			break;
		case "serveur":
			System.out.println("lancement du serveur");
			break;
		default:
			System.out.println("Erreur: Arguments attendu:\n client\nserveur nbServeurs");
			break;
		}

	}

}
