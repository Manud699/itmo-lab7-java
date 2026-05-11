package client.commands;

import client.cli.Console;
import common.network.Result;
import common.repository.WorkerRepository;
import java.util.List;

/**
 * Command: print_field_descending_salary
 * Description: Prints the salary field values of all the elements in descending order.
 */
public class PrintFieldDescendingSalaryCommand extends AbstractCommand {


    private final WorkerRepository workerRepository;
    private final Console console;


    /**
     * Constructor for the PrintFieldDescendingSalaryCommand class.
     * @param workerRepository the repository for managing workers
     * @param console the console for input/output operations
     */
    public PrintFieldDescendingSalaryCommand(WorkerRepository workerRepository, Console console){
        super("print_field_descending_salary", "print the salary field values of all the elements in descending order");
        this.workerRepository = workerRepository;
        this.console = console;
    }



    /**
     * Executes the print_field_descending_salary command.
     *
     * @param argms the command arguments
     * @return 0 if successful, another value if validation fails
     */
    @Override
    public int execute(String argms) {
        if(!validateNoArgument(argms, console)) {
            return 1;
        }
        Result<List<Long>> result = workerRepository.getDescendingSalaries();
        List<Long> descendingSalaries = result.getValue();

        console.println("Descending salaries:");
        descendingSalaries.forEach(console::println);
        return 0;
    }
}
