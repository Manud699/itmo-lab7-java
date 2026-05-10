//package server.commands;
//
//import common.network.Request;
//import common.network.Response;
//import common.network.Result;
//import common.repository.WorkerRepository;
//import server.repository.LocalWorkerRepository;
//
//public class SumOfSalaryCommand extends AbstractCommand {
//
//    private final WorkerRepository localWorkerRepository;
//
//    public SumOfSalaryCommand(WorkerRepository localWorkerRepository){
//        super("sum_of_salary","Displays the sum of the salaries of all elements in the collection");
//        this.localWorkerRepository = localWorkerRepository;
//    }
//
//    @Override
//    public Response execute(Request request){
//        var error = validateNoArgument(request);
//        if(error != null){
//            return error;
//        }
//        Result<Long> result = localWorkerRepository.sumOfSalary();
//        return new Response(result);
//    }
//}
