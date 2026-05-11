package client.commands;

import client.cli.Console;
import common.network.Result;
import common.repository.WorkerRepository;


public class InfoCommand extends AbstractCommand {

    private final Console console;
    private final WorkerRepository workerRepository;

    public InfoCommand(WorkerRepository workerRepository, Console console){
        super("info", "Displays information about the collection");
        this.workerRepository = workerRepository;
        this.console = console;
    }


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
