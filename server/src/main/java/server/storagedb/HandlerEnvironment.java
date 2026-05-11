package server.storagedb;

import java.util.logging.Level;
import java.util.logging.Logger;

public class HandlerEnvironment {

    private static final Logger logger = Logger.getLogger(HandlerEnvironment.class.getName());

    public static DatabaseCredentials getDatabaseCredentials() {
        final String url = System.getenv("url");
        final String user = System.getenv("user");
        final String password = System.getenv("password");

        if (url == null || url.isEmpty()) {
            logger.log(Level.SEVERE, "Database URL environment variable (DB_URL) is not set or empty.");
            System.exit(1);
        }
        if (user == null || user.isEmpty()) {
            logger.log(Level.SEVERE, "Database username environment variable (DB_USER) is not set or empty.");
            System.exit(1);
        }
        if (password == null || password.isEmpty()) {
            logger.log(Level.SEVERE, "Database password environment variable (DB_PASSWORD) is not set or empty.");
            System.exit(1);
        }

        return new DatabaseCredentials(url, user, password);
    }
}