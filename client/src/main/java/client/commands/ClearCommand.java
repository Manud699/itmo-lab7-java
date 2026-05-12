package client.commands;

import client.cli.Console;
import common.network.Result;
import common.repository.WorkerRepository;


public class ClearCommand extends AbstractCommand {

    private final WorkerRepository workerRepository;
    private final Console console;



    public ClearCommand(WorkerRepository workerRepository, Console console) {
        super("clear", "Clears all elements from the collection");
        this.workerRepository = workerRepository;
        this.console = console;
    }



    @Override
    public int execute(String argms) {
        if(!validateNoArgument(argms, console)) {
            return 1;
        }
        Result<Void> workersDeleted = workerRepository.clear();
        if(!workersDeleted.isSuccess()){
            console.printError(workersDeleted.getErrorMessage());
            return 2;
        }
        console.println("Database cleared successfully.");
        return 0;
    }
}
