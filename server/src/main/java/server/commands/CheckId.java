package server.commands;

import common.network.Request;
import common.network.Response;
import common.network.Result;
import common.repository.WorkerRepository;
import server.repository.LocalWorkerRepository;


public class CheckId extends AbstractCommand {

    private final WorkerRepository localWorkerRepository;
    public CheckId(WorkerRepository localWorkerRepository){
        super("check_id", "checks if the id exists");
        this.localWorkerRepository = localWorkerRepository;
    }

    @Override
    public Response execute(Request request){
        Response error = validateHasArgument(request);
        if(error != null){
            return  error;
        }
        long workerId = Long.parseLong(request.getCommandArgument());
        Result<Boolean> result = localWorkerRepository.existById(workerId);
        return new Response(result);
    }

}
