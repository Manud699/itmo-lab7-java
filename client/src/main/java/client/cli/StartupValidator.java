package client.cli;

/**
 * Class: StartupValidator
 * Description: Validates the startup arguments for the application and ensures safe file paths.
 */
public class StartupValidator {

    private static final String DEFAULT_FILE = "workerDataApp/workers_by_default.csv";

    /**
     * Validates the startup arguments and returns a valid file name for loading the database.
     * @param args the startup arguments
     * @param console the console for printing messages
     */
    public static String getValidFileName(String[] args, Console console) {
        if (args == null || args.length == 0 || args[0].trim().isEmpty()) {
            console.println(" Warning: No file specified in startup arguments.");
            console.println(" Falling back to default database: '" + DEFAULT_FILE + "'");
            return DEFAULT_FILE;
        }
        if (args.length > 1) {
            console.println("Warning: Multiple arguments detected. Only the first one ('" + args[0] + "') will be used.");
        }
        String fileName = args[0].trim();

        if (!fileName.toLowerCase().endsWith(".csv")) {
            console.printError("Warning: The specified file '" + fileName + "' does not end with '.csv'.");
            console.println("The program will attempt to use it, but errors may occur if the format is incompatible.");
        }

        console.println("Loading database from: '" + fileName + "'");
        return fileName;
    }
}