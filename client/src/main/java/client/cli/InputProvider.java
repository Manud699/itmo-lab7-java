package client.cli;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

/**
 * InputProvider is responsible for managing input sources for the application.
 */
public class InputProvider {

    private final Deque<Scanner> scanners;
    private boolean isInteractiveMode = true;

    /**
     * Initializes the InputProvider with a Scanner for System.in and sets the mode to interactive.
     */
    public InputProvider() {
        this.scanners = new ArrayDeque<>();
        this.scanners.push(new Scanner(System.in));
    }


    /**
     *  Connects to a file and adds it to the stack of scanners.
     *
     * @param file
     * @throws FileNotFoundException
     */
    public void connectToFile(File file) throws FileNotFoundException {
        Scanner fileScanner = new Scanner(file);
        scanners.push(fileScanner);
        this.isInteractiveMode = false;
    }


    /**
     * Disconnects the current file scanner and removes it from the stack.
     */
    public void disconnectCurrentFile() {
        if (scanners.size() > 1) {
            scanners.pop().close();
            this.isInteractiveMode = (scanners .size() == 1);
        }
    }



    /**
     * Returns the current scanner, which is the one on top of the stack.
     *
     * @return Scanner
     */
    public Scanner getCurrentScanner() {
        return scanners.peek();
    }



    /**
     * Checks if the application is currently in interactive mode.
     * @return true if in interactive mode, false otherwise
     */
    public boolean isInteractiveMode() {
        return this.isInteractiveMode;
    }

}