package client.commands;

import client.cli.Console;

/**
 * Abstract base class for all commands in the application.
 */
public abstract class  AbstractCommand implements Command {

        private final String nameCommand; 
        private final String description; 

    /**
     * Constructor for the AbstractCommand class.
     * 
     * @param nameCommand the name of the command
     * @param description the description of the command
     */
    public AbstractCommand(String nameCommand, String description) {
        this.nameCommand = nameCommand; 
        this.description = description; 
    } 


    /**
     * Validates that the command does not have any arguments.
     * 
     * @param argument
     * @param console
     * @return true if validation is successful, false otherwise
     */
    protected boolean validateNoArgument(String argument, Console console) {
        if (!argument.trim().isEmpty()) {
            console.printError("Command '" + getName() + "' does not accept arguments.");
            return false;
        }
        return true;
    }



    /**
     * Validates that the command has an argument.
     *
     * @param argument the command argument
     * @param console  the console for printing error messages
     * @return true if validation is successful, false otherwise
     */
    protected boolean validateHasArgument(String argument, Console console) {
        if (argument == null || argument.trim().isEmpty()) {
            console.printError("Command '" + getName() + "' requires an argument.");
            return false;
        }
        return true;
    }


    /**
     * @return the name of the command
     */
    @Override
    public String getName() {
        return nameCommand; 
    } 


    /**
     * @return the description of the command
     */
    @Override
    public String getDescription(){
        return description; 
    }




}
