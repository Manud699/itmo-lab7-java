package client.repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.Deque;
import client.cli.Console;
import client.cli.InputProvider;



public class ScriptExecutionStack {
    
    private final Deque<String> activeScripts;

    private final InputProvider inputProvider; 


    public ScriptExecutionStack(InputProvider inputProvider, Console console) {
        this.activeScripts = new ArrayDeque<>();
        this.inputProvider = inputProvider; 
    }


    public void connectToFileScanner(File file) throws FileNotFoundException {
        String absolutePath = file.getAbsolutePath();
        activeScripts.push(absolutePath);       
        inputProvider.connectToFile(file);       
    }


    public boolean isActiveScript(String absolutePath){
        return activeScripts.contains(absolutePath);
    }


    public void exitCurrentScript() {
        if (!activeScripts.isEmpty()) {
            activeScripts.pop();
            inputProvider.disconnectCurrentFile();;
        }
    }
}

