package client.repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.Deque;

import client.cli.Console;
import client.cli.InputProvider;


/**
 * ScriptExecutionStack is responsible for managing the execution of scripts in the application.
 */
public class ScriptExecutionStack {
    
    private final Deque<String> activeScripts;

    private final InputProvider inputProvider; 
    

    /**
     * Constructor for the ScriptExecutionStack class.
     * @param inputProvider the input provider for handling file input
     * @param console the console for input/output operations
     */
    public ScriptExecutionStack(InputProvider inputProvider, Console console) {
        this.activeScripts = new ArrayDeque<>();
        this.inputProvider = inputProvider; 
    }


    /**
     * Connects the script execution stack to a file scanner.
     * 
     * @param file the file to connect to
     * @throws FileNotFoundException if the file is not found
     */
    public void connectToFileScanner(File file) throws FileNotFoundException {
        String absolutePath = file.getAbsolutePath();
        activeScripts.push(absolutePath);       
        inputProvider.connectToFile(file);       
    }



    /**
     * Checks if a script is currently active.
     * 
     * @param absolutePath the absolute path of the script to check
     * @return true if the script is active, false otherwise
     */
    public boolean isActiveScript(String absolutePath){
        return activeScripts.contains(absolutePath);
    }



    /**
    * Exits the current script by popping it from the stack and disconnecting the file scanner.   
    */
    public void exitCurrentScript() {
        if (!activeScripts.isEmpty()) {
            activeScripts.pop();
            inputProvider.disconnectCurrentFile();;
        }
    }

}

