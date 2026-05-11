package client.commands;

import client.repository.ProxyWorkerRepository;
import client.cli.Console;
import common.model.Position;
import client.repository.ProxyWorkerRepository;
import common.network.Result;
import common.repository.WorkerRepository;


/**
 * Command: remove_all_by_position
 * Description: Removes all elements from the collection whose position field is equivalent to the specified one.
 */
public class RemoveAllByPosition extends AbstractCommand  {

    private final WorkerRepository workerRepository;
    private final Console console;


    /**
     * Constructor for the RemoveAllByPosition class.
     * @param workerRepository the repository for managing workers
     * @param console the console for input/output operations
     */
    public RemoveAllByPosition(WorkerRepository workerRepository, Console console) {
        super("remove_all_by_position", "Removes all elements from the collection whose position field is equivalent to the specified one");
        this.workerRepository = workerRepository;
        this.console = console;
    }


    /**
     * Executes the remove_all_by_position command.
     *
     * @param argm the command arguments
     * @return 0 if successful, another value if validation fails
     */
    @Override
    public int execute(String argm){
        if(!validateHasArgument(argm, console)){
            return 1;
        }

        Position position = convertToStatusEnum(argm);
        if(position == null){
            console.printError("Error: '" + argm + "' is not a valid position.");
            return 2;
        }

        Result<Integer> result = workerRepository.removeAllByPosition(position);
        if (!result.isSuccess()){
            console.printError(result.getErrorMessage());
            return 3;
        }

        if(result.getValue() == null || result.getValue() == 0){
            console.println("No se encontraron workers en la posicion:" + position);
            return  0;
        }

        console.println("Successfully removed all workers with position: " + position.name());
        console.println("total:"+ result.getValue());
        return 0;
    }



    /**
     * Converts a string to a Position enum value.
     *
     * @param argm the string to convert
     * @return the corresponding Position enum value, or null if invalid
     */
    public Position convertToStatusEnum(String argm) {
        try {
            return Position.valueOf(argm.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
