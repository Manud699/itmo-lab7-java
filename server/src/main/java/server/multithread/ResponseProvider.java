package server.multithread;

import common.network.Response;

import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class ResponseProvider {

    private final ExecutorService executorService;
    private final DatagramSocket datagramSocket;
    public ResponseProvider(DatagramSocket datagramSocket) {
        this.executorService = Executors.newFixedThreadPool(5);
        this.datagramSocket = datagramSocket;
    }


    public void sendResponse(Response response, SocketAddress socketAddress){
        executorService.submit(new SendResponse(response, socketAddress,datagramSocket));
    }
}
