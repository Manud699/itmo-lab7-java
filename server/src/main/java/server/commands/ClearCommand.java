package server.commands;

import common.network.*;
import common.repository.WorkerRepository;




/**
 * Command: ClearCommand
 * Command description: Clears all elements from the collection.
 */
public class ClearCommand extends AbstractCommand {

    private final WorkerRepository workerRepository;

    public ClearCommand(WorkerRepository workerRepository) {
        super("clear", "Clears all elements from the collection");
        this.workerRepository = workerRepository;
    }

    /**
     * Executes the clear command.
     */
    @Override
    public Response execute(Request request) {
        Response response = validateNoArgument(request);
        if(response != null) {
            return response;
        }
        return new Response(workerRepository.clear());
    }

}
