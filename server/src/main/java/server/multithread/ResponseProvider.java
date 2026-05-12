package server.multithread;

import common.network.Response;
import server.lifecycle.Shutdownable;

import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;


public final class ResponseProvider implements Shutdownable {

    private final ExecutorService executorService;
    private final DatagramSocket datagramSocket;

    public ResponseProvider(DatagramSocket datagramSocket) {
        this.executorService = Executors.newFixedThreadPool(10, getThreadFactory());
        this.datagramSocket = datagramSocket;
    }


    public void sendResponse(Response response, SocketAddress socketAddress){
        executorService.submit(new SendResponse(response, socketAddress,datagramSocket));
    }


    private ThreadFactory getThreadFactory(){
        return runnable -> {
            var t = new Thread(runnable);
            t.setName("UDP-Sender-"+ t.getId());
            return t;
        };
    }


    @Override
    public void shutdownHook(){
        ExecutorUtils.shutdownAndAwaitTermination(executorService, "UDP-Sender");
    }


}
