package clientPackage;

import commonPackage.Operation;
import serverPackage.Server2_2;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;

public class Client2_2 {
    public static void main(String[] args) {
        // Adresse du serveur : localhost par défaut, ou une IP passée en argument
        // Ex : java Client 192.168.1.48
        String host = (args.length > 0) ? args[0] : "localhost";
        try (
                Socket socket = new Socket(host, Server2_2.PORT);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                Scanner clavier = new Scanner(System.in)
        ) {
            System.out.println("[CLIENT] Connecté au serveur !");

            while (true) {
                System.out.print("\nEntrez une opération (ex: 55 * 25) ou 'exit' pour quitter : ");
                String saisie = clavier.nextLine().trim();

                if (saisie.equalsIgnoreCase("exit")) {
                    out.println("exit");
                    System.out.println("[CLIENT] Déconnexion. Au revoir !");
                    break;
                }

                // Validation de la syntaxe AVANT l'envoi
                if (!Operation.estValide(saisie)) {
                    System.out.println("[CLIENT] Syntaxe incorrecte ! Exemples valides : 55 * 25, 10 + 3, 8 / 2");
                    continue;
                }

                Operation operation = Operation.depuisChaine(saisie);

                // Envoi des 3 éléments, un par un
                out.println(Operation.formater(operation.getOperande1()));
                out.println(operation.getOperateur());
                out.println(Operation.formater(operation.getOperande2()));
                System.out.println("[CLIENT] Opération envoyée : " + operation);

                // Lecture et affichage de la réponse
                String reponse = in.readLine();
                if (reponse == null) {
                    System.out.println("[CLIENT] Le serveur a fermé la connexion.");
                    break;
                }
                System.out.println("[CLIENT] Réponse du serveur -> " + reponse);
            }

        } catch (ConnectException e) {
            System.out.println("[CLIENT] Impossible de se connecter : le serveur est-il lancé ?");
        } catch (IOException e) {
            System.out.println("[CLIENT] Erreur : " + e.getMessage());
        }
    }
}
