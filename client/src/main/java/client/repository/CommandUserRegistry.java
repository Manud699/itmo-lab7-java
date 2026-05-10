package client.repository;

import client.cli.Console;
import client.usercommands.CommandUser;
import java.util.HashMap;
import java.util.Map;

public class CommandUserRegistry {

    CommandUser commandUser;

    Map<String, CommandUser> commandsUser;
    private final Console console;

    public CommandUserRegistry(Console console){
        this.commandsUser = new HashMap<>(8);
        this.console = console;
    }



    public void addCommand(String name, CommandUser commandUser){
        commandsUser.put(name.trim().toUpperCase(), commandUser);
    }



    public int execute(String commandName){;
        String command = commandName.trim().toUpperCase();
        if(commandsUser.containsKey(command))
            return  commandsUser.get(command).execute();
        console.printError("no se reconoce la entrada. Vuelve a intentarlo");
        return 1;
    }
}
