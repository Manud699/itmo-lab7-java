package server.commands;

import common.network.Request;
import common.network.Response;
import common.network.Result;
import common.repository.WorkerRepository;


public class SumOfSalaryCommand extends AbstractCommand {

    private final WorkerRepository workerRepository;

    public SumOfSalaryCommand(WorkerRepository workerRepository){
        super("sum_of_salary","Displays the sum of the salaries of all elements in the collection");
        this.workerRepository = workerRepository;
    }

    @Override
    public Response execute(Request request){
        var error = validateNoArgument(request);
        if(error != null){
            return error;
        }
        Result<Long> result = workerRepository.sumOfSalary();
        return new Response(result);
    }
}
