package client.repository;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import client.cli.Console;
import client.commands.Command;


/**
 * Class CommandRegistry
 * Description: This class is responsible for managing the registration and execution of commands in the application.
 */
public class CommandRegistry {

    private final Map<String, Command> commands;    
    private final Deque<String> historyCommands;
    private final Console console; 
    private final int MAX_HISTORY_COMMANDS = 11; 



    public CommandRegistry(Console console) {
        this.console = console;
        this.commands = new HashMap<>();
        this.historyCommands = new ArrayDeque<>();  
    }



    public int executeCommand(String commandName, String argument) {
        Command command = commands.get(commandName.trim().toLowerCase());
        if(command == null) {
            console.printError("Unrecognized input: '" + commandName + "'");
            return 1; 
        } 
        addToHistory(commandName);
        return command.execute(argument);
    }



    public void addCommand(Command command) {
        commands.put(command.getName(), command);
    }



    public Deque<String> getHistoryCommands() {
        return historyCommands; 
    } 



    public List<Command> getCommands() {
        return commands.values().stream().toList();  
    } 



    public void addToHistory(String commandName) {
        if(historyCommands.size() == MAX_HISTORY_COMMANDS){
            historyCommands.removeFirst();        
        }  
        historyCommands.add(commandName);
    }
}
