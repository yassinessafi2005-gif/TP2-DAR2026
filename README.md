# TP2 - Sockets TCP : chaines de caracteres et objets

Ce dossier contient deux implementations Java client/serveur independantes. Elles utilisent TCP et acceptent les operations `+`, `-`, `*` et `/`. Chaque serveur traite une connexion puis se termine.

## Activite 2-1 - Echange de chaines

Le client transmet une expression complete sous forme de chaine de caracteres (par exemple `55 * 25`). Le serveur verifie la syntaxe, calcule le resultat puis renvoie une reponse texte.

### Compilation

Depuis le dossier `Activite2-1-chaines` :

```text
javac Serveur2_1.java Client2_1.java
```

### Execution

Ouvrir deux terminaux dans ce dossier. Dans le premier :

```text
java Serveur2_1
```

Dans le second :

```text
java Client2_1 "55 * 25"
```

### Sortie console attendue

Terminal du serveur :

```text
Expression recue: 55 * 25
Resultat envoye: 1375.0
```

Terminal du client :

```text
Expression envoyee: 55 * 25
Resultat recu: 1375.0
```

Le client accepte une autre expression en arguments, par exemple `java Client "12 + 3"`. Une expression incorrecte ou une division par zero produit un message d'erreur.

## Activite 2-2 - Echange d'un objet

La classe `Operation` implemente `Serializable`. Le client envoie une instance contenant les deux operandes et l'operateur au moyen des flux d'objets Java. Le serveur effectue le calcul et renvoie un objet `Double` (ou un message d'erreur).

### Compilation

Depuis le dossier `Activite2-2-Objets` :

```text
javac Operation.java Serveur2_2.java Client2_2.java
```

### Execution

Ouvrir deux terminaux dans ce dossier. Dans le premier :

```text
java Serveur2_2
```

Dans le second :

```text
java Client2_2
```

### Sortie console attendue

Terminal du serveur :

```text
Démarrage sur le port 1234 ...
Objet Operation recu: 55.0 * 25.0
Resultat envoye: 1375.0
```

Terminal du client :

```text
Objet Operation envoye: 55.0 * 25.0
Resultat recu: 1375.0
```

## Fichiers : (mais les clients dans la meme package et les serveurs)

- `Activite2-1-Chaines/Client.java` et `Serveur.java` : protocole texte.
- `Activite2-2-Objets/Operation.java`, `Client.java` et `Serveur.java` : protocole d'objets serialises.

## Notes

- JDK 8 ou plus recent est necessaire.
- Lancer le serveur avant le client; les activites utilisent respectivement les ports TCP 5000 et 5001.
- Dans un vrai deploiement, ne deserialiser que des classes de confiance et ajouter une validation/gestion de securite adaptee.
- Deposer le projet et le lien du depot GitHub sur la plateforme ENT, comme demande dans les consignes.
