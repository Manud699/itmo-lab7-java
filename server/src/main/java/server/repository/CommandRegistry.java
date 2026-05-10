package server.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.network.Result;
import server.commands.Command;
import common.network.Request;
import common.network.Response;


/**
 * Class CommandRegistry
 * Description: This class is responsible for managing the registration and execution of commands in the application.
 */
public class CommandRegistry {

    private final Map<String, Command> commands;    

    public CommandRegistry() {
        this.commands = new HashMap<>();
    }



    public Response executeCommand(Request request) {
        Command command = commands.get(request.getCommandName().trim().toLowerCase());
        if(command == null)
            return new Response(Result.failure("Unrecognized input:"+ request.getCommandName()));

        Response response = command.execute(request);;
        return response;
    }



    public void addCommand(Command command) {
        String keySafe = command.getName().toLowerCase().trim();
        commands.put(keySafe, command);
    }



    public List<Command> getCommands() {
        return commands.values().stream().toList();  
    }

}
