package server.repository;

import common.model.*;
import common.network.Response;
import common.network.Result;
import common.repository.WorkerRepository;
import server.multithread.UserContext;
import server.storagedb.DatabaseConnection;
import java.sql.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DataBaseWorker implements WorkerRepository {

    private static final Logger logger = Logger.getLogger(DataBaseWorker.class.getName());


    private final DatabaseConnection databaseConnection;
    private final LocalWorkerRepository localWorkerRepository;

    public DataBaseWorker(DatabaseConnection databaseConnection, LocalWorkerRepository localWorkerRepository){
        this.databaseConnection = databaseConnection;
        this.localWorkerRepository = localWorkerRepository;
    }


//JOOQ
    @Override
    public Result<Boolean> add(Worker worker) {
        String sql = "INSERT INTO worker (name, coordinate_x, coordinate_y, creation_date, salary, position, status, " +
            "org_full_name, org_annual_turnover, org_employees_count, id_user) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, (SELECT id FROM users WHERE name = ?)) RETURNING id";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, worker.getName());
            preparedStatement.setFloat(2, worker.getCoordinates().getX());
            preparedStatement.setDouble(3, worker.getCoordinates().getY());
            preparedStatement.setTimestamp(4, Timestamp.from(worker.getCreationDate().toInstant()));
            preparedStatement.setLong(5, worker.getSalary());
            preparedStatement.setString(6, worker.getPosition().name());
            preparedStatement.setString(7, worker.getStatus().name());
            preparedStatement.setString(8, worker.getOrganization().getFullName());
            preparedStatement.setFloat(9, worker.getOrganization().getAnnualTurnover());
            preparedStatement.setInt(10, worker.getOrganization().getEmployeesCount());
            preparedStatement.setString(11, worker.getCreatorName());

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                worker.setId(resultSet.getLong(1));
                logger.log(Level.INFO, "Worker agregado correctamente con ID: " + worker.getId());
                localWorkerRepository.add(worker);
                return Result.success(true);
            }
        }
        return Result.failure("No se genero un ID en la base de datos.");
    } catch (SQLException e) {
        logger.log(Level.SEVERE, "Error de base de datos al intentar agregar el worker", e);
        return Result.failure("Error de SQL: " + e.getMessage());
    }
}



public void load(){
        String requestSql = "SELECT U.name AS name_user ,W.* FROM worker W JOIN users U ON W.id_user = U.id";
        try (Connection connection =  databaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(requestSql);
             ResultSet resultSet = preparedStatement.executeQuery()){


            while (resultSet.next()){
                Worker worker = new Worker();
                Coordinates cooordinates = new Coordinates(
                        resultSet.getFloat("coordinate_x"),
                        resultSet.getDouble("coordinate_y")
                );

                Organization organization = new Organization(
                        resultSet.getString("org_full_name"),
                        resultSet.getFloat("org_annual_turnover"),
                        resultSet.getInt("org_employees_count")
                );

                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");

                Timestamp timestamp = resultSet.getTimestamp("creation_date");
                ZonedDateTime zonedDateTime = timestamp.toInstant().atZone(ZoneId.systemDefault());

                long salary = resultSet.getLong("salary");
                String position = resultSet.getString("position");
                String status = resultSet.getString("status");
                String creator = resultSet.getString("name_user");

                worker.setId(id);
                worker.setName(name);
                worker.setCoordinates(cooordinates);
                worker.setCreationDate(zonedDateTime);
                worker.setSalary(salary);
                worker.setPosition(Position.valueOf(position.toUpperCase()));
                worker.setStatus(Status.valueOf(status.toUpperCase()));
                worker.setOrganization(organization);
                worker.setCreatorName(creator);
                localWorkerRepository.add(worker);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al intentar cargar los datos desde la base de datos");
        }
    }



        @Override
    public Result<List<Worker>> getAllWorkers() {
            return localWorkerRepository.getAllWorkers();
        }




    @Override
    public Result<Void> clear() {
        String nameUser = UserContext.get().name();
        String clearSQL = "DELETE FROM worker WHERE id_user = (SELECT id FROM users WHERE name = ?)";
        try(Connection connection = databaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(clearSQL)){

            preparedStatement.setString(1, nameUser);
            int rowsDeleted = preparedStatement.executeUpdate();
            if(rowsDeleted > 0)
                localWorkerRepository.clear();
            return Result.success();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "No fue posible ejecutar la consulta CLEAR. " + e.getMessage());
            return Result.failure("No fue posible ejecutar la consulta 'clear`'");
        }
    }



    @Override
    public Result<Boolean> existById(long id) {
        String nameUser = UserContext.get().name();
        String requesSql = "SELECT EXISTS(SELECT 1 FROM worker" +
                            " WHERE id = ? AND id_user = " +
                            "(SELECT id FROM users WHERE name = ?))";
        try(Connection connection = databaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(requesSql)){

            preparedStatement.setLong(1, id);
            preparedStatement.setString(2, nameUser);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next())
                return Result.success(resultSet.getBoolean(1));

            return Result.success(false);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "No fue posible ejecutar la consulta exist_by_id. " + e.getMessage());
            return Result.failure("No fue posible ejecutar la consulta");
        }
    }



    @Override
    public Result<Void> updateWorkerById(Worker workerUpdated) {
        String nameUser = UserContext.get().name();

        String requestSql = "UPDATE worker " +
                "SET name = ?, " +
                "coordinate_x = ?, " +
                "coordinate_y = ?, " +
                "salary = ?, " +
                "position = ?, " +
                "status = ?, " +
                "org_full_name = ?, " +
                "org_annual_turnover = ?, " +
                "org_employees_count = ? " +
                "WHERE id = ? AND id_user = (SELECT id FROM users WHERE name = ?)";

        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(requestSql)) {

            preparedStatement.setString(1, workerUpdated.getName());
            preparedStatement.setFloat(2, workerUpdated.getCoordinates().getX());
            preparedStatement.setDouble(3, workerUpdated.getCoordinates().getY());
            preparedStatement.setLong(4, workerUpdated.getSalary());
            preparedStatement.setString(5, workerUpdated.getPosition().toString());
            preparedStatement.setString(6, workerUpdated.getStatus().toString());
            preparedStatement.setString(7, workerUpdated.getOrganization().getFullName());
            preparedStatement.setFloat(8, workerUpdated.getOrganization().getAnnualTurnover());
            preparedStatement.setInt(9, workerUpdated.getOrganization().getEmployeesCount());

            preparedStatement.setLong(10, workerUpdated.getId());
            preparedStatement.setString(11, nameUser);

            int rowUpdated = preparedStatement.executeUpdate();

            if (rowUpdated > 0) {
                localWorkerRepository.updateWorkerById(workerUpdated);
                return Result.success();
            } else {
                return Result.failure("No se pudo actualizar. El trabajador no existe o no te pertenece.");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "No fue posible ejecutar la consulta update_by_id. " + e.getMessage());
            return Result.failure("No fue posible ejecutar la consulta update_by_id.");
        }
    }




    @Override
    public synchronized Result<Boolean> removeById(long id) {
        String nameUser = UserContext.get().name();
        String requestSql = "DELETE FROM worker WHERE id = ? AND id_user = (SELECT id FROM users WHERE  name = ?)";

        try(Connection connection = databaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(requestSql)){
            preparedStatement.setLong(1, id);
            preparedStatement.setString(2, nameUser);
            int rowDeleted = preparedStatement.executeUpdate();
            if(rowDeleted > 0){
                localWorkerRepository.removeById(id);
                return Result.success(true);
            }
            return Result.success(false);
        } catch (SQLException e) {
            logger.log(Level.SEVERE,e.getMessage());
            return Result.failure("No fue posible ejecutar la consulta 'remove_by_id'.");
        }
    }



    @Override
    public synchronized Result<Worker> getHead() {
        return localWorkerRepository.getHead();
    }



    @Override
    public Result<Integer> removeAllByPosition(Position position) {
        String nameUser = UserContext.get().name();
        String requestSql = "DELETE FROM worker WHERE position = ? AND id_user = (SELECT id FROM users WHERE name = ?)";
        try(Connection connection = databaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(requestSql)){
            preparedStatement.setString(1, position.name());
            preparedStatement.setString(2, nameUser);
            int rowsDeleted = preparedStatement.executeUpdate();
            if(rowsDeleted > 0)
                localWorkerRepository.removeAllByPosition(position);
            return Result.success(rowsDeleted);
        } catch (SQLException e) {
            logger.log(Level.SEVERE,e.getMessage());
            return Result.failure("No se pudo llevar a cabo la tarea 'remove_all_by_position'");
        }
    }



    @Override
    public Result<Long> sumOfSalary() {
        return localWorkerRepository.sumOfSalary();
    }



    @Override
    public Result<List<Long>> getDescendingSalaries() {
        return localWorkerRepository.getDescendingSalaries();
    }



    @Override
    public Result<Worker> removeHead() {
        String nameUser = UserContext.get().name();
        String requestSql = "DELETE FROM worker" +
                "WHERE id_user = " +
                "(SELECT id FROM user WHERE name = ?) " +
                "ORDER BY name ASC LIMIT 1 " +
                "RETUTNING worker";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(requestSql)) {
            preparedStatement.setString(1, nameUser);
            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0)
                return localWorkerRepository.removeHead();
            return Result.failure("No tienes workers registrados");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "No fue posible ejecutar la consulta 'remove_by_position'. " + e.getMessage());
            return Result.failure(e.getMessage());
        }
    }



    @Override
    public Result<String> getInfo() {
        return localWorkerRepository.getInfo();
    }


}
