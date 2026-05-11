package server.storagedb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnection {

    private static final Logger logger = Logger.getLogger(DatabaseConnection.class.getName());

    private static volatile DatabaseConnection instance;
    private final DatabaseCredentials credentials;


    private DatabaseConnection(DatabaseCredentials credentials) {
        this.credentials = credentials;
    }


    public static void init(DatabaseCredentials credentials) {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection(credentials);
                }
            }
        }
    }


    public static DatabaseConnection getInstance() {
        if (instance == null) {
            logger.log(Level.SEVERE, "Database connection not initialized. Missing database credentials.");
            System.exit(1);
        }
        return instance;
    }


    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                credentials.url(),
                credentials.user(),
                credentials.password());
    }
}