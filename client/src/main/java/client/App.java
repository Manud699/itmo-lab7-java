package client;
import client.cli.ApplicationRunner;


public class App {
    public static void main(String[]argms) {
        var systemBootstrapper = new SystemBootstrapper(argms);
        ApplicationRunner applicationRunner = systemBootstrapper.buildApplicationRunner();
        applicationRunner.startSession();
    }
}

