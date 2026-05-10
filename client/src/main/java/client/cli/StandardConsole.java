package client.cli;


/**
 * StandardConsole is a concrete implementation of the Console interface that provides standard input and output operations using the system console.
 */
public  class StandardConsole implements Console  {


    private final String PS1 = "$ ";
    private final String PS2 = "> ";


    /**
     * Prints the specified object to the console.
     * @param object the object to print
     */
    @Override
    public  void print(Object object) {
        System.out.print(object);
    }


    /**
     * Prints the specified object to the console followed by a newline.
     * @param object the object to print
     */
    @Override
    public void println(Object object) {
        System.out.println(object);
        System.out.flush();
    }


    /**
     * Prints a table row with the specified left and right elements.
     * @param elementleft the left element
     * @param elementRight the right element
     */
    @Override
    public void printTable(Object elementleft, Object elementRight) {
        System.out.printf(" %-32s%-1s%n", elementleft, elementRight);
    }

    /**
     * Prints an error message to the console in red color.
     */
    @Override
    public void printError(Object message) {
    System.out.println("\u001B[31m" + message + "\u001B[0m");
    }



    /**
     * Prints the PS2. Means that the application is waiting for a continuation of a multi-line command.
     */
    @Override
    public void ps2() {
        print(PS2);
        System.out.flush();
    }


    /**
     * Prints the PS1. Means that the application is waiting for a new command.
     */
    public void ps1() {
        print(PS1);
        System.out.flush();
    }

}
