//package client.commands;
//
//import client.cli.Console;
//import client.cli.formatter.TableDisplayable;
//import common.model.Worker;
//import client.repository.ProxyWorkerRepository;
//import common.network.Result;
//import common.repository.WorkerRepository;
//
//
///**
// * Command: remove_head
// * Description: Prints and removes the first element of the collection.
// */
//public class RemoveHeadCommand extends AbstractCommand implements TableDisplayable {
//
//
//    private final Console console;
//    private final WorkerRepository workerRepository;
//
//
//    /**
//     * Constructor for the RemoveHeadCommand class.
//     * @param workerRepository the repository for managing workers
//     * @param console the console for input/output operations
//     */
//    public RemoveHeadCommand(WorkerRepository workerRepository, Console console) {
//        super("remove_head", "Prints and removes the first element of the collection");
//        this.workerRepository = workerRepository;
//        this.console = console;
//    }
//
//
//    /**
//     * Executes the remove_head command.
//     *
//     * @param argm the command arguments (should be empty)
//     * @return 0 if successful, 1 if validation fails or the collection is empty
//     */
//    @Override
//    public int execute(String argm) {
//        if(!validateNoArgument(argm, console)) {
//            return 1;
//        }
//        Result<Worker> result = workerRepository.removeHead();
//        if(result ==null ){
//            console.println("The collection is empty.");
//            return 0;
//        }
//        console.println("Successfully removed the first worker from the collection:");
//        printWorkerTable(result.getValue(), console);
//        return 0;
//    }
//
//
//}
