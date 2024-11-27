package calculator;



public class State {

    private static State instance;

    private String value = "";
    private String memory = "0";
    private String error = "";
    private boolean hasError = false;
    private boolean isMutable = true;
    private double operand1 = 0;
    private Operator currentOperator = null;
    private boolean waitingForNextOperand = false;
    private boolean clearedOnNextInput = false;

    private State() {}

    private Stack<Double> stack = new Stack<>();

    public void pushToStack(double value) {
        stack.push(value);
    }

    public double popFromStack() {
        if (stack.isEmpty()) {
            setError("Stack is empty");
            return 0;
        }
        return stack.pop();
    }

    public String stackToString() {
        return stack.toString();
    }

    public Object[] stackToArray() {
        return stack.toArray();
    }

    public static State getState() {
        if (instance == null) {
            instance = new State();
        }
        return instance;
    }

    public void clearError() {
        value = "";
        error = "";
        hasError = false;
        isMutable = true;

    }

    public boolean isStackEmpty(){
        return stack.isEmpty();
    }

    public void clear() {
        value = "";
        isMutable = true;
        clearStack();
    }

    private void clearStack() {
        while (!stack.isEmpty()) {
            stack.pop();       // Dépile tous les éléments
        }
    }

    public void appendValue(int x) {
        if (clearedOnNextInput) {
            value = "";
            clearedOnNextInput = false;
        }
        value += x;
    }

    public void appendDot() {
        if (value.isEmpty()) value += "0";
        if (!value.contains(".")) value += ".";
    }

    public void prepareForNextOperand() {
        operand1 = value();
        clearedOnNextInput = true;
        waitingForNextOperand = true;
    }

    public void changeSign() {
        if (!hasError) {
            double val = value();
            value = val > 0 ? "-" + value : value.substring(1);
        }
    }

    public void reciprocal() {
        if (value() == 0) {
            setError("cannot divide by 0");
        } else {
            setValue(1 / value());
        }
    }

    public void square() {
        setValue(Math.pow(value(), 2));
    }

    public void squareRoot() {
        if (value() < 0) {
            setError("cannot take square root of negative value");
        } else {
            setValue(Math.sqrt(value()));
        }
    }

    public void delLastValue() {
        if (isMutable && !value.isEmpty()) {
            value = value.substring(0, value.length() - 1);
        }
    }

    public void storeValue() {
        if (!hasError) {
            memory = value.isEmpty() ? "0" : value;
        }
    }

    public void recallValue() {
        value = memory;
        isMutable = false;
        clearError();
    }

    public String getValueString() {
        return hasError ? error : (value.isEmpty() ? "0" : value);
    }

    public String getMemory() {
        return memory;
    }

    public double value() {
        try {
            return value.isEmpty() ? 0 : Double.parseDouble(value);
        } catch (NumberFormatException e) {
            setError("Invalid number format: " + value);
            return 0;
        }
    }

    public void setValue(double x) {
        value = formatValue(x);
        isMutable = false;
    }

    private String formatValue(double x) {
        return (x == (long) x) ? String.valueOf((long) x) : Double.toString(x);
    }

    public void setError(String errorMessage) {
        error = errorMessage;
        hasError = true;
    }

    public void setOperand1(double operand1) {
        this.operand1 = operand1;
    }

    public double getOperand1() {
        return operand1;
    }

    public void setCurrentOperator(Operator operator) {
        currentOperator = operator;
    }

    public Operator getCurrentOperator() {
        return currentOperator;
    }

    public boolean isWaitingForNextOperand() {
        return waitingForNextOperand;
    }

    public void setWaitingForNextOperand(boolean waiting) {
        waitingForNextOperand = waiting;
    }
}
