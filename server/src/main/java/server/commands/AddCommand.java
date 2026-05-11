package server.commands;

import common.network.Request;
import common.network.Response;
import common.network.Result;
import common.repository.WorkerRepository;


import java.time.ZonedDateTime;

public final class AddCommand extends AbstractCommand {

    private final WorkerRepository localWorkerRepository;

    public AddCommand(WorkerRepository localWorkerRepository) {
        super("add", "Adds a new worker to the collection");
        this.localWorkerRepository = localWorkerRepository;
    }

    @Override
    public Response execute(Request request) {
        Response errorResponse = validateNoArgument(request);
        if(errorResponse != null) {
            return errorResponse;
        }
        if(request.getWorker().isEmpty()) {
            return new Response(Result.failure("Protocol error: No worker received"));
        }
        var userName = request.getUser().name();
        var worker = request.getWorker().get();
        worker.setCreationDate(ZonedDateTime.now());
        worker.setCreatorName(userName);
        Result<Boolean> addResult = localWorkerRepository.add(worker);
        return new Response(addResult);
    }
}