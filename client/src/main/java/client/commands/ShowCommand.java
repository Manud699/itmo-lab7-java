package client.commands;

import client.cli.Console;
import client.cli.formatter.TableDisplayable;
import common.model.Worker;
import common.network.Result;
import common.repository.WorkerRepository;
import java.util.List;


public class ShowCommand extends AbstractCommand implements TableDisplayable {

    private final WorkerRepository workerRepository;
    private final Console console;

    public ShowCommand(WorkerRepository workerRepository, Console console) {
        super("show", "Displays all elements of the collection");
        this.workerRepository = workerRepository;
        this.console = console;
    }


    @Override
    public int execute(String argument) {
        if(!validateNoArgument(argument, console)){
            return 1;
        }
        Result<List<Worker>> result = workerRepository.getAllWorkers();
        if (!result.isSuccess()) {
            console.printError(result.getErrorMessage());
            return 1;
        }
        List<Worker> workers = result.getValue();
        if (workers.isEmpty()) {
            console.println("The collection is empty.");
        } else {
            printWorkerTable(workers, console);
        }
        return 0;
    }
}
