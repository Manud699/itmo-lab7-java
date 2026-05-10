package client.commands;

import client.cli.Console;
import client.repository.CommandRegistry;


public class HelpCommand extends AbstractCommand {

    private final Console console;
    private final CommandRegistry commandRegistry;


    public HelpCommand(CommandRegistry commandManager, Console console){
        super("help", "Prints a list of all available commands and their descriptions");
        this.commandRegistry = commandManager;
        this.console = console;
    }


    @Override
    public int execute(String argument) {

        if(!validateNoArgument(argument, console)) {
            return 1;
        }
        console.println("Commands: ");
        commandRegistry.getCommands().forEach(command -> console.printTable(command.getName(), command.getDescription()));
        return 0;
    }
}
