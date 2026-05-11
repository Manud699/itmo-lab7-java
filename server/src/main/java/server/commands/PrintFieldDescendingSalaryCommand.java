package server.commands;

import common.network.Request;
import common.network.Response;
import common.network.Result;
import common.repository.WorkerRepository;
import java.util.List;



public class PrintFieldDescendingSalaryCommand extends AbstractCommand {


    private final WorkerRepository workerRepository;



    public PrintFieldDescendingSalaryCommand(WorkerRepository workerRepository){
        super("print_field_descending_salary", "print the salary field values of all the elements in descending order");
        this.workerRepository = workerRepository;
    }



    @Override
    public Response execute(Request request) {
        var error = validateNoArgument(request);
        if(error != null) {
            return  error;
        }
        Result<List<Long>> result = workerRepository.getDescendingSalaries();
        return new Response(result);
    }
}
