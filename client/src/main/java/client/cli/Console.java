package client.cli;

/**
 * Console interface for handling input and output operations.
 */
public interface Console {

    void print(Object object);

    void println(Object object);

    void printError(Object object);

    void printTable(Object object, Object object2);

    void ps1();

    void ps2();


}
