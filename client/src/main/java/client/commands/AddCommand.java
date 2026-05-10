package client.commands;

import client.cli.Console;
import client.builders.WorkerMainBuilder;
import common.network.Result;
import common.repository.WorkerRepository;



public class AddCommand extends AbstractCommand {

    private final WorkerRepository workerRepository;
    private final Console console; 
    private final WorkerMainBuilder formWorker; 



    public AddCommand(WorkerRepository workerRepository, Console console, WorkerMainBuilder formWorker) {
        super("add", "Adds a new worker to the collection");
        this.workerRepository = workerRepository;
        this.console = console; 
        this.formWorker = formWorker;   
    } 



    @Override
    public int execute(String argms) {
        if(!validateNoArgument(argms, console)) {
            return 1; 
        }
        Result<Boolean> result = workerRepository.add(formWorker.build());
        if(!result.isSuccess()){
            console.printError(result.getErrorMessage());
            return 2; 
        }
        console.println("Worker successfully added to the collection.");
        return 0; 
    }
}
