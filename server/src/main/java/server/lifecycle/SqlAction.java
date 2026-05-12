package server.lifecycle;

import common.network.Result;

import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface SqlAction<T> {
    Result<T> execute(Connection connection) throws SQLException;
}
