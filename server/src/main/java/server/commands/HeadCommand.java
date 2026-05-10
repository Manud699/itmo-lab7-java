//package server.commands;
//
//import common.model.Worker;
//import common.network.Request;
//import common.network.Response;
//import common.network.Result;
//import common.repository.WorkerRepository;
//import server.repository.LocalWorkerRepository;
//
//public class HeadCommand extends AbstractCommand {
//
//    private final WorkerRepository localWorkerRepository;
//
//    public HeadCommand(WorkerRepository localWorkerRepository){
//        super("head", "Return head of collection");
//        this.localWorkerRepository = localWorkerRepository;
//    }
//
//
//    public Response execute(Request request){
//        var error = validateNoArgument(request);
//        if(error != null){
//            return error;
//        }
//        Result<Worker> result = localWorkerRepository.getHead();
//        return new Response(result);
//    }
//}
