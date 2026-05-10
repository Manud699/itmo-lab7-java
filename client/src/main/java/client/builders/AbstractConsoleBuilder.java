package client.builders;

import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Predicate;

import client.cli.Console;
import client.cli.InputProvider;


/**
 * AbstractConsoleBuilder is an abstract class that provides common methods for building objects through console input.
 * It uses an InputProvider to get the current Scanner and a Console to interact with the usercommands.
 * Subclasses of AbstractConsoleBuilder will implement the build() method to create specific objects based on usercommands input.
 */
public abstract class AbstractConsoleBuilder<T>  {


    private final InputProvider inputProvider;
    private Scanner scanner;
    private final Console console;



    public AbstractConsoleBuilder(InputProvider inputProvider, Console console) {
        this.inputProvider = inputProvider;
        this.console = console;
    }

    public abstract T build();



    public boolean isSafeNextLine(Scanner scanner) {
        if(!scanner.hasNextLine()) {
            return false;
        }
        return true;
    }



    public String askString(String prompt, String restrictions, Predicate<String> validator) {
        while(true) {
            scanner = inputProvider.getCurrentScanner();
            if(inputProvider.isInteractiveMode()){
                console.ps2();
                console.print("Enter " + prompt + " " + restrictions + ":" );
            }
            if(!isSafeNextLine(scanner)){
                console.println("Exiting program...");
                System.exit(0);
            }
            String inputLine = scanner.nextLine();
            if(validator.test(inputLine)) {
                return inputLine;
            }
            if(inputProvider.isInteractiveMode()) {
                console.printError("Invalid input '"+inputLine+"'. Please try again.");
            } else {
                throw new IllegalArgumentException("Script Error: Invalid format for " + prompt + ". Received: '" + inputLine + "'");
            }
        }
    }


    public String askString(String prompt, Predicate<String> validator) {
        while(true) {
            scanner = inputProvider.getCurrentScanner();
            if(inputProvider.isInteractiveMode()){
                console.ps2();
                console.print(prompt);
            }
            if(!isSafeNextLine(scanner)){
                console.println("Exiting program...");
                System.exit(0);
            }
            String inputLine = scanner.nextLine();
            if(validator.test(inputLine)) {
                return inputLine;
            }
            if(inputProvider.isInteractiveMode()) {
                console.printError("Invalid input '"+inputLine+"'. Please try again.");
            } else {
                throw new IllegalArgumentException("Script Error: Invalid format for " + prompt + ". Received: '" + inputLine + "'");
            }
        }
    }



    public <N> N askNumber(String prompt, String restrictions, Predicate<N> validator, Function<String, N> parse) {
        while (true) {
            scanner = inputProvider.getCurrentScanner();
            if(inputProvider.isInteractiveMode()){
                console.ps2();
                console.print("Enter " + prompt + " " + restrictions + ":" );
            }
            if(!isSafeNextLine(scanner)){
                console.println("Exiting program...");
                System.exit(0);
            }
            String inputLine = scanner.nextLine();
            try {
                N enterUser = parse.apply(inputLine);
                if(validator.test(enterUser)){
                    return enterUser;
                }
                if(inputProvider.isInteractiveMode()){
                    console.println("Invalid input. Please try again");
                } else {
                    throw new IllegalArgumentException("Script Error: Invalid format for " + prompt + ". Received: '" + inputLine + "'");
                }
            } catch (NumberFormatException e) {
                    console.printError("Invalid number format. Please enter a valid whole number.");
                    if (inputProvider.isInteractiveMode()) {
                        continue;
                    } else {
                        throw new IllegalArgumentException("Script Error: Invalid format for " + prompt + ". Received: '" + inputLine + "'");
                    }
            }
        }
    }



    public <E extends Enum<E>> E askEnum(String prompt, E[] valoresAceptados) {
        while(true) {
            scanner = inputProvider.getCurrentScanner();
            if(inputProvider.isInteractiveMode()){
                console.ps2();
                console.println("Enter "+ prompt);
                console.print("Available options " + Arrays.toString(valoresAceptados) +":" );
            }
            if(!isSafeNextLine(scanner)){
                console.println("Exiting program...");
                System.exit(0);
            }
            String inputLine = scanner.nextLine();
            for(E item : valoresAceptados) {
                if(item.name().equals(inputLine.trim().toUpperCase())) {
                    return item;
                }
            }
            if(inputProvider.isInteractiveMode()){
                console.printError("Invalid option. Please choose from the available values. ");
            } else {
                throw new IllegalArgumentException("Fatal script error: Invalid value for  "+ prompt + ". Received: '" + inputLine + "'");
            }
        }
    }


}