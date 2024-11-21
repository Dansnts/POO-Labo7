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
