package client.commands;

import client.cli.Console;
import common.model.Position;
import common.network.Result;
import common.repository.WorkerRepository;


public class RemoveAllByPosition extends AbstractCommand {

    private final WorkerRepository workerRepository;
    private final Console console;


    public RemoveAllByPosition(WorkerRepository workerRepository, Console console) {
        super("remove_all_by_position", "Removes all elements from the collection whose position field is equivalent to the specified one");
        this.workerRepository = workerRepository;
        this.console = console;
    }


    @Override
    public int execute(String argm) {
        if (!validateHasArgument(argm, console)) {
            return 1;
        }

        Position position = convertToEnum(argm);
        if (position == null) {
            console.printError("Error: '" + argm + "' is not a valid position.");
            return 2;
        }

        Result<Integer> result = workerRepository.removeAllByPosition(position);
        if (!result.isSuccess()) {
            console.printError(result.getErrorMessage());
            return 3;
        }

        if (result.getValue() == null || result.getValue() == 0) {
            console.println("No workers found with position: " + position);
            return 0;
        }

        console.println("Successfully removed all workers with position: " + position.name());
        console.println("Total: " + result.getValue());
        return 0;
    }


    public Position convertToEnum(String argm) {
        try {
            return Position.valueOf(argm.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}