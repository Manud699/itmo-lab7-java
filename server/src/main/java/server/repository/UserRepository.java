package server.repository;

import common.network.Response;
import common.network.Result;
import common.network.User;
import common.security.HashSecurity;
import common.security.SaltSecurity;
import server.storagedb.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserRepository {

    private static final Logger logger = Logger.getLogger(UserRepository.class.getName());
    private final DatabaseConnection databaseConnection;



    public UserRepository(DatabaseConnection databaseConnection){
        this.databaseConnection = databaseConnection;
    }



    public Response registrate(User user) {

        if(isUserRegistrate(user.name()))
            return new Response(Result.failure("Nombre de usuario existente. Elige otro por favor."));

        String registrateSQL = "INSERT INTO users (name, password_hash, salt) VALUES (?, ?, ?)";

        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(registrateSQL)) {

            String passwordPreHash = user.password();
            String salt = SaltSecurity.getSalt();
            String passwordSalt = HashSecurity.getHash(passwordPreHash + salt);

            ps.setString(1, user.name());
            ps.setString(2, passwordSalt);
            ps.setString(3, salt);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0)
                return new Response(Result.success(true));

            return new Response(Result.failure("Fallo desconocido al insertar en la base de datos"));
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error SQL al intentar registrar al usuario", e);
            return new Response(Result.failure("Error interno del servidor: " + e.getMessage()));
        }
    }



    public Response logging(User user){
        if(!isUserRegistrate(user.name()))
            return new Response(Result.failure("El usuario no existe "+ user.name()));

        String selectUser = "SELECT password_hash, salt FROM users WHERE name = ?";
        try(Connection connection = databaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(selectUser)) {

            preparedStatement.setString(1, user.name());

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if(resultSet.next()){

                    String password_hash = resultSet.getString("password_hash");
                    String salt = resultSet.getString("salt");

                    String prePassword = user.password();
                    String temporalPassword = HashSecurity.getHash(prePassword + salt);

                    if(!temporalPassword.equals(password_hash))
                        return new Response(Result.failure("Password incorrecta"));

                    return new Response(Result.success());
                }
                return new Response(Result.failure("Nombre de usuario incorrecto"));
            }
        } catch (SQLException e){
            logger.log(Level.SEVERE, e.getMessage());
            return new Response(Result.failure("Lo sentimos, ha ocurrido un error. Intentanlo de nuevo por favor"));
        }
    }



    public boolean isUserRegistrate(String nameUser) {
        String isUserSQL = "SELECT EXISTS (SELECT 1 FROM users WHERE name = ?)";

        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(isUserSQL)) {

            ps.setString(1, nameUser);

            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getBoolean(1);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al verificar la existencia del usuario", e);
        }
        return false;
    }



}