package calculator;

abstract class Operator {
    abstract void execute();

    protected void executeBinaryOperation(double operand1, double operand2, State state, BinaryOperation operation) {
        try {
            double result = operation.compute(operand1, operand2);
            state.setValue(result);
        } catch (ArithmeticException e) {
            state.setError(e.getMessage());
        }
    }

    double compute(double operand1, double operand2) {
        return 0;
    }
}

class Digit extends Operator {
    int digit;

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

class BackSpace extends Operator {
    @Override
    void execute() {
        State.getState().delLastValue();
    }
}

class ClearError extends Operator {
    @Override
    void execute() {
        State.getState().clearError();
    }
}

class Clear extends Operator {
    @Override
    void execute() {
        State.getState().clear();
    }
}

class MemoryRecall extends Operator {
    @Override
    void execute() {
        State.getState().recallValue();
    }
}

class MemoryStore extends Operator {
    @Override
    void execute() {
        State.getState().storeValue();
    }
}

class ChangeSign extends Operator {
    @Override
    void execute() {
        State.getState().changeSign();
    }
}

class AppendDot extends Operator {
    @Override
    void execute() {
        State.getState().appendDot();
    }
}

abstract class UnaryOperation extends Operator {
    private final UnaryFunction operation;

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
            //state.pushToStack(result);
            state.setValue(result);
        } catch (ArithmeticException e) {
            state.setError(e.getMessage());
        }
        state.prepareForNextOperand();
    }

    @FunctionalInterface
    interface UnaryFunction {
        double apply(double operand);
    }
}

class Square extends UnaryOperation {
    Square() {
        super(operand -> operand * operand);
    }
}

class SquareRoot extends UnaryOperation {
    SquareRoot() {
        super(operand -> {
            if (operand < 0) throw new ArithmeticException("Cannot compute square root of a negative number.");
            return Math.sqrt(operand);
        });
    }
}

class Reciprocal extends UnaryOperation {
    Reciprocal() {
        super(operand -> {
            if (operand == 0) throw new ArithmeticException("Cannot compute reciprocal of zero.");
            return 1 / operand;
        });
    }
}


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

    abstract double compute(double operand1, double operand2);
}

class Addition extends BinaryOperation {
    @Override
    double compute(double operand1, double operand2) {
        return operand1 + operand2;
    }
}

class Subtraction extends BinaryOperation {
    @Override
    double compute(double operand1, double operand2) {
        return operand1 - operand2;
    }
}

class Multiplication extends BinaryOperation {
    @Override
    double compute(double operand1, double operand2) {
        return operand1 * operand2;
    }
}

class Division extends BinaryOperation {
    @Override
    double compute(double operand1, double operand2) {
        if (operand2 == 0) {
            throw new ArithmeticException("Illegal division");
        }
        return operand1 / operand2;
    }
}

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
