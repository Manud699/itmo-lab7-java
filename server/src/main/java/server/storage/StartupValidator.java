package server.storage;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class: StartupValidator
 * Description: Validates the startup arguments for the application and ensures safe file paths.
 */
public class StartupValidator {

    private final static Logger logger = Logger.getLogger(StartupValidator.class.getName());
    private static final String DEFAULT_FILE = "filesCsv/workers_by_default.csv";



    public static String getValidFileName(String[] args) {
        if (args == null || args.length == 0 || args[0].trim().isEmpty()) {
            logger.log(Level.WARNING,"Warning: No file specified in startup arguments.");
            logger.log(Level.WARNING," Falling back to default database: '" + DEFAULT_FILE + "'");
            return DEFAULT_FILE;
        }

        String fileName = args[0].trim();

        if (!fileName.toLowerCase().endsWith(".csv")) {
            logger.log(Level.WARNING,"Warning: The specified file '" + fileName + "' does not end with '.csv'.");
            logger.log(Level.WARNING,"The program will attempt to use it, but errors may occur if the format is incompatible.");
        }

        logger.log(Level.INFO,"Loading database from: '" + fileName + "'");
        return fileName;
    }
}