package server.commands;

import common.network.*;


/**
 * Command interface that defines the structure for all commands in the application.
 */
public interface Command {

    Response execute(Request request);

    /**
     * Returns the name of the command.
     * @return the name of the command
     */
    String getName(); 
    String getDescription(); 


}   
