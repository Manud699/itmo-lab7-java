package server;

import server.network.NetworkServer;

public class RunnerAppServer {

    private final NetworkServer networkServer;


    public RunnerAppServer(NetworkServer networkServer){
        this.networkServer = networkServer;
    }
    public void start(){

            networkServer.start();
    }
}
