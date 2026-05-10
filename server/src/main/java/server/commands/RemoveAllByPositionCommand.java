//package server.commands;
//
//import common.model.Position;
//import common.network.Request;
//import common.network.Response;
//import common.network.Result;
//import common.repository.WorkerRepository;
//import server.repository.LocalWorkerRepository;
//
//public class RemoveAllByPositionCommand extends AbstractCommand{
//
//    private final WorkerRepository localWorkerRepository;
//
//    public RemoveAllByPositionCommand(WorkerRepository localWorkerRepository){
//        super("remove_all_by_position", "Removes all elements from the collection whose position field is equivalent to the specified one");
//        this.localWorkerRepository = localWorkerRepository;
//    }
//
//
//
//    @Override
//    public Response execute(Request request){
//        var error = validateHasArgument(request);
//        if(error != null){
//            return error;
//        }
//        Position position = convertToStatusEnum(request.getCommandArgument());
//        if(position == null){
//            return new Response(Result.failure("Error: '" + request.getCommandArgument() + "' is not a valid position."));
//        }
//        Result<Integer> result = localWorkerRepository.removeAllByPosition(position);
//        return new Response(result);
//    }
//
//
//
//    public Position convertToStatusEnum(String argm) {
//        try {
//            return Position.valueOf(argm.trim().toUpperCase());
//        } catch (IllegalArgumentException e) {
//            return null;
//        }
//    }
//}
