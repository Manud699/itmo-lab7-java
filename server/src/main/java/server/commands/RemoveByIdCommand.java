//package server.commands;
//
//import common.network.Request;
//import common.network.Response;
//import common.network.Result;
//import common.repository.WorkerRepository;
//import server.repository.LocalWorkerRepository;
//import common.util.NumberParseSafe;
//
//import java.util.Optional;
//
//public class RemoveByIdCommand extends AbstractCommand {
//
//    private final WorkerRepository localWorkerRepository;
//
//    public RemoveByIdCommand(WorkerRepository localWorkerRepository){
//        super("remove_by_id", "Delete a worker by its ID. ");
//        this.localWorkerRepository = localWorkerRepository;
//    }
//
//    @Override
//    public Response execute(Request request){
//        var error = validateHasArgument(request);
//        if(error != null){
//            return error;
//        }
//        Optional<Long> workerId = NumberParseSafe.parse(request.getCommandArgument(), Long::parseLong);
//        if(workerId.isEmpty()){
//            return new Response(Result.failure("Invalid id " + request.getCommandArgument()));
//        }
//        Result<Boolean> result = localWorkerRepository.removeById(workerId.get());
//        return new Response(result);
//    }
//
//
//
//}
