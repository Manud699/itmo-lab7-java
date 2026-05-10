package client;

import client.builders.UserBuild;
import client.cli.*;
import client.commands.*;
import client.builders.CoordinatesBuilder;
import client.builders.OrganizationBuilder;
import client.builders.WorkerMainBuilder;
import client.network.ArgumentNetworkParse;
import client.network.NetworkClient;
import client.repository.*;
import client.usercommands.LoggingUserCommand;
import client.usercommands.RegistrateUserCommand;



public class SystemBootstrapper {
    private InputProvider inputProvider;
    private Console console;
    private CommandRegistry commandRegistry;
    private ProxyWorkerRepository proxyWorkerRepository;
    private ScriptExecutionStack scriptExecutionStack;
    private final String[] argumentsFromMain;
    private WorkerMainBuilder workerBuilder;
    private NetworkClient networkClient;
    private CommandUserRegistry commandUserRegistry;
    private UserBuild userBuild;
    private UserRegistry userRegistry;
    private ClientSession clientSession;


    
    public SystemBootstrapper(String[] argumentsFromMain){
        this.argumentsFromMain = argumentsFromMain;
    }



    public ApplicationRunner buildApplicationRunner() {
        initInfrastructure();
        initNetworkClient();
        initRepositories();
        initBuildersMainObject();
        initProxyRepository();
        initCommands();
        initCommandsUser();
        return new ApplicationRunner(console, inputProvider, commandRegistry, scriptExecutionStack, commandUserRegistry);
    }



    public void initInfrastructure() {
        this.inputProvider = new InputProvider();
        this.console = new StandardConsole();

    }


    public void initNetworkClient(){
        var networkArguments = ArgumentNetworkParse.parseNetworkArguments(argumentsFromMain, console);
        this.networkClient = new NetworkClient(networkArguments.host(), networkArguments.port());
    }


    public void initProxyRepository(){
        this.proxyWorkerRepository = new ProxyWorkerRepository(networkClient, clientSession);
    }



    public void initRepositories() {
        this.userRegistry = new UserRegistry(networkClient);
        this.commandUserRegistry = new CommandUserRegistry(console);
        this.commandRegistry = new CommandRegistry(console);
        this.scriptExecutionStack = new ScriptExecutionStack(inputProvider, console);
        this.clientSession = new ClientSession();
    }



    public void initBuildersMainObject() {
        var coordinatesBuild = new CoordinatesBuilder(inputProvider, console);
        var organizationBuild = new OrganizationBuilder(inputProvider, console);
        this.workerBuilder = new WorkerMainBuilder(inputProvider, console);
        workerBuilder.setCoordinatesBuild(coordinatesBuild);
        workerBuilder.setOrganizationBuilder(organizationBuild);
        this.userBuild = new UserBuild(inputProvider, console);
    }



    public void initCommands() {
        commandRegistry.addCommand(new AddCommand(proxyWorkerRepository, console, workerBuilder));
        commandRegistry.addCommand(new ShowCommand(proxyWorkerRepository, console));
//        commandRegistry.addCommand(new RemoveByIdCommand(proxyWorkerRepository, console));
//        commandRegistry.addCommand(new HeadCommand(proxyWorkerRepository, console));
//        commandRegistry.addCommand(new RemoveHeadCommand(proxyWorkerRepository, console));
//        commandRegistry.addCommand(new SumOfSalaryCommand(proxyWorkerRepository, console));
        commandRegistry.addCommand(new HelpCommand(commandRegistry, console));
        commandRegistry.addCommand(new HistoryCommand(commandRegistry, console));
        commandRegistry.addCommand(new ClearCommand(proxyWorkerRepository, console));
//        commandRegistry.addCommand(new InfoCommand(proxyWorkerRepository, console));
        commandRegistry.addCommand(new UpdateByIdCommand(proxyWorkerRepository, console, workerBuilder));
        commandRegistry.addCommand(new ExitCommand(console));
//        commandRegistry.addCommand(new ExecuteScriptCommand(console, scriptExecutionStack));
//        commandRegistry.addCommand(new PrintFieldDescendingSalaryCommand(proxyWorkerRepository, console));
//        commandRegistry.addCommand(new RemoveAllByPosition(proxyWorkerRepository, console));
    }

    public void initCommandsUser(){
        commandUserRegistry.addCommand("logging", new LoggingUserCommand(userBuild, userRegistry, console, clientSession));
        commandUserRegistry.addCommand("registrate", new RegistrateUserCommand(userBuild,userRegistry,console, clientSession));
    }

}