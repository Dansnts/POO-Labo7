/**
 * @author Aubry Antoine
 * @author Faria dos Santos Dani Tiago
 */

package calculator;

/**
 * Classe représentant l'état de la calculatrice.
 * Gère la valeur courante, la mémoire, les erreurs, et la pile des opérations.
 */
public class State {
    private static State instance;
    private String value = "";
    private String memory = "0";
    private String error = "";
    private boolean hasError = false;
    private boolean isMutable = true;
    private Operator currentOperator = null;
    private boolean waitingForNextOperand = false;
    private boolean clearedOnNextInput = false;

    /**
     * Constructeur par défaut de la classe State.
     */
    public State() {}

    private Stack<Double> stack = new Stack<>();

    /**
     * Empile une valeur sur la pile.
     *
     * @param value La valeur à empiler.
     */
    public void pushToStack(double value) {
        stack.push(value);
    }

    /**
     * Désempile une valeur de la pile.
     *
     * @return La valeur désemparée.
     */
    public double popFromStack() {
        if (stack.isEmpty()) {
            setError("Stack is empty");
            return 0;
        }
        return stack.pop();
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères du contenu de la pile.
     *
     * @return Une chaîne représentant les éléments de la pile.
     */
    public String stackToString() {
        return stack.toString();
    }

    /**
     * Retourne un tableau représentant l'état actuel de la pile.
     *
     * @return Un tableau contenant les éléments de la pile.
     */
    public Object[] stackToArray() {
        return stack.toArray();
    }

    /**
     * Retourne l'instance unique de l'état.
     *
     * @return L'instance unique de la classe State.
     */
    public static State getState() {
        if (instance == null) {
            instance = new State();
        }
        return instance;
    }

    /**
     * Efface les erreurs actuelles et réinitialise l'état mutable.
     */
    public void clearError() {
        value = "";
        error = "";
        hasError = false;
        isMutable = true;
    }

    /**
     * Vérifie si la pile est vide.
     *
     * @return true si la pile est vide, sinon false.
     */
    public boolean isStackEmpty() {
        return stack.isEmpty();
    }

    /**
     * Efface la valeur actuelle et réinitialise la pile.
     */
    public void clear() {
        value = "";
        isMutable = true;
        clearStack();
    }

    /**
     * Vide la pile des valeurs.
     */
    private void clearStack() {
        while (!stack.isEmpty()) {
            stack.pop();  // Dépile tous les éléments
        }
    }

    /**
     * Ajoute une valeur entière au champ de saisie.
     *
     * @param x La valeur à ajouter.
     */
    public void appendValue(int x) {
        if (clearedOnNextInput) {
            value = "";
            clearedOnNextInput = false;
        }
        value += x;
    }

    /**
     * Ajoute un point décimal à la valeur actuelle.
     */
    public void appendDot() {
        if (value.isEmpty()) value += "0";
        if (!value.contains(".")) value += ".";
    }

    /**
     * Prépare l'état pour la saisie du prochain opérande.
     */
    public void prepareForNextOperand() {
        clearedOnNextInput = true;
        waitingForNextOperand = true;
    }

    /**
     * Change le signe de la valeur actuelle.
     */
    public void changeSign() {
        if (!hasError) {
            double val = value();
            value = val > 0 ? "-" + value : value.substring(1);
        }
    }

    /**
     * Supprime le dernier chiffre de la valeur actuelle.
     */
    public void delLastValue() {
        if (isMutable && !value.isEmpty()) {
            value = value.substring(0, value.length() - 1);
        }
    }

    /**
     * Stocke la valeur actuelle dans la mémoire.
     */
    public void storeValue() {
        if (!hasError) {
            memory = value.isEmpty() ? "0" : value;
        }
    }

    /**
     * Rappelle la valeur stockée en mémoire.
     */
    public void recallValue() {
        value = memory;
        isMutable = false;
    }

    /**
     * Retourne la valeur actuelle sous forme de chaîne de caractères.
     *
     * @return La valeur actuelle ou l'erreur si elle est présente.
     */
    public String getValueString() {
        return hasError ? error : (value.isEmpty() ? "0" : value);
    }

    /**
     * Retourne la valeur actuelle sous forme de double.
     *
     * @return La valeur actuelle.
     */
    public double value() {
        try {
            return value.isEmpty() ? 0 : Double.parseDouble(value);
        } catch (NumberFormatException e) {
            setError("Invalid number format: " + value);
            return 0;
        }
    }

    /**
     * Définit la valeur actuelle.
     *
     * @param x La valeur à définir.
     */
    public void setValue(double x) {
        value = formatValue(x);
        isMutable = false;
    }

    /**
     * Formate la valeur pour supprimer la partie décimale si elle est inutile.
     *
     * @param x La valeur à formater.
     * @return La valeur formatée sous forme de chaîne.
     */
    private String formatValue(double x) {
        return (x == (long) x) ? String.valueOf((long) x) : Double.toString(x);
    }

    /**
     * Définit le message d'erreur actuel.
     *
     * @param errorMessage Le message d'erreur.
     */
    public void setError(String errorMessage) {
        error = errorMessage;
        hasError = true;
    }

    /**
     * Définit l'opérateur courant.
     *
     * @param operator L'opérateur à définir.
     */
    public void setCurrentOperator(Operator operator) {
        currentOperator = operator;
    }

    /**
     * Retourne l'opérateur courant.
     *
     * @return L'opérateur courant.
     */
    public Operator getCurrentOperator() {
        return currentOperator;
    }

    /**
     * Vérifie si l'état attend la saisie d'un prochain opérande.
     *
     * @return true si l'état attend un prochain opérande, sinon false.
     */
    public boolean isWaitingForNextOperand() {
        return waitingForNextOperand;
    }

    /**
     * Définit si l'état attend la saisie d'un prochain opérande.
     *
     * @param waiting true pour attendre un prochain opérande, sinon false.
     */
    public void setWaitingForNextOperand(boolean waiting) {
        waitingForNextOperand = waiting;
    }

    /**
     * Retourne la taille actuelle de la pile.
     *
     * @return Le nombre d'éléments dans la pile.
     */
    public int stackSize() {
        return stack.size();
    }
}

