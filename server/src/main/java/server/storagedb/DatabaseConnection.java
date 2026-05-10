package server.storagedb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnection {

    private static final Logger logger = Logger.getLogger(DatabaseConnection.class.getName());

    private static volatile DatabaseConnection instance;
    private static volatile Connection connection;
    private final DatabaseCredentials credentials;



    private DatabaseConnection(DatabaseCredentials credentials) {
            this.credentials = credentials;
    }



    public static void init(DatabaseCredentials credentials){
        if(instance == null){
            synchronized (DatabaseConnection.class){
                if(instance == null){
                    instance = new DatabaseConnection(credentials);
                }
            }
        }
    }



    public static DatabaseConnection getInstance() {
        if(instance == null) {
          logger.log(Level.SEVERE,"No se pudo iniciar. Faltan las credeciales de la base de datos. ");
          System.exit(1);
        }
        return instance;
    }



    public Connection getConnection() throws SQLException {
        if(connection == null || connection.isClosed()) {
            synchronized (this) {
                if(connection == null || connection.isClosed()) {
                    connection = DriverManager.getConnection(credentials.URL(), credentials.user(), credentials.password());
                    return connection;
                }
            }
        }
        logger.log(Level.INFO, "Conexion establecida con la base de datos:" + connection.getCatalog() );
        return connection;
    }
}
