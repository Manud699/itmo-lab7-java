package client.commands;

import client.cli.Console;
import client.repository.ProxyWorkerRepository;
import common.network.Result;
import common.repository.WorkerRepository;


/**
 * Command: SumOfSalaryCommand
 * Command description: Displays the sum of the salaries of all elements in the collection.
 */
public class SumOfSalaryCommand extends AbstractCommand {



    private final WorkerRepository workerRepository;
    private final Console console;


    /**
     * Constructor for the SumOfSalaryCommand class.
     * @param workerRepository the repository for managing workers
     * @param console the console for input/output operations
     */
    public SumOfSalaryCommand(WorkerRepository workerRepository, Console console) {
        super("sum_of_salary","Displays the sum of the salaries of all elements in the collection");
        this.workerRepository = workerRepository;
        this.console = console;
    }


    /**
     * Executes the sum_of_salary command.
     *
     * @param argms the command arguments
     * @return the exit code
     */
    @Override
    public int execute(String argms) {
        if(!validateNoArgument(argms, console)) {
            return 1;
        }
        Result<Long> result = workerRepository.sumOfSalary();
        console.println("Total salary of all workers: " + result.getValue());
        return 0;
    }

}
