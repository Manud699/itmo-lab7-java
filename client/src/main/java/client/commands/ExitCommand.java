package client.commands;

import client.cli.Console;


public class ExitCommand extends AbstractCommand  {


    public ExitCommand() {
        super("exit", "Terminates the program");

    }


    public int execute(String argms) {
        System.exit(0);
        return 1;
    }
}
