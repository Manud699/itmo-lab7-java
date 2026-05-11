package server.multithread;

import common.network.Request;
import server.repository.CommandRegistry;
import java.net.SocketAddress;

public class ClientHandler implements Runnable{

    private final CommandRegistry commandRegistry;
    private final Request request;
    private final ResponseProvider responseProvider;
    private final SocketAddress socketAddress;



    public ClientHandler(
            Request request,
            CommandRegistry commandRegistry,
            ResponseProvider responseProvider,
            SocketAddress socketAddress
            ){
        this.commandRegistry = commandRegistry;
        this.request = request;
        this.responseProvider = responseProvider;
        this.socketAddress = socketAddress;
    }


    @Override
    public void run(){
        try {
            UserContext.set(request.getUser());
            var response = commandRegistry.executeCommand(request);
            responseProvider.sendResponse(response, socketAddress);

        } finally {
            UserContext.clearThread();
        }
    }


}
