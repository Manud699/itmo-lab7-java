package client.filehelper;
import client.cli.Console;
import java.io.File;



public class FindFile {


    public static File find(File fileToCheck) {
        if (!fileToCheck.exists()) {
            File fallbackFile = new File("../" + fileToCheck.getPath());
            if (fallbackFile.exists()) {
                return fallbackFile;
            }
        }
        return fileToCheck;
    }
}