package View;

import View.Commands.Command;

import java.util.*;

class TextMenu {
    private Map<String, Command> commands;

    TextMenu() {
        commands = new TreeMap<>();
    }

    void addCommand(Command c) {
        commands.put(c.getKey(), c);
    }

    void show() {
        Scanner scanner = new Scanner(System.in);

        boolean isRunning = true;
        while (isRunning) {
            printMenu();
            System.out.print("Input the option: ");
            String key = scanner.nextLine();
            Command command = commands.get(key.toUpperCase());
            if (command == null) {
                System.out.println("Invalid option\n");
                continue;
            }
            if (command.getKey().equals("0"))
                isRunning = false;
            command.execute();
        }
    }

    private void printMenu() {
        System.out.println("Option: Example");
        for (Command com : commands.values()) {
            String line = String.format("%s: %s", com.getKey(), com.getDescription());
            System.out.println(line);
        }
    }
}
