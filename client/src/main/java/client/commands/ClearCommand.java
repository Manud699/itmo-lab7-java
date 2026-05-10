package client.commands;

import client.cli.Console;
import common.repository.WorkerRepository;


/**
 * Command: ClearCommand
 * Command description: Clears all elements from the collection.
 */
public class ClearCommand extends AbstractCommand {

    private final WorkerRepository workerRepository;
    private final Console console;

    /**
     * Constructor for the ClearCommand class.
     * @param console the console for input/output operations
     */
    public ClearCommand(WorkerRepository workerRepository, Console console) {
        super("clear", "Clears all elements from the collection");
        this.workerRepository = workerRepository;
        this.console = console;
    }



    /**
     * Executes the clear command.
     *
     * @param argms the command argument
     * @return 0 if successful, another value if validation fails
     */
    @Override
    public int execute(String argms) {
        if(!validateNoArgument(argms, console)) {
            return 1;
        }
        console.println("Collection cleared successfully");
        return 0;
    }

}
