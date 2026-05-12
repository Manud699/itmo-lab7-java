package server.lifecycle;

import server.network.NetworkServer;
import server.storagedb.DatabaseConnection;
import java.util.logging.Logger;

public class ShutdownCoordinator {

    private static final Logger logger = Logger.getLogger(ShutdownCoordinator.class.getName());

    private final DatabaseConnection databaseConnection;
    private final NetworkServer networkServer;


    public ShutdownCoordinator(DatabaseConnection databaseConnection, NetworkServer networkServer){
        this.databaseConnection = databaseConnection;
        this.networkServer = networkServer;
    }


    public void pickShutDownHook(){
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                logger.info("Termination signal received (Ctrl+C). Initiating graceful shutdown...");
                networkServer.shutdownHook();
                databaseConnection.shutdownHook();
            }));
        }
}
