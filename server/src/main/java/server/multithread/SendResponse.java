package server.multithread;

import common.network.DataChunk;
import common.network.Response;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SendResponse implements Runnable  {

    private static final Logger logger = Logger.getLogger(SendResponse.class.getName());

    private final Response response;
    private final SocketAddress clientAddress;
    private final DatagramSocket datagramSocket;

    public SendResponse(Response response, SocketAddress clientAddress, DatagramSocket socket){
        this.response = response;
        this.clientAddress = clientAddress;
        this.datagramSocket = socket;
    }

    @Override
    public void run(){
        try {
            sendChunkPakecks(response, clientAddress, datagramSocket);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error sending chunks", e);
            throw new RuntimeException(e);
        }
    }

    private void sendChunkPakecks(Response response, SocketAddress clientAddress, DatagramSocket socket) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(response);
        oos.flush();
        byte[] fullData = baos.toByteArray();
        int CHUNK_SIZE = 1024;
        int totalChunks = (int) Math.ceil((double) fullData.length / CHUNK_SIZE);

        logger.log(Level.INFO, "Sending response in " + totalChunks + " packet(s)...");

        for(int i = 0; i < totalChunks; i++){
            int start = i * CHUNK_SIZE;
            int length = Math.min(CHUNK_SIZE, fullData.length - start);

            byte[] piece = new byte[length];
            System.arraycopy(fullData,start,piece,0,length);
            var dataChunk = new DataChunk(totalChunks,i,piece);

            ByteArrayOutputStream chunckBaos = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(chunckBaos);

            objectOutputStream.writeObject(dataChunk);
            byte[] bufferToSend = chunckBaos.toByteArray();


            var packetToSend = new DatagramPacket(
                    bufferToSend,
                    bufferToSend.length,
                    clientAddress
            );
            socket.send(packetToSend);
        }
    }
}