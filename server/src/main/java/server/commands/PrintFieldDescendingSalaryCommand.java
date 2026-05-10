//package server.commands;
//
//import common.network.Request;
//import common.network.Response;
//import common.network.Result;
//import common.repository.WorkerRepository;
//import server.repository.LocalWorkerRepository;
//
//import java.util.List;
//
///**
// * Command: print_field_descending_salary
// * Description: Prints the salary field values of all the elements in descending order.
// */
//public class PrintFieldDescendingSalaryCommand extends AbstractCommand {
//
//
//    private final WorkerRepository localWorkerRepository;
//
//
//    /**
//     * Constructor for the PrintFieldDescendingSalaryCommand class.
//     * @param localWorkerRepository the repository for managing workers
//     */
//    public PrintFieldDescendingSalaryCommand(WorkerRepository localWorkerRepository){
//        super("print_field_descending_salary", "print the salary field values of all the elements in descending order");
//        this.localWorkerRepository = localWorkerRepository;
//    }
//
//
//
//    /**
//     * Executes the print_field_descending_salary command.
//     */
//    @Override
//    public Response execute(Request request) {
//        var error = validateNoArgument(request);
//        if(error != null) {
//            return  error;
//        }
//        Result<List<Long>> result = localWorkerRepository.getDescendingSalaries();
//        return new Response(result);
//    }
//}
