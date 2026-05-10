package server.storage;
import java.io.File;
import  java.util.logging.Logger;
import  java.util.logging.Level;




public class FileValidator {

    static Logger logger = Logger.getLogger(FileValidator.class.getName());

    public static boolean isValidForRead(File file) {
        if (file.isDirectory()) {
            logger.log(Level.SEVERE ,"Error: Path is a directory: '" + file.getAbsolutePath() + "'");
            return false;
        }
        if (!file.exists()) {
            logger.log(Level.WARNING,"Notice: Database file does not exist yet. Starting with an empty collection.");
            return false;
        }
        if (!file.canRead()) {
            logger.log(Level.SEVERE,"Error: No read permissions for: '" + file.getAbsolutePath() + "'");
            return false;
        }
        return true;
    }


    public static boolean isValidForWrite(File file) {
        if (file.isDirectory()) {
            logger.log(Level.SEVERE,"Error: Path is a directory. Cannot save.");
            return false;
        }
        if (file.exists() && !file.canWrite()) {
        logger.log(Level.SEVERE,"Error: No write permissions for: '" + file.getAbsolutePath() + "'. Cannot save.");
        return false;
    }
        return true;
    }
}