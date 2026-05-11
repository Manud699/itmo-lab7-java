package client.repository;

import client.ClientSession;
import client.network.NetworkClient;
import common.model.Position;
import common.network.Request;
import common.network.Response;
import common.network.Result;
import common.repository.WorkerRepository;
import common.model.Worker;
import java.util.List;

public class ProxyWorkerRepository implements WorkerRepository {

    private final NetworkClient networkClient;
    private final ClientSession clientSession;

    public ProxyWorkerRepository(NetworkClient networkClient, ClientSession clientSession){
        this.networkClient = networkClient;
        this.clientSession = clientSession;
    }

    @SuppressWarnings("unchecked")
    private <T> Result<T> executeRequest(Request request, String errorMessage) {
        try {
            Response response = networkClient.execute(request);
            return (Result<T>) response.result();
        } catch (Exception e) {
            return Result.failure(errorMessage);
        }
    }



    @Override
    public Result<Boolean> add(Worker worker) {
        return executeRequest(new Request(clientSession.getUser(), "add", worker), "Connection error while adding the worker.");
    }



    @Override
    public Result<List<Worker>> getAllWorkers() {
        return executeRequest(new Request(clientSession.getUser(),"show"), "Connection error while getting the worker list.");
    }



    @Override
    public Result<Void> clear(){
        return executeRequest(new Request(clientSession.getUser(),"clear"), "Connection error during the 'clear' command.");
    }



    @Override
    public Result<Boolean> existById(long id){
        return executeRequest(new Request(clientSession.getUser(),"check_id", String.valueOf(id)), "Error processing the 'check_id' command.");
    }



    @Override
    public Result<Void> updateWorkerById(Worker workerUpdated){
        return executeRequest(new Request(clientSession.getUser(),"update_by_id", workerUpdated), "An error occurred while updating the worker.");
    }



    @Override
    public Result<Boolean> removeById(long id){
        return executeRequest(new Request(clientSession.getUser(),"remove_by_id", String.valueOf(id)), "An error occurred while removing the worker by ID.");
    }



    @Override
    public Result<Worker> getHead(){
        return executeRequest(new Request(clientSession.getUser(),"head"), "Connection error executing the 'head' command.");
    }



    @Override
    public Result<Integer> removeAllByPosition(Position position){
        return executeRequest(new Request(clientSession.getUser(), "remove_all_by_position", position.name()), "Connection error removing workers by position.");
    }



    @Override
    public Result<Worker> removeHead(){
        return executeRequest(new Request(clientSession.getUser(),"remove_head"), "Connection error removing the head element.");
    }



    @Override
    public Result<Long> sumOfSalary(){
        return executeRequest(new Request(clientSession.getUser(),"sum_of_salary"), "Connection error calculating the sum of salaries.");
    }



    @Override
    public Result<List<Long>> getDescendingSalaries(){
        return executeRequest(new Request(clientSession.getUser(),"print_field_descending_salary"), "Connection error retrieving salaries in descending order.");
    }



    @Override
    public Result<String> getInfo(){
        return executeRequest(new Request(clientSession.getUser(),"info"), "Connection error requesting collection information.");
    }


    @Override
    public void load(){
        throw new UnsupportedOperationException("Esta operacion no es soportada en el cliente");
    }
}