package serverPackage;

import commonPackage.Operation;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server2_2 {
    public static final int PORT = 1234;

    public static void main(String[] args) {
        System.out.println("[SERVEUR] Démarrage sur le port " + PORT + "...");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("[SERVEUR] En attente de connexion d'un client...");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("[SERVEUR] Client connecté : " + socket.getInetAddress());
                new Thread(() -> traiterClient(socket)).start();
            }

        } catch (IOException e) {
            System.out.println("[SERVEUR] Erreur : " + e.getMessage());
        }
    }

    private static void traiterClient(Socket socket) {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            String op1;
            while ((op1 = in.readLine()) != null) {

                // Le client veut se déconnecter
                if (op1.equalsIgnoreCase("exit")) {
                    System.out.println("[SERVEUR] Le client a demandé la déconnexion.");
                    break;
                }

                String operateur = in.readLine();
                String op2 = in.readLine();
                System.out.println("[SERVEUR] Reçu -> opérande 1 : '" + op1
                        + "' | opérateur : '" + operateur + "' | opérande 2 : '" + op2+ "'");

                try {
                    Operation operation = new Operation(
                            Double.parseDouble(op1), operateur.charAt(0), Double.parseDouble(op2));

                    double resultat = operation.calculer();
                    String reponse = Operation.formater(resultat);

                    System.out.println("[SERVEUR] Calcul : " + operation + " = " + reponse);
                    out.println("Résultat : " + operation + " = " + reponse);

                } catch (ArithmeticException e) {
                    System.out.println("[SERVEUR] Erreur de calcul : " + e.getMessage());
                    out.println("ERREUR : " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("[SERVEUR] Données invalides reçues.");
                    out.println("ERREUR : données invalides");
                }
            }
        } catch (IOException e) {
            System.out.println("[SERVEUR] Connexion perdue : " + e.getMessage());
        } finally {
            try { socket.close(); } catch (IOException ignored) {}
            System.out.println("[SERVEUR] Client déconnecté.");
        }
    }
}
