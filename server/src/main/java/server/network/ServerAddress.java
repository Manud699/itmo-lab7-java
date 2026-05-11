package server.network;

import common.util.NumberParseSafe;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerAddress {

    private final static Logger logger = Logger.getLogger(ServerAddress.class.getName());

    public static int parsePort(String[]argms){
        if(argms.length < 1) {
            logger.log(Level.SEVERE, "Missing arguments. Correct usage: java -jar server.jar <file.csv> <port>");
            System.exit(1);
        }
        Optional<Integer> optionalPort = NumberParseSafe.parse(argms[0],Integer::parseInt);
        if(optionalPort.isEmpty()){
            logger.log(Level.SEVERE,"Invalid port format " + argms[0]);
            System.exit(1);
        }
        int port = optionalPort.get();
        if(port < 1 || port > 65535){
            logger.log(Level.SEVERE,"Error, the port range must be between 1 and 65535");
            System.exit(1);
        }
        return port;
    }
}
