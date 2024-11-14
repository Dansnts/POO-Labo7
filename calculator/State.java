package calculator;

public class State {

    private String value;
    private String memory;
    private String error;

    private boolean hasError;
    private boolean isMutable = true;

    /**
     * @brief Clears the error state.
     */
    private void clearError() {
        value = "";
        error = "";
        hasError = false;
        isMutable = true;
    }

    /**
     * @brief Appends the provided digit to the current value.
     *
     * @param x The digit to be appended
     */
    public void appendValue(int x) {
        value += x;
    }

    /**
     * @brief Appends a decimal point to the current value.
     */
    public void appendDot() {

        // If the value is empty, adds "0" before the decimal point
        if (value.isEmpty())
            value += "0";

        // If the value doesn't contain a decimal point, appends it
        if (!value.contains("."))
            value += ".";
    }

    /**
     * @brief Changes the sign of the current value.
     */
    public void changeSign() {
        double val = value();
        if (!hasError) {
            if (val > 0)
                value = "-" + value;
            else { // val < 0
                // Removes the negative sign if negative
                value = value.substring(1);
            }
        }
    }

    /**
     * @brief Performs the reciprocal operation (1/x).
     */
    public void reciprocal() {
        if (value() == 0) {
            hasError = true;
            error = "cannot divide by 0";
        } else
            setValue(1 / value());

    }

    /**
     * @brief Performs the square operation (x^2).
     */
    public void square() {
        setValue(Math.pow(value(), 2));
    }

    /**
     * @brief Performs the square root operation.
     */
    public void squareRoot() {
        if (value() < 0) {
            hasError = true;
            error = "cannot set square roots values below 0";
        } else
            setValue(Math.sqrt(value()));

    }


    /**
     * @brief Performs the backspace operation (removing the last character).
     */
    public void delLastValue() {
        if (isMutable) {
            if (!value.isEmpty())
                value = value.substring(0, value.length() - 1);
        }
    }

    /**
     * @brief Stores the current value in memory.
     */
    public void storeValue() {
        value();
        if (!hasError) {
            memory = value;
        }
    }

    /**
     * @brief Recalls the value stored in memory.
     */
    public void recallValue() {
        value = memory;
        isMutable = false;
        clearError();
    }

    /**
     * @brief Clears any error state.
     */
    public void clearErrors() {
        clearError();
    }


    /**
     * @brief Retrieves the numeric value from the current string value.
     *
     * @return The numeric value parsed from the string, or 0 if empty
     */
    private double value() {
        try {
            if (value.isEmpty())
                return 0;

            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            hasError = true;
            error = "Error : format isn't correct " + value;
            return 0;
        }
    }

    /**
     * @brief Sets the current value to the provided numeric value.
     *
     * @param x The numeric value to set
     */
    private void setValue(double x) {
        value = Double.toString(x); // Converts the numeric value to string for display
        isMutable = false; // Indicates that the value is no longer mutable
    }

    /**
     * @brief Retrieves the current value as a string.
     *
     * @return The string representation of the current value or error message
     */
    public String getValueString() {
        if (hasError) {
            return error;
        } else if (value.isEmpty()) {
            return "0";
        }
        return value;
    }

}
