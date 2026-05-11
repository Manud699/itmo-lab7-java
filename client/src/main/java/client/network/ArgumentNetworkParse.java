package client.network;

import client.cli.Console;
import common.util.NumberParseSafe;
import java.util.Optional;

public class ArgumentNetworkParse {

    private static final String DEFAULT_HOST = "localhost";

    private ArgumentNetworkParse() {
    }

    public static AddressNetwork parseNetworkArguments(String[] args , Console console) {
        if (args.length == 0) {
            console.printError("Error: You must provide at least the port. Example: java -jar client.jar [host] <port>");
            System.exit(1);
        }

        if (args.length == 1) {
            int port = parsePort(args[0], console);
            return new AddressNetwork(DEFAULT_HOST, port);
        }

        String host = args[0];
        int port = parsePort(args[1],console);
        return new AddressNetwork(host, port);
    }


    public static int parsePort(String portString, Console console) {
        Optional<Integer> portOptional = NumberParseSafe.parse(portString, Integer::parseInt);

        if (portOptional.isEmpty()) {
            console.printError("Error: The provided port argument is not a valid number ('" + portString + "').");
            System.exit(1);
        }
        int port = portOptional.get();
        if (port < 1 || port > 65535) {
            console.printError("Error: The port range must be between 1 and 65535.");
            System.exit(1);
        }
        return port;
    }
}