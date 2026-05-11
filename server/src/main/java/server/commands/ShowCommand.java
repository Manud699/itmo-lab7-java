package server.commands;


import common.model.Worker;
import common.network.Request;
import common.network.Response;
import common.network.Result;
import common.repository.WorkerRepository;
import server.repository.LocalWorkerRepository;
import java.util.List;



public class ShowCommand extends AbstractCommand  {

    private final WorkerRepository workerRepository;


    public ShowCommand(WorkerRepository workerRepository) {
        super("show", "Displays all elements of the collection");
        this.workerRepository = workerRepository;
    } 



    @Override
    public Response execute(Request request) {
        Response error = validateNoArgument(request);
        if(error != null){
            return error;
        }
        Result<List<Worker>> result = workerRepository.getAllWorkers();
        return new Response(result);
    }

}
