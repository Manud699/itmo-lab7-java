package client.commands;
import client.cli.Console;
import client.cli.formatter.TableDisplayable;
import common.model.Worker;
import common.network.Result;
import common.repository.WorkerRepository;


public class HeadCommand extends AbstractCommand implements TableDisplayable {

    private final WorkerRepository workerRepository;
    private final Console console;


    public HeadCommand(WorkerRepository workerRepository, Console console) {
        super("head","Prints the first element of the collection");
        this.workerRepository = workerRepository;
        this.console = console;
    }


    @Override
    public int execute(String argms){
        if(!validateNoArgument(argms, console)) {
            return 1;
        }
        Result<Worker> result = workerRepository.getHead();
        if(!result.isSuccess()){
            console.println(result.getErrorMessage());
            return 2;
        }
        if(result.getValue() == null){
            console.println("The collection is empty.");
            return 3;
        }
        console.println("First worker in the collection:");
        printWorkerTable(result.getValue(), console);
        return 0;
        }

}
