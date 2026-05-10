package server.commands;


import common.model.Worker;
import common.network.Request;
import common.network.Response;
import common.network.Result;
import common.repository.WorkerRepository;
import server.repository.LocalWorkerRepository;
import java.util.List;


/**
 * Command: ShowCommand
 * Command description: Displays all elements of the collection.
 */
public class ShowCommand extends AbstractCommand  {

    private final WorkerRepository localWorkerRepository;

    /**
     * Constructor for the ShowCommand class.
     * @param localWorkerRepository the repository for managing workers
     */
    public ShowCommand(WorkerRepository localWorkerRepository) {
        super("show", "Displays all elements of the collection");
        this.localWorkerRepository = localWorkerRepository;
    } 


    /**     
     *  Executes the show command.
     */
    @Override
    public Response execute(Request request) {
        Response error = validateNoArgument(request);
        if(error != null){
            return error;
        }
        Result<List<Worker>> result = localWorkerRepository.getAllWorkers();
        return new Response(result);
    }

}
