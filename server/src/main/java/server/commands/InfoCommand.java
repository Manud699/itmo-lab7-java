package server.commands;

import common.network.Request;
import common.network.Response;
import common.network.Result;
import common.repository.WorkerRepository;
import server.repository.LocalWorkerRepository;

public class InfoCommand extends  AbstractCommand{

    private final WorkerRepository localWorkerRepository;

    public InfoCommand(WorkerRepository localWorkerRepository){
        super("info", "Displays information about the collection");
        this.localWorkerRepository = localWorkerRepository;
    }

    @Override
    public Response execute(Request request){
        var error = validateNoArgument(request);
        if(error != null){
            return error;
        }

        Result<String> result = localWorkerRepository.getInfo();
        return new Response(result);
    }
}
