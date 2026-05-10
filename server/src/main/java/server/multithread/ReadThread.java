package server.multithread;

import common.network.Request;
import server.repository.CommandRegistry;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.SocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;


public final class ReadThread implements Runnable {

    private static final Logger logger = Logger.getLogger(ReadThread.class.getName());
    private final CommandRegistry commandRegistry;
    private final ResponseProvider responseProvider;
    private final SocketAddress socketAddress;
    private final byte[]rawData;


    public ReadThread(CommandRegistry commandRegistry, ResponseProvider responseProvider, SocketAddress socketAddress, byte[]rawData){
        this.commandRegistry = commandRegistry;
        this.responseProvider = responseProvider;
        this.socketAddress = socketAddress;
        this.rawData = rawData;
    }

    @Override
    public void run(){
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(rawData, 0, rawData.length);
            ObjectInputStream ois = new ObjectInputStream(bais);
            Request request = (Request) ois.readObject();
            logger.log(Level.INFO,"Received from client -> Command: [" + request.getCommandName() + "]");

            var clientHandler = new ClientHandler(request,commandRegistry,responseProvider, socketAddress);
            var thread = new Thread(clientHandler);
            thread.start();
        } catch (Exception e) {
            logger.log(Level.SEVERE,"Error processing incoming packet ", e);
        }
    }
}
