package server.repository;

import common.model.*;
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
        String requestSql = "SELECT * FROM worker W JOIN users U ON W.id_user = U.id";
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
                String creator = resultSet.getString("id_user");

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
            if(localWorkerRepository.isCollectionEmpty())
                load();
            return localWorkerRepository.getAllWorkers();
        }




    @Override
    public Result<Integer> clear() {
        String nameUser = UserContext.get().name();
        String clearSQL = "DELETE FROM worker WHERE id_user = (SELECT id FROM users WHERE name = ?)";
        try(Connection connection = databaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(clearSQL)){

            preparedStatement.setString(1, nameUser);
            int rowsAfe = preparedStatement.executeUpdate();
            return Result.success(rowsAfe);

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "No fue posible ejecutar la consulta CLEAR. " + e.getMessage());
            return Result.failure(e.getMessage());
        }

    }

    @Override
    public Result<Boolean> existById(long id) {
        String nameUser = UserContext.get().name();
        String requesSql = "SELECT EXISTS(SELECT 1 FROM worker WHERE id = ?)";
        try(Connection connection = databaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(requesSql)){

            preparedStatement.setString(1, nameUser);
            ResultSet resultSet = preparedStatement.executeQuery();
            return Result.success(resultSet.next());

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "No fue posible ejecutar la consulta CLEAR. " + e.getMessage());
            return Result.failure(e.getMessage());
        }
    }

    @Override
    public Result<Void> updateWorkerById(Worker workerUpdated) {
        String nameUser = UserContext.get().name();
        String requestSql = "UPDATE  worker " +
                            "SET name = ?," +
                            "coordinate_x = ?," +
                            "coordinate_y = ?," +
                            "salary = ?," +
                            "position = ?," +
                            "status = ?," +
                            "org_full_name = ?," +
                            "org_annual_turnover = ?," +
                            "org_employees = ?"+
                             " FROM worker WHERE id_user = (SELECT id FROM users WHERE name = ?)";

        //name, coordinate_x, coordinate_y, salary, position, status, org_full_name, org_annual_turnover, org_employees

        try(Connection connection = databaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(requestSql)){
            preparedStatement.setString(1, nameUser);
            preparedStatement.executeUpdate();
            return Result.success();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "No fue posible ejecutar la consulta CLEAR. " + e.getMessage());
            return Result.failure(e.getMessage());
        }
    }




//
//    @Override
//    public Result<Boolean> removeById(long id) {
//        return null;
//    }
//
//    @Override
//    public Result<Worker> getHead() {
//        return null;
//    }
//
//    @Override
//    public Result<Integer> removeAllByPosition(Position position) {
//        return null;
//    }
//
//    @Override
//    public Result<Long> sumOfSalary() {
//        return null;
//    }
//
//    @Override
//    public Result<List<Long>> getDescendingSalaries() {
//        return null;
//    }
//
//    @Override
//    public Result<Worker> removeHead() {
//        return null;
//    }
//
//    @Override
//    public Result<String> getInfo() {
//        return null;
//    }
}
