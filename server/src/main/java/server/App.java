package server;

public class  App {
    public static void main(String[] args) {

    var systemBootstrapper =  new SystemBootstrapper(args);
    RunnerAppServer runnerAppServer = systemBootstrapper.getRunnerAppServer();
    runnerAppServer.start();
    }
}
