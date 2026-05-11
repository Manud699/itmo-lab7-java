package client.commands;

import client.cli.Console;
import client.cli.formatter.TableDisplayable;
import common.model.Worker;
import common.network.Result;
import common.repository.WorkerRepository;


public class RemoveHeadCommand extends AbstractCommand implements TableDisplayable {


    private final Console console;
    private final WorkerRepository workerRepository;


    public RemoveHeadCommand(WorkerRepository workerRepository, Console console) {
        super("remove_head", "Prints and removes the first element of the collection");
        this.workerRepository = workerRepository;
        this.console = console;
    }



    @Override
    public int execute(String argm) {
        if(!validateNoArgument(argm, console)) {
            return 1;
        }
        Result<Worker> result = workerRepository.removeHead();

        if (!result.isSuccess()) {
            console.printError(result.getErrorMessage());
            return 2;
        }

        console.println("Successfully removed the first worker from the collection:");
        printWorkerTable(result.getValue(), console);
        return 0;
    }


}
