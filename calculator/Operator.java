package calculator;

abstract class Operator
{
  abstract void execute();
}

class Digit extends Operator{
  int digit;
  Digit(int digit){
    this.digit = digit;
  }
  @Override
  void execute() {
    State.getState().appendValue(digit);
  }
}

class BackSpace extends Operator{
  @Override
  void execute() {
    State.getState().delLastValue();
  }
}
class ClearError extends Operator {

  @Override
  void execute() {
    State.getState().clearErrors();
  }
}
class Clear extends Operator {

  @Override
  void execute() {
    State.getState().clearError();
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
    State.getState().square();
  }
}
class SquareRoot extends Operator {

  @Override
  void execute() {
    State.getState().squareRoot();
  }
}
class Enter extends Operator {

  @Override
  void execute() {
    // TOBE-DONE
  }
}

class Addition extends Operator{
  @Override
  void execute() {
    // TOBE-DONE
  }
}
class Soustraction extends Operator{
  @Override
  void execute() {
    // TOBE-DONE
  }
}
class Multiplication extends Operator{
  @Override
  void execute() {
    // TOBE-DONE
  }
}
class Division extends Operator{
  @Override
  void execute() {
    // TOBE-DONE
  }
}

