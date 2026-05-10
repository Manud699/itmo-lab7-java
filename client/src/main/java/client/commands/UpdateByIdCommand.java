package client.commands;

import java.util.Optional;
import client.cli.Console;
import client.builders.WorkerMainBuilder;
import common.network.Result;
import common.repository.WorkerRepository;
import common.util.NumberParseSafe;

/**
 * Command: UpdateByIdCommand
 * Command description: Updates a worker by ID.
 */
public class UpdateByIdCommand extends AbstractCommand {

    private final WorkerRepository workerRepository;
    private final Console console;
    private final WorkerMainBuilder workerBuilder;



    public UpdateByIdCommand(WorkerRepository workerRepository, Console console, WorkerMainBuilder workerBuilder) {
        super("update_by_id", "Updates a worker by ID");
        this.workerRepository = workerRepository;
        this.console = console;
        this.workerBuilder = workerBuilder;
    }



    @Override
    public int execute(String argms) {
        if(!validateHasArgument(argms, console)) {
            return 1;
        }
        Optional<Long> parsingId = NumberParseSafe.parse(argms, Long::parseLong);
        if(parsingId.isEmpty()) {
            console.printError("Invalid number format. Please enter a valid whole number.");
            return 2;
        }
        long workerId = parsingId.get();

        Result<Boolean> existResult = workerRepository.existById(workerId);
        if (!existResult.isSuccess()) {
            console.printError("Error del servidor: " + existResult.getErrorMessage());
            return 3;
        }

        if(!existResult.getValue()){
            console.printError("Worker ID not found in the collection '" + argms+ "'" );
            return 4;
        }

        var workerToUpdate = workerBuilder.build();
        workerToUpdate.setId(workerId);
        Result<Void> result =  workerRepository.updateWorkerById(workerToUpdate);
        if(result.isSuccess()){
            console.println("The worker was successfully updated.");
            return 0;
        }
        console.printError(result.getErrorMessage());
        return 4;
    }
}
