//package client.commands;
//
//import java.util.Optional;
//import client.cli.Console;
//import common.network.Result;
//import common.repository.WorkerRepository;
//import common.util.NumberParseSafe;
//
//
///**
// * Command: remove_by_id
// * Description: Removes an element from the collection by its ID.
// */
//public class RemoveByIdCommand extends AbstractCommand {
//
//    private final WorkerRepository workerRepository;
//    private final Console console;
//
//
//    /**
//     * Constructor for the RemoveByIdCommand class.
//     * @param workerRepository the repository for managing workers
//     * @param console the console for input/output operations
//     */
//    public RemoveByIdCommand( WorkerRepository workerRepository, Console console){
//        super("remove_by_id", "Removes an element from the collection by its ID");
//        this.console = console;
//        this.workerRepository = workerRepository;
//    }
//
//
//
//    /**
//     * Executes the remove_by_id command.
//     * @param argument the ID of the worker to remove
//     * @return 0 if successful, another value if validation fails or the collection is empty
//     */
//    @Override
//    public int execute(String argument) {
//        if(!validateHasArgument(argument, console)) {
//            console.printError("Please provide the ID of the worker to remove.");
//            return 1;
//        }
//
//        Optional<Long> parseId = NumberParseSafe.parse(argument, Long::parseLong);
//        if (parseId.isEmpty()) {
//            console.printError("Invalid number format. Please enter a valid whole number.");
//            return 2;
//        }
//        long workerId = parseId.get();
//        Result<Boolean> result = workerRepository.removeById(workerId);
//        if(!result.isSuccess()){
//            console.printError(result.getErrorMessage());
//            return 3;
//        }
//        boolean isRemoved = result.getValue();
//        if(isRemoved) {
//            console.println("Worker with ID '" + workerId + "'' has been successfully removed.");
//            return 0;
//        }
//        console.printError("Worker with ID " + "'"+ workerId + "'"+" not found.");
//        return 4;
//    }
//}
