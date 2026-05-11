package server;

import server.network.ServerAddress;
import server.repository.DataBaseWorker;
import server.repository.UserRepository;
import server.commands.*;
import server.network.NetworkServer;
import server.repository.CommandRegistry;
import server.repository.LocalWorkerRepository;
import server.storagedb.DatabaseConnection;
import server.storagedb.DatabaseCredentials;
import common.repository.WorkerRepository;
import java.util.logging.Logger;




public class SystemBootstrapper {
    private static final Logger logger = Logger.getLogger(SystemBootstrapper.class.getName());
    private CommandRegistry commandRegistry;
    private WorkerRepository workerRepository;
    private NetworkServer networkServer;
    private DatabaseConnection databaseConnection;
    private final String [] argms;
    private UserRepository userRepository;
    private LocalWorkerRepository localWorkerRepository;

    
    public SystemBootstrapper(String[] argms){
            this.argms = argms;
    }



    public RunnerAppServer getRunnerAppServer() {
        initDataBaseConnection();
        initRepository();
        initCommands();
        initNetworking();
        initLoadData();
        return new RunnerAppServer(networkServer);
    }



    public void initDataBaseConnection() {
        DatabaseCredentials databaseCredentials = new DatabaseCredentials("jdbc:postgresql://localhost:5432/workers", "postgres", "12345");
        DatabaseConnection.init(databaseCredentials);
        databaseConnection = DatabaseConnection.getInstance();
    }



    public void initRepository(){
        this.localWorkerRepository = new LocalWorkerRepository();
        this.commandRegistry = new CommandRegistry();
        this.workerRepository = new DataBaseWorker(databaseConnection, localWorkerRepository);
        this.userRepository = new UserRepository(databaseConnection);
    }



    public void initCommands(){
        commandRegistry.addCommand(new AddCommand(workerRepository));
        commandRegistry.addCommand(new ClearCommand(workerRepository));
        commandRegistry.addCommand(new ShowCommand(workerRepository));
        commandRegistry.addCommand(new UpdateByIdCommand(workerRepository));
        commandRegistry.addCommand(new CheckId(workerRepository));
        commandRegistry.addCommand(new RemoveByIdCommand(workerRepository));
        commandRegistry.addCommand(new HeadCommand(workerRepository));
        commandRegistry.addCommand(new RemoveAllByPositionCommand(workerRepository));
        commandRegistry.addCommand(new SumOfSalaryCommand(workerRepository));
        commandRegistry.addCommand(new PrintFieldDescendingSalaryCommand(workerRepository));
        commandRegistry.addCommand(new RemoveHeadCommand(workerRepository));
        commandRegistry.addCommand(new InfoCommand(workerRepository));
        commandRegistry.addCommand(new RegistrateUserCommand(userRepository));
        commandRegistry.addCommand(new LoggingUserCommand(userRepository));
    }



    public void initNetworking(){
        int port = ServerAddress.parsePort(argms);
        this.networkServer = new NetworkServer(port, commandRegistry);
    }



    public void initLoadData(){
        workerRepository.load();
    }
}
