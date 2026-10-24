package commonPackage;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Operation implements Serializable {

    private static final long serialVersionUID =1L;

    private static final Pattern PATTERN = Pattern.compile(
            "^\\s*(-?\\d+(?:\\.\\d+)?)\\s*([+\\-*/])\\s*(-?\\d+(?:\\.\\d+)?)\\s*$");

    private double operande1;
    private char operateur;
    private double operande2;

    public Operation(double operande1, char operateur, double operande2) {
        this.operande1 = operande1;
        this.operateur = operateur;
        this.operande2 = operande2;
    }

    public double getOperande1() { return operande1; }
    public char getOperateur()   { return operateur; }
    public double getOperande2() { return operande2; }

    /** Vérifie la syntaxe d'une saisie (ex: "55 * 25"). */
    public static boolean estValide(String saisie) {
        return saisie != null && PATTERN.matcher(saisie).matches();
    }

    /** Construit une Operation à partir d'une chaîne valide. */
    public static Operation depuisChaine(String saisie) {
        Matcher m = PATTERN.matcher(saisie);
        if (!m.matches()) {
            throw new IllegalArgumentException("Syntaxe invalide : " + saisie);
        }
        return new Operation(
                Double.parseDouble(m.group(1)),
                m.group(2).charAt(0),
                Double.parseDouble(m.group(3)));
    }

    /** Calcule le résultat de l'opération. */
    public double calculer() {
        switch (operateur) {
            case '+': return operande1 + operande2;
            case '-': return operande1 - operande2;
            case '*': return operande1 * operande2;
            case '/':
                if (operande2 == 0) {
                    throw new ArithmeticException("Division par zéro impossible");
                }
                return operande1 / operande2;
            default:
                throw new IllegalArgumentException("Opérateur inconnu : " + operateur);
        }
    }

    /** Affiche 1375 au lieu de 1375.0 */
    public static String formater(double valeur) {
        if (valeur == Math.floor(valeur) && !Double.isInfinite(valeur)) {
            return String.valueOf((long) valeur);
        }
        return String.valueOf(valeur);
    }

    @Override
    public String toString() {
        return formater(operande1) + " " + operateur + " " + formater(operande2);
    }
}
