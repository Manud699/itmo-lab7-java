package client.network;

import common.network.DataChunk;
import common.network.Request;
import common.network.Response;
import common.network.Result;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class NetworkClient {
    private final String host;
    private final int port;
    private static final int MAX_RETRIES = 50;
    private static final int BUFFER_SIZE = 4096;

    public NetworkClient(String host, int port) {
        this.host = host;
        this.port = port;
    }



    public Response execute(Request request) {
        try (DatagramChannel channel = DatagramChannel.open()) {
            channel.configureBlocking(false);
            InetSocketAddress serverAddress = new InetSocketAddress(host, port);

            byte[] requestBytes = serialize(request);

            sendData(channel, requestBytes, serverAddress);

            byte[] completeResponseBytes = receiveDataChunks(channel);

            return (Response) deserialize(completeResponseBytes);

        } catch (Exception e) {
            return new Response(Result.failure("Communication error: " + e.getMessage()));
        }
    }



    private byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(obj);
            oos.flush();
        }
        return baos.toByteArray();
    }



    private Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        try (ObjectInputStream ois = new ObjectInputStream(bais)) {
            return ois.readObject();
        }
    }



    private void sendData(DatagramChannel channel, byte[] data, InetSocketAddress address) throws IOException {
        channel.send(ByteBuffer.wrap(data), address);
    }



    private byte[] receiveDataChunks(DatagramChannel channel) throws IOException, InterruptedException, ClassNotFoundException {
        ByteBuffer receiveBuffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
        ByteArrayOutputStream finalBuffer = new ByteArrayOutputStream();

        int totalExpected = -1;
        int chunksReceived = 0;
        int attempts = 0;

        while (attempts < MAX_RETRIES) {
            receiveBuffer.clear();
            InetSocketAddress senderAddress = (InetSocketAddress) channel.receive(receiveBuffer);

            if (senderAddress != null) {
                attempts = 0;
                receiveBuffer.flip();
                byte[] chunkBytes = new byte[receiveBuffer.remaining()];
                receiveBuffer.get(chunkBytes);

                DataChunk chunk = (DataChunk) deserialize(chunkBytes);

                if (totalExpected == -1) {
                    totalExpected = chunk.size();
                }

                finalBuffer.write(chunk.payload());
                chunksReceived++;

                if (chunksReceived == totalExpected) {
                    return finalBuffer.toByteArray();
                }
            } else {
                Thread.sleep(100);
                attempts++;
            }
        }
        throw new IOException("the server is not responding. Please try again.");
    }
}