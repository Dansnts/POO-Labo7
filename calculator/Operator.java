/**
 * @author Aubry Antoine
 * @author Faria dos Santos Dani Tiago
 */

package calculator;

/**
 * Classe abstraite représentant un opérateur.
 */
abstract class Operator {
    /**
     * Méthode abstraite d'exécution, à implémenter dans les sous-classes.
     */
    abstract void execute();

    /**
     * Exécute une opération binaire en utilisant les deux opérandes données.
     *
     * @param operand1 Premier opérande.
     * @param operand2 Deuxième opérande.
     * @param state    L'état actuel de la calculatrice.
     * @param operation L'opération binaire à exécuter.
     */
    protected void executeBinaryOperation(double operand1, double operand2, State state, BinaryOperation operation) {
        try {
            double result = operation.compute(operand1, operand2);
            state.setValue(result);
        } catch (ArithmeticException e) {
            state.setError(e.getMessage());
        }
    }

    /**
     * Calcule une opération binaire entre deux opérandes.
     *
     * @param operand1 Premier opérande.
     * @param operand2 Deuxième opérande.
     * @return Le résultat du calcul.
     */
    double compute(double operand1, double operand2) {
        return 0;
    }
}

/**
 * Classe représentant un chiffre dans la calculatrice.
 */
class Digit extends Operator {
    int digit;

    /**
     * Constructeur de Digit.
     *
     * @param digit Le chiffre à représenter.
     */
    Digit(int digit) {
        this.digit = digit;
    }

    @Override
    void execute() {
        State state = State.getState();

        if (state.isWaitingForNextOperand()) {
            state.setValue(0); // Réinitialise l'affichage
            state.setWaitingForNextOperand(false); // Désactive l'attente
        }

        state.appendValue(digit); // Ajoute le chiffre
    }
}

/**
 * Classe représentant l'opérateur de suppression du dernier chiffre.
 */
class BackSpace extends Operator {
    @Override
    void execute() {
        State.getState().delLastValue();
    }
}

/**
 * Classe représentant l'opérateur de suppression de l'erreur.
 */
class ClearError extends Operator {
    @Override
    void execute() {
        State.getState().clearError();
    }
}

/**
 * Classe représentant l'opérateur de réinitialisation complète.
 */
class Clear extends Operator {
    @Override
    void execute() {
        State.getState().clear();
    }
}

/**
 * Classe représentant l'opérateur de rappel de la mémoire.
 */
class MemoryRecall extends Operator {
    @Override
    void execute() {
        State.getState().recallValue();
    }
}

/**
 * Classe représentant l'opérateur de stockage de la valeur en mémoire.
 */
class MemoryStore extends Operator {
    @Override
    void execute() {
        State.getState().storeValue();
    }
}

/**
 * Classe représentant l'opérateur de changement de signe.
 */
class ChangeSign extends Operator {
    @Override
    void execute() {
        State.getState().changeSign();
    }
}

/**
 * Classe représentant l'opérateur d'ajout d'un point décimal.
 */
class AppendDot extends Operator {
    @Override
    void execute() {
        State.getState().appendDot();
    }
}

/**
 * Classe abstraite pour les opérations unaires.
 */
abstract class UnaryOperation extends Operator {
    private final UnaryFunction operation;

    /**
     * Constructeur d'opération unaire.
     *
     * @param operation La fonction unaire à appliquer.
     */
    UnaryOperation(UnaryFunction operation) {
        this.operation = operation;
    }

    @Override
    void execute() {
        State state = State.getState();
        if (state.stackSize() < 1) {
            System.out.println("Not enough operands in the stack.");
            return;
        }

        double operand = state.popFromStack();
        try {
            double result = operation.apply(operand);
            state.setValue(result);
        } catch (ArithmeticException e) {
            state.setError(e.getMessage());
        }
        state.prepareForNextOperand();
    }

    /**
     * Interface fonctionnelle pour les fonctions unaires.
     */
    @FunctionalInterface
    interface UnaryFunction {
        double apply(double operand);
    }
}

/**
 * Classe représentant l'opération de carré.
 */
class Square extends UnaryOperation {
    Square() {
        super(operand -> operand * operand);
    }
}

/**
 * Classe représentant l'opération de racine carrée.
 */
class SquareRoot extends UnaryOperation {
    SquareRoot() {
        super(operand -> {
            if (operand < 0) throw new ArithmeticException("Cannot compute square root of a negative number.");
            return Math.sqrt(operand);
        });
    }
}

/**
 * Classe représentant l'opération de réciproque (1/x).
 */
class Reciprocal extends UnaryOperation {
    Reciprocal() {
        super(operand -> {
            if (operand == 0) throw new ArithmeticException("Cannot compute reciprocal of zero.");
            return 1 / operand;
        });
    }
}

/**
 * Classe abstraite pour les opérations binaires.
 */
abstract class BinaryOperation extends Operator {
    @Override
    void execute() {
        State state = State.getState();
        if (state.stackSize() < 2) {
            System.out.println("Not enough operands in the stack.");
            return;
        }
        double operand2 = state.popFromStack();
        double operand1 = state.popFromStack();
        executeBinaryOperation(operand1, operand2, state, this);
        state.prepareForNextOperand();
    }

    /**
     * Calcule une opération binaire entre deux opérandes.
     *
     * @param operand1 Premier opérande.
     * @param operand2 Deuxième opérande.
     * @return Le résultat du calcul.
     */
    abstract double compute(double operand1, double operand2);
}

/**
 * Classe représentant l'opération d'addition.
 */
class Addition extends BinaryOperation {
    @Override
    double compute(double operand1, double operand2) {
        return operand1 + operand2;
    }
}

/**
 * Classe représentant l'opération de soustraction.
 */
class Subtraction extends BinaryOperation {
    @Override
    double compute(double operand1, double operand2) {
        return operand1 - operand2;
    }
}

/**
 * Classe représentant l'opération de multiplication.
 */
class Multiplication extends BinaryOperation {
    @Override
    double compute(double operand1, double operand2) {
        return operand1 * operand2;
    }
}

/**
 * Classe représentant l'opération de division.
 */
class Division extends BinaryOperation {
    @Override
    double compute(double operand1, double operand2) {
        if (operand2 == 0) {
            throw new ArithmeticException("Illegal division");
        }
        return operand1 / operand2;
    }
}

/**
 * Classe représentant l'opérateur Enter.
 */
class Enter extends Operator {
    @Override
    void execute() {
        State state = State.getState();
        double currentValue = state.value();
        state.pushToStack(currentValue);
        state.prepareForNextOperand();
        Operator currentOperator = state.getCurrentOperator();
        if (currentOperator != null) {
            double operand1 = state.popFromStack();
            double operand2 = currentValue;
            try {
                double result = currentOperator.compute(operand1, operand2);
                state.setValue(result);
            } catch (ArithmeticException e) {
                state.setError(e.getMessage());
            }
            state.setCurrentOperator(null);
            state.setWaitingForNextOperand(false);
        }
    }
}
