package jja;


import java.io.BufferedInputStream;

import java.io.IOException;

import java.io.PrintWriter;

import java.net.InetSocketAddress;

import java.net.Socket;

import java.net.SocketException;

import java.text.DateFormat;

import java.util.Date;


public class ClientProcessor implements Runnable{


   private Socket sock;
   private App[] data;
   private PrintWriter writer = null;

   private BufferedInputStream reader = null;

   

   public ClientProcessor(Socket pSock, App[] test){

      sock = pSock;
      data = test;
   }

   

   //Le traitement lancé dans un thread séparé

   public void run(){

      System.err.println("Lancement du traitement de la connexion cliente");


      boolean closeConnexion = false;

      //tant que la connexion est active, on traite les demandes

      while(!sock.isClosed()){

         

         try {

            

            //Ici, nous n'utilisons pas les mêmes objets que précédemment

            //Je vous expliquerai pourquoi ensuite

            writer = new PrintWriter(sock.getOutputStream());

            reader = new BufferedInputStream(sock.getInputStream());

            

            //On attend la demande du client            

            String response = read();

            InetSocketAddress remote = (InetSocketAddress)sock.getRemoteSocketAddress();

            

            //On affiche quelques infos, pour le débuggage

            String debug = "";

            debug = "Thread : " + Thread.currentThread().getName() + ". ";

            debug += "Demande de l'adresse : " + remote.getAddress().getHostAddress() +".";

            debug += " Sur le port : " + remote.getPort() + ".\n";

            debug += "\t -> Commande reçue : " + response + "\n";

            System.err.println("\n" + debug);

            

            //On traite la demande du client en fonction de la commande envoyée

            String toSend = "";
            String[] rep = response.split(" ");

            for(int i=0;i<rep.length;i++){
            	System.out.println(i + " " + rep[i]);
            }
            
            switch(rep[0].toUpperCase()){

               case "SET":

                  toSend = trouverBonDatacenter(rep[1]).ajouter(rep[1], rep[2]);

                  break;

               case "GET":

                   toSend = trouverBonDatacenter(rep[1]).recuperer(rep[1]);

                  break;

               case "HSET":
                   toSend = trouverBonDatacenter(rep[1]).setHashMap(rep[1],rep[2],rep[3]);        	   
            	   break;
            	   
               case "HGET":
                   toSend = trouverBonDatacenter(rep[1]).getHashMap(rep[1],rep[2]);        	   
            	   break;
            	   
               case "CLOSE":

                  toSend = "Communication terminée"; 

                  closeConnexion = true;

                  break;

               default : 

                  toSend = "Commande inconnu !";                     

                  break;

            }

            

            //On envoie la réponse au client

            writer.write(toSend);

            //Il FAUT IMPERATIVEMENT UTILISER flush()

            //Sinon les données ne seront pas transmises au client

            //et il attendra indéfiniment

            writer.flush();

            

            if(closeConnexion){

               System.err.println("COMMANDE CLOSE DETECTEE ! ");

               writer = null;

               reader = null;

               sock.close();

               break;

            }

         }catch(SocketException e){

            System.err.println("LA CONNEXION A ETE INTERROMPUE ! ");

            break;

         } catch (IOException e) {

            e.printStackTrace();

         }         

      }

   }

   

   private App trouverBonDatacenter(String string) {
	// TODO Auto-generated method stub
	return data[0];
}



//La méthode que nous utilisons pour lire les réponses

   private String read() throws IOException{      

      String response = "";

      int stream;

      byte[] b = new byte[4096];

      stream = reader.read(b);

      response = new String(b, 0, stream);

      return response;

   }

   

}
