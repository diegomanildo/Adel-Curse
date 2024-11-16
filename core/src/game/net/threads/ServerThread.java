package game.net.threads;

import game.net.Direction;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ServerThread extends Thread {
    public static final String OK_MESSAGE = "OK";
    public static final String START_MESSAGE = "Start";
    public static final int PORT = 22121;
    private static final int MAX_CLIENTS = 3;

    private final DatagramSocket connexion;
    private boolean end;
    private final Direction[] clients = new Direction[MAX_CLIENTS];
    private int clientsConnected = 0;

    public ServerThread() {
        try {
            connexion = new DatagramSocket(PORT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void sendMessage(String message, InetAddress ip, int port) {
        byte[] data = message.getBytes();
        DatagramPacket datagramPacket = new DatagramPacket(data, data.length, ip, port);
        try {
            connexion.send(datagramPacket);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        super.run();

        while (!end) {
            DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
            try {
                connexion.receive(packet);
                processMessage(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void processMessage(DatagramPacket packet) {
        String message = new String(packet.getData()).trim();
        System.out.println("Message = " + message);
        if (!message.equals(ClientThread.CONNECT_MESSAGE)) {
            return;
        }
        if (clientsConnected >= MAX_CLIENTS) {
            return;
        }

        System.out.println("Client " + clientsConnected + " connected");
        clients[clientsConnected] = new Direction(packet.getAddress(), packet.getPort());
        sendMessage(OK_MESSAGE, clients[clientsConnected].getIp(), clients[clientsConnected].getPort());
        clientsConnected++;
        if (clientsConnected == MAX_CLIENTS) {
            for (int i = 0; i < clientsConnected; i++) {
                sendMessage(START_MESSAGE, clients[i].getIp(), clients[i].getPort());
            }
        }
    }
}
