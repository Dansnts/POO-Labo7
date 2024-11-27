package calculator;

public class CalculatorTests {

    public static void main(String[] args) {
        // Créer une instance de la classe State pour gérer l'état de la calculatrice
        State state = State.getState();
        System.out.println("=== Tests Améliorés de la Calculatrice ===");

        // Fonction utilitaire pour tester les résultats
        System.out.println("Lancement des tests...");
        try {

            // Étape 1 : Empiler des valeurs et tester
            state.setValue(10.0);
            state.pushToStack(state.value());
            state.setValue(5.0);
            state.pushToStack(state.value());
            testResult(state.stackToString(), "[5.0, 10.0]", "Étape 1 - Empilement de 10 et 5");


            // Étape 2 : Addition (10 + 5)
            new Addition().execute();
            state.pushToStack(state.value());
            testResult(state.stackToString(), "[15.0]", "Étape 2 - Addition (10 + 5)");

            // Étape 3 : Empiler une nouvelle valeur et multiplier
            state.setValue(3.0);
            state.pushToStack(state.value());
            new Multiplication().execute(); // Résultat * 3
            state.pushToStack(state.value());
            testResult(state.stackToString(), "[45.0]", "Étape 3 - Multiplication par 3");

            // Étape 4 : Diviser par une nouvelle valeur
            state.setValue(9.0);
            state.pushToStack(state.value());
            new Division().execute(); // Résultat / 9
            state.pushToStack(state.value());
            testResult(state.stackToString(), "[5.0]", "Étape 4 - Division par 9");

            // Étape 5 : Racine carrée
            state.setValue(4.0);
            state.pushToStack(state.value());
            new SquareRoot().execute();
            state.pushToStack(state.value());
            testResult(state.stackToString(), "[2.0, 5.0]", "Étape 5 - Racine carrée de 4");

            // Étape 6 : Mise au carré
            new Square().execute();
            state.pushToStack(state.value());
            testResult(state.stackToString(), "[4.0, 5.0]", "Étape 6 - Mise au carré");

            // Étape 7 : Inverse (1/x)
            new Reciprocal().execute();
            state.pushToStack(state.value());
            testResult(state.stackToString(), "[0.25, 5.0]", "Étape 7 - Inverse (1/4)");

            // Étape 8 : Combinaison complexe
            state.setValue(2.0);
            state.pushToStack(state.value());
            new Multiplication().execute(); // Résultat * 2
            state.pushToStack(state.value());
            state.setValue(5.0);
            state.pushToStack(state.value());
            new Subtraction().execute(); // Résultat - 5
            state.pushToStack(state.value());
            testResult(state.stackToString(), "[-4.5, 5.0]", "Étape 8 - Combinaison complexe");

            // Étape 9 : Réinitialisation de la pile
            new Clear().execute();
            testResult(state.stackToString(), "[]", "Étape 9 - Réinitialisation (Clear)");

        } catch (Exception e) {
            System.out.println("Une erreur inattendue s'est produite: " + e.getMessage());
        }

        System.out.println("=== Fin des Tests Améliorés ===");
    }

    /**
     * Fonction utilitaire pour tester un résultat et afficher un message de succès ou d'échec.
     *
     * @param actual   Le résultat actuel obtenu
     * @param expected Le résultat attendu
     * @param testName Nom du test pour identification
     */
    private static void testResult(String actual, String expected, String testName) {
        if (actual.equals(expected)) {
            System.out.println("✅ " + testName + " réussi ! Résultat: " + actual);
        } else {
            System.out.println("❌ " + testName + " échoué. Attendu: " + expected + ", Obtenu: " + actual);
        }
    }
}
