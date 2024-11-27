package calculator;

abstract class Operator {
  abstract void execute();

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

class Reciprocal extends Operator {
  @Override
  void execute() {
    State.getState().reciprocal();
  }
}

class Square extends Operator {
  @Override
  void execute() {
    State state = State.getState();
    state.square();
    state.pushToStack(state.value());
    state.prepareForNextOperand();
  }
}

class SquareRoot extends Operator {
  @Override
  void execute() {
    State state = State.getState();
    state.squareRoot();
    state.pushToStack(state.value());
    state.prepareForNextOperand();
  }
}

abstract class BinaryOperation extends Operator {
  @Override
  void execute() {
    State state = State.getState();

    // Si une valeur est déjà sur la pile, effectuez immédiatement le calcul
    if (state.hasOperandOnStack()) {
      double operand1 = state.popFromStack(); // Récupère l'opérande précédent
      double operand2 = state.value(); // Utilise la valeur courante
      try {
        double result = compute(operand1, operand2);
        state.setValue(result); // Met à jour la valeur courante
      } catch (ArithmeticException e) {
        state.setError(e.getMessage());
      }
    }

    // Place l'opérateur courant et prépare pour le prochain opérande
    state.pushToStack(state.value());
    //state.setCurrentOperator(this);
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
      throw new ArithmeticException("Division par zéro");
    }
    return operand1 / operand2;
  }
}

class Enter extends Operator {
  @Override
  void execute() {
    State state = State.getState();
    double currentValue = state.value();
    state.pushToStack(currentValue); // Pousse la valeur courante sur la pile
    state.prepareForNextOperand(); // Prépare l'entrée suivante

    // Évaluation si un opérateur est en attente
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
      state.setCurrentOperator(null); // Réinitialise l'opérateur courant
      state.setWaitingForNextOperand(false);
    }
  }
}
