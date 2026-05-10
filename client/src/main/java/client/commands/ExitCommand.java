package client.commands;

import client.cli.Console;


public class ExitCommand extends AbstractCommand  {

    private final Console console;


    public ExitCommand(Console console) {
        super("exit", "Terminates the program");
        this.console = console;
    }


    public int execute(String argms) {
        System.exit(0);
        return 1;
    }
}
