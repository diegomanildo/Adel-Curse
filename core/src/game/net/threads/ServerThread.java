package game.net.threads;

import game.net.Client;
import game.net.Messages;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ServerThread extends Thread {
    public static final String SPECIAL_CHARACTER = "!";
    public static final int PORT = 22121;
    private static final int MAX_CLIENTS = 2;

    private final DatagramSocket socket;
    private boolean end;
    private Client[] clients = new Client[MAX_CLIENTS];
    private int clientsConnected;

    public ServerThread() {
        try {
            socket = new DatagramSocket(PORT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        super.run();
        System.out.println("Server started");

        while (!end) {
            DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
            try {
                socket.receive(packet);
                processMessage(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void processMessage(DatagramPacket packet) {
        String message = new String(packet.getData()).trim();
        System.out.println("Message: " + message);
        String[] parts = message.split(SPECIAL_CHARACTER);

        switch (parts[0]) {
            case Messages.CONNECT:
                boolean goodConnected = connectClient(packet);
                if (goodConnected && clients.length == MAX_CLIENTS) {
                    sendMessageToAll(Messages.START_GAME);
                }
                break;
            case Messages.DISCONNECT:
                break;
            default:
                throw new RuntimeException("Message not recognized: " + parts[0]);
        }
    }

    private boolean connectClient(DatagramPacket packet) {
        if (clients.length < MAX_CLIENTS) {
            if (!clientExists(packet.getAddress(), packet.getPort())) {
                addClient(packet);
                sendMessage(Messages.CONNECT + SPECIAL_CHARACTER + Messages.SUCCESSFUL + SPECIAL_CHARACTER + (clientsConnected - 1), packet.getAddress(), packet.getPort());
                return true;
            }
        }

        return false;
    }

    private boolean clientExists(InetAddress address, int port) {
        for (int i = 0; i < clientsConnected; i++) {
            if (clients[i].getIp().equals(address) && clients[i].getPort() == port) {
                return true;
            }
        }
        return false;
    }

    private void addClient(DatagramPacket packet) {
        clients[clientsConnected] = new Client(packet.getAddress(), packet.getPort(), clients.length);
        clientsConnected++;
        System.out.println("Cliente conectado");
    }

    public void sendMessage(String msg, InetAddress ip, int port){
        byte[] data = msg.getBytes();
        DatagramPacket packet = new DatagramPacket(data, data.length, ip, port);
        try {
            socket.send(packet);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessageToAll(String msg){
        if (clientsConnected == 0) {
            return;
        }
        for (int i = 0; i < clientsConnected; i++) {
            sendMessage(msg, clients[i].getIp(), clients[i].getPort());
        }
    }

    public void clearClients() {
        clients = new Client[MAX_CLIENTS];
        clientsConnected = 0;
    }

    public void end() {
        end = true;
        socket.close();
    }
}
