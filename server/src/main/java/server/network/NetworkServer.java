package server.network;

import server.lifecycle.Shutdownable;
import server.multithread.ExecutorUtils;
import server.multithread.ReadThread;
import server.multithread.ResponseProvider;
import server.repository.CommandRegistry;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NetworkServer implements Shutdownable {

    private final int port;
    private final CommandRegistry commandRegistry;
    private final ExecutorService readPool;
    private ResponseProvider responseProvider;
    private final static Logger logger = Logger.getLogger(NetworkServer.class.getName());


    public NetworkServer(int port, CommandRegistry commandRegistry) {
        this.port = port;
        this.commandRegistry = commandRegistry;
        this.readPool = Executors.newFixedThreadPool(10, getWorkerFactory());
    }


    public void start() {
        try (DatagramSocket datagramSocket = new DatagramSocket(port)) {
            responseProvider = new ResponseProvider(datagramSocket);
            ByteBuffer buffer = ByteBuffer.allocate(2048);

            DatagramPacket receivePacket = new DatagramPacket(buffer.array(), buffer.capacity());
            logger.info("UDP server started and listening on port " + port);

            for (;;) {
                receivePacket.setLength(buffer.capacity());
                datagramSocket.receive(receivePacket);
                SocketAddress clientAddress = receivePacket.getSocketAddress();
                byte[] dataCopy = Arrays.copyOf(receivePacket.getData(), receivePacket.getLength());
                readPool.submit(new ReadThread(commandRegistry, responseProvider,clientAddress, dataCopy));
                logger.log(Level.INFO, "Dispatched raw packet (" + dataCopy.length + " bytes) from [" + clientAddress + "] to the read pool.");
            }

        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
            ExecutorUtils.shutdownAndAwaitTermination(readPool, "Read-UDP-Pool");
            System.exit(1);
        }
    }

    @Override
    public void shutdownHook(){
        ExecutorUtils.shutdownAndAwaitTermination(readPool, "Read-UDP-Pool");
        responseProvider.shutdownHook();
        logger.info("Server safely stopped.");

    }


    private ThreadFactory getWorkerFactory(){
        return runnable -> {
            Thread t = new Thread(runnable);
            t.setName("Task-Dispatcher" + t.getId());
            return t;
        };
    }
}