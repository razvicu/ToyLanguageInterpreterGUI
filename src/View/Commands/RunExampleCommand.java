package View.Commands;

import Controller.Controller;

import java.io.IOException;

public class RunExampleCommand extends Command {
    private Controller controller;

    public RunExampleCommand(String key, String description, Controller controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        try {
            controller.setDisplayFlag();
            controller.allStepsExecution();
        }catch(IOException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }
}
