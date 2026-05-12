package server.repository;

import common.network.Response;
import common.network.Result;
import common.network.User;
import common.security.HashSecurity;
import common.security.SaltSecurity;
import server.lifecycle.SqlAction;
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

    public UserRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public Response register(User user) {
        if (isUserRegistered(user.name()).getValue()) {
            return new Response(Result.failure("Username already exists. Please choose another one."));
        }

        var result = executeWithConnection(
                connection -> {
                    String registerSql = "INSERT INTO users (name, password_hash, salt) VALUES (?, ?, ?)";

                    try (PreparedStatement ps = connection.prepareStatement(registerSql)) {
                        String rawPassword = user.password();
                        String salt = SaltSecurity.getSalt();
                        String hashedPassword = HashSecurity.getHash(rawPassword + salt);

                        ps.setString(1, user.name());
                        ps.setString(2, hashedPassword);
                        ps.setString(3, salt);

                        int rowsAffected = ps.executeUpdate();

                        if (rowsAffected > 0) {
                            return Result.success(true);
                        }
                        return Result.failure("Unknown error occurred while creating the account.");
                    }
                });
        return new Response(result);
    }


    public Response login(User user) {
        if (!isUserRegistered(user.name()).getValue()) {
            return new Response(Result.failure("User does not exist: " + user.name()));
        }

        var result = executeWithConnection(
                connection -> {
                    String selectUserSql = "SELECT password_hash, salt FROM users WHERE name = ?";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(selectUserSql)) {
                        preparedStatement.setString(1, user.name());
                        try (ResultSet resultSet = preparedStatement.executeQuery()) {
                            if (resultSet.next()) {
                                String storedHash = resultSet.getString("password_hash");
                                String storedSalt = resultSet.getString("salt");
                                String rawPassword = user.password();

                                String calculatedHash = HashSecurity.getHash(rawPassword + storedSalt);

                                if (!calculatedHash.equals(storedHash)) {
                                    return Result.failure("Incorrect password.");
                                }
                                return Result.success();
                            }
                            return Result.failure("User not found.");
                        }
                    }
                });
        return new Response(result);
    }


    public Result<Boolean> isUserRegistered(String username) {
        return executeWithConnection(
                connection -> {
                    String checkUserSql = "SELECT EXISTS (SELECT 1 FROM users WHERE name = ?)";
                    try (PreparedStatement ps = connection.prepareStatement(checkUserSql)) {
                        ps.setString(1, username);
                        try (ResultSet resultSet = ps.executeQuery()) {
                            if (resultSet.next()) {
                                return Result.success(resultSet.getBoolean(1));
                            }
                        }
                    }
                    return Result.failure("An error occurred. Please try again.");
                });
    }


    private <T> Result<T> executeWithConnection(SqlAction<T> action) {
        Connection connection = null;
        try {
            connection = databaseConnection.getConnection();
            return action.execute(connection);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.log(Level.SEVERE, "Thread interrupted while waiting for DB connection", e);
            return Result.failure("Server is busy or shutting down. Please try again later.");

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQL Error", e);
            return Result.failure("Database task failed. Please try again later.");

        } finally {
            if (connection != null) {
                databaseConnection.giveBackTheConnection(connection);
            }
        }
    }
}