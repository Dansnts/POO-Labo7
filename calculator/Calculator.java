package calculator;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Calculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        State state = State.getState();
        Map<String, Operator> commandMap = new HashMap<>();
        commandMap.put("+", new Addition());
        commandMap.put("-", new Subtraction());
        commandMap.put("*", new Multiplication());
        commandMap.put("/", new Division());
        commandMap.put("sqrt", new SquareRoot());
        commandMap.put("square", new Square());
        commandMap.put("inv", new Reciprocal());
        commandMap.put("clear", new Clear());

        System.out.println("Calculator (type 'exit' to quit)");
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                break;
            }
            if (input.matches("-?\\d+(\\.\\d+)?")) {
                double number = Double.parseDouble(input);
                state.setValue(number);
                state.pushToStack(number);
            }
            else if (commandMap.containsKey(input.toLowerCase())) {
                commandMap.get(input.toLowerCase()).execute();
                state.pushToStack(state.value());
            } else {
                System.out.println("Unknown command: " + input);
                continue;
            }
            System.out.println(state.stackToString());
        }

        scanner.close();
    }
}
