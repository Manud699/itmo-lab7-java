package client.commands;

import client.cli.Console;
import common.network.Result;
import common.repository.WorkerRepository;
import java.util.List;

public class PrintFieldDescendingSalaryCommand extends AbstractCommand {


    private final WorkerRepository workerRepository;
    private final Console console;



    public PrintFieldDescendingSalaryCommand(WorkerRepository workerRepository, Console console){
        super("print_field_descending_salary", "print the salary field values of all the elements in descending order");
        this.workerRepository = workerRepository;
        this.console = console;
    }


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
