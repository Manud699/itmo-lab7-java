package server.commands;

import common.model.Worker;
import common.network.Request;
import common.network.Response;
import common.network.Result;
import common.repository.WorkerRepository;

public class RemoveHeadCommand extends AbstractCommand {

    private final WorkerRepository workerRepository;

    public RemoveHeadCommand(WorkerRepository workerRepository) {
        super("remove_head", "Prints and removes the first element of the collection");
        this.workerRepository = workerRepository;
    }

    @Override
    public Response execute(Request request){
        var error = validateNoArgument(request);
        if(error != null){
            return error;
        }

        Result<Worker> result = workerRepository.removeHead();
        return new Response(result);
    }

}
