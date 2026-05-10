package common.util;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class FindFile
 * This class provides a utility method to find a file, checking both the given path and a fallback path one level up.
 */
public class FindFile {

    private static final Logger  logger = Logger.getLogger(FindFile.class.getName());


    public static File find(File fileToCheck) {
        if (!fileToCheck.exists()) {
            logger.log(Level.WARNING,"Archivo no encontrado en la ruta:"+ fileToCheck.getAbsolutePath());
            File fallbackFile = new File("../" + fileToCheck.getPath());
            logger.log(Level.WARNING,"Intentando encontrar el archivo en la ruta:"+ fallbackFile.getAbsolutePath());
            if (fallbackFile.exists()) {
                logger.log(Level.INFO,"Archivo encontrado en:" + fileToCheck.getAbsolutePath());
                return fallbackFile; 
            } 
        }
        logger.log(Level.INFO,"Archivo encontrado en la ruta:"+ fileToCheck.getAbsolutePath());
        return fileToCheck;
    }
}