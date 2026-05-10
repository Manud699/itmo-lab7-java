package client.commands;


public interface Command {

    int execute(String argms);
    String getName(); 
    String getDescription();
}   
