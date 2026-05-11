package server.commands;

import common.model.Worker;
import common.network.Request;
import common.network.Response;
import common.network.Result;
import common.repository.WorkerRepository;


public class HeadCommand extends AbstractCommand {

    private final WorkerRepository workerRepository;

    public HeadCommand(WorkerRepository workerRepository){
        super("head", "Return head of collection");
        this.workerRepository = workerRepository;
    }



    public Response execute(Request request){
        var error = validateNoArgument(request);
        if(error != null){
            return error;
        }
        Result<Worker> result = workerRepository.getHead();
        return new Response(result);
    }
}
