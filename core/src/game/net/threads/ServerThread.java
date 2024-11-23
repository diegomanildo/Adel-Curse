package game.net.threads;

import game.net.Client;
import game.net.Messages;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ServerThread extends Thread {
    public static final String SP_C = "!";
    public static final int PORT = 22121;
    private static final int MAX_CLIENTS = 2;

    private final DatagramSocket socket;
    private boolean end;
    private final Client[] clients = new Client[MAX_CLIENTS];
    private int clientsConnected;

    public ServerThread() {
        try {
            socket = new DatagramSocket(PORT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void console(Object obj) {
        System.out.println("[SERVER] " + obj);
    }

    @Override
    public void run() {
        super.run();
        console("Started on port " + PORT);

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
        String[] parts = message.split(SP_C);

        switch (parts[0]) {
            case Messages.CONNECT:
                boolean goodConnected = connectClient(packet);
                if (goodConnected && clientsConnected == MAX_CLIENTS) {
                    sendMessageToAll(Messages.START_GAME);
                }
                break;
            case Messages.DISCONNECT:
                int index = Integer.parseInt(parts[1]);
                removeClient(index);
                break;
            case Messages.POSITION:
                updateEntityPosition(parts);
                break;
            default:
                throw new RuntimeException("Message not recognized: " + parts[0]);
        }
    }

    private boolean connectClient(DatagramPacket packet) {
        if (clientsConnected < MAX_CLIENTS) {
            if (!clientExists(packet.getAddress(), packet.getPort())) {
                sendMessage(Messages.CONNECTION + SP_C + Messages.SUCCESSFUL + SP_C + clientsConnected, packet.getAddress(), packet.getPort());
                addClient(packet);
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
        console("Client " + (clientsConnected + 1) + " connected");
        clients[clientsConnected] = new Client(packet.getAddress(), packet.getPort(), clients.length);
        clientsConnected++;
    }

    private void removeClient(int index) {
        if (clients[index] == null || !clientExists(clients[index].getIp(), clients[index].getPort())) {
            return;
        }

        sendMessage(Messages.DISCONNECTION, clients[index].getIp(), clients[index].getPort());
        console("Client " + (index + 1) + " disconnected");
        clients[index] = null;
        clientsConnected--;
        System.arraycopy(clients, index + 1, clients, index, clientsConnected - index);
        clients[clientsConnected] = null;
    }

    private void updateEntityPosition(String[] parts) {
        int clientId = Integer.parseInt(parts[1]);
        int entityId = Integer.parseInt(parts[2]);
        float x = Float.parseFloat(parts[3]);
        float y = Float.parseFloat(parts[4]);
        sendMessageToAllExpect(clientId, Messages.POSITION + SP_C + entityId + SP_C + x + SP_C + y);
    }

    public void sendMessage(String msg, InetAddress ip, int port) {
        byte[] data = msg.getBytes();
        DatagramPacket packet = new DatagramPacket(data, data.length, ip, port);
        try {
            socket.send(packet);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessageToAll(String msg) {
        sendMessageToAllExpect(-1, msg);
    }

    public void sendMessageToAllExpect(int clientId, String msg) {
        if (clientsConnected == 0) {
            return;
        }
        for (int i = 0; i < clientsConnected; i++) {
            if (i != clientId) {
                sendMessage(msg, clients[i].getIp(), clients[i].getPort());
            }
        }
    }

    public void clearClients() {
        for (int i = 0; i < clientsConnected; i++) {
            removeClient(i);
        }
    }

    public void end() {
        clearClients();
        end = true;
        socket.close();
        console("Closed");
    }
}
