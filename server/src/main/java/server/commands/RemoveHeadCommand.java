//package server.commands;
//
//import common.model.Worker;
//import common.network.Request;
//import common.network.Response;
//import common.network.Result;
//import common.repository.WorkerRepository;
//import server.repository.LocalWorkerRepository;
//
//public class RemoveHeadCommand extends AbstractCommand {
//
//    private final WorkerRepository localWorkerRepository;
//
//    public RemoveHeadCommand(WorkerRepository localWorkerRepository) {
//        super("remove_head", "Prints and removes the first element of the collection");
//        this.localWorkerRepository = localWorkerRepository;
//    }
//
//    @Override
//    public Response execute(Request request){
//        var error = validateNoArgument(request);
//        if(error != null){
//            return error;
//        }
//
//        Result<Worker> result = localWorkerRepository.removeHead();
//        return new Response(result);
//    }
//
//}
