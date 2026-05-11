package server.network;

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
import java.util.logging.Level;
import java.util.logging.Logger;

public class NetworkServer {

    private final int port;
    private final CommandRegistry commandRegistry;
    private final ExecutorService readPool;
    private final boolean running = true;
    private final static Logger logger = Logger.getLogger(NetworkServer.class.getName());



    public NetworkServer(int port, CommandRegistry commandRegistry) {
        this.port = port;
        this.commandRegistry = commandRegistry;
        this.readPool = Executors.newFixedThreadPool(10);
    }

    public void start() {
        try (DatagramSocket datagramSocket = new DatagramSocket(port)) {
            ResponseProvider responseProvider = new ResponseProvider(datagramSocket);
            ByteBuffer buffer = ByteBuffer.allocate(2048);

            DatagramPacket receivePacket = new DatagramPacket(buffer.array(), buffer.capacity());
            logger.info("UDP server started and listening on port " + port);

            while (running) {
                receivePacket.setLength(buffer.capacity());
                datagramSocket.receive(receivePacket);

                SocketAddress clientAddress = receivePacket.getSocketAddress();
                byte[] dataCopy = Arrays.copyOf(receivePacket.getData(), receivePacket.getLength());

                readPool.submit(new ReadThread( commandRegistry, responseProvider,clientAddress, dataCopy));
            }

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Critical error: Could not open port " + port + ". " + e.getMessage());
            System.exit(1);
        }
    }
}