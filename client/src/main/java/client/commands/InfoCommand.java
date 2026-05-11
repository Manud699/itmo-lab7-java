package client.commands;

import java.time.format.DateTimeFormatter;
import client.cli.Console;
import client.repository.ProxyWorkerRepository;
import common.network.Result;
import common.repository.WorkerRepository;


/**
 * Command: InfoCommand
 * Command description: Displays information about the collection.
 */
public class InfoCommand extends AbstractCommand {

    private final Console console;
    private final WorkerRepository workerRepository;

    /**
     * Constructor for the InfoCommand class.
     * @param workerRepository the repository for managing workers
     * @param console the console for input/output operations
     */
    public InfoCommand(WorkerRepository workerRepository, Console console){
        super("info", "Displays information about the collection");
        this.workerRepository = workerRepository;
        this.console = console;
    }



    /**
     * Executes the info command.
     *
     * @param argument the command argument
     * @return 0 if successful, another value if validation fails
     */
    @Override
    public int execute(String argument){
        if(!validateNoArgument(argument, console)) {
            return 1;
        }

        Result<String> result = workerRepository.getInfo();

        if(!result.isSuccess()){
            console.printError(result.getErrorMessage());
            return 2;
        }

        String info = result.getValue();
        console.println("----------informacion de la Coleccion----------");
        console.println(info);
        console.println("-----------------------------------------------");

        return 0;
    }


}
