package server.commands;

import common.network.*;


/**
 * Abstract base class for all commands in the application.
 */
public abstract class AbstractCommand implements Command {

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


    protected Response validateNoArgument(Request request) {
        String arg = request.getCommandArgument();
        if (arg != null && !arg.trim().isEmpty()) {
            var result = Result.failure("Error: el comando '" + getName() + "' NO acepta argumentos.");
            return new Response(result);
        }
        return null;
    }

    /**
     * Valida que el comando SÍ tenga un argumento.
     */
    protected Response validateHasArgument(Request request) {
        String arg = request.getCommandArgument();
        if (arg == null || arg.trim().isEmpty()) {
            var result = Result.failure("Error: el comando '" + getName() + "' requiere un argumento.");
            return new Response(result);
        }
        return null;
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
