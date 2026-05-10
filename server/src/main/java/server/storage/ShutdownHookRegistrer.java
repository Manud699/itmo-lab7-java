package server.storage;

import server.repository.LocalWorkerRepository;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ShutdownHookRegistrer {

    private static final Logger logger = Logger.getLogger(ShutdownHookRegistrer.class.getName());

    private ShutdownHookRegistrer(){
        throw new UnsupportedOperationException("This class is for static method usage only");
    }



    public static void reisterHook(LocalWorkerRepository localWorkerRepository){
        try {
            Runtime.getRuntime().addShutdownHook(new Thread(() ->{
                logger.log(Level.WARNING, "Saving data.");
                localWorkerRepository.save();
            }));
        } catch (Exception e){
            logger.log(Level.SEVERE,"[SERVER] Critical error while trying to save during shutdown: " + e.getMessage());
        }
    }
}
