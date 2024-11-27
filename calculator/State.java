package calculator;

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

    public State() {}

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
        clearedOnNextInput = true;
        waitingForNextOperand = true;
    }

    public void changeSign() {
        if (!hasError) {
            double val = value();
            value = val > 0 ? "-" + value : value.substring(1);
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
    }

    public String getValueString() {
        return hasError ? error : (value.isEmpty() ? "0" : value);
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

    public int stackSize() { return stack.size();
    }
}
