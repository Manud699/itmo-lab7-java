package server.multithread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExecutorUtils {

    private static final Logger logger = Logger.getLogger(ExecutorService.class.getName());
    private ExecutorUtils(){
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static void shutdownAndAwaitTermination(ExecutorService pool, String poolName){
        pool.shutdown();
        logger.log(Level.INFO, "Initiating graceful shutdown for " + poolName);
        try {
            if(!pool.awaitTermination(10, TimeUnit.SECONDS)){
                logger.warning( poolName+ "did not terminate in 10s. Forcing shutdown...");
                pool.shutdownNow();

                if(!pool.awaitTermination(10, TimeUnit.SECONDS))
                    logger.warning( poolName+ "completely failed to terminate.");
            }
            logger.info(poolName + " shut down successfully.");
        } catch (InterruptedException e) {
            logger.warning("Shutdown process interrupted for " + poolName + ". Forcing immediate shutdown.");
            pool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
