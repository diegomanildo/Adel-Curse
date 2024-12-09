package game.net;

import game.net.utilities.Thread;
import game.utilities.Direction;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Server extends Thread {
    public static final int OWNER = 0;
    public static final String SP_C = "!"; // Special character
    public static final int PORT = 22121;
    public static final int MAX_CLIENTS = 2;
    public static final float TIMEOUT_TIME = 3;

    private final DatagramSocket socket;
    private boolean end;
    private final ClientData[] clients = new ClientData[MAX_CLIENTS];
    private int clientsConnected;

    public Server() {
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
            case Messages.CONNECT: {
                boolean goodConnected = connectClient(packet);
                if (goodConnected && clientsConnected == MAX_CLIENTS) {
                    sendMessageToAll(Messages.START_GAME);
                    internalSleep(100);
                    sendMessage(Messages.CREATE_LEVEL, OWNER);
                }
                break;
            }
            case Messages.DISCONNECT: {
                int index = Integer.parseInt(parts[1]);
                removeClient(index);
                break;
            }
            case Messages.RESTART_GAME: {
                int clientId = Integer.parseInt(parts[1]);
                sendMessageToAllExpect(clientId, Messages.RESTART_GAME);
                internalSleep(100);
                sendMessage(Messages.CREATE_LEVEL, OWNER);
                break;
            }
            case Messages.INIT_LEVEL: {
                sendMessageToAllExpect(OWNER, Messages.INIT_LEVEL + SP_C + parts[1]);
                break;
            }
            case Messages.ROOM_CHANGED: {
                int clientId = Integer.parseInt(parts[1]);
                Direction direction = Direction.parseDirection(parts[2]);
                sendMessageToAllExpect(clientId, Messages.ROOM_CHANGED + SP_C + direction);
                break;
            }
            case Messages.POSITION: {
                int clientId = Integer.parseInt(parts[1]);
                int entityId = Integer.parseInt(parts[2]);
                float x = Float.parseFloat(parts[3]);
                float y = Float.parseFloat(parts[4]);
                Direction direction = Direction.parseDirection(parts[5]);
                sendMessageToAllExpect(clientId, Messages.POSITION + SP_C + entityId + SP_C + x + SP_C + y + SP_C + direction);
                break;
            }
            case Messages.SIZE: {
                int clientId = Integer.parseInt(parts[1]);
                int entityId = Integer.parseInt(parts[2]);
                float width = Float.parseFloat(parts[3]);
                float height = Float.parseFloat(parts[4]);
                sendMessageToAllExpect(clientId, Messages.SIZE + SP_C + entityId + SP_C + width + SP_C + height);
                break;
            }
            case Messages.SHOOT: {
                int clientId = Integer.parseInt(parts[1]);
                int entityId = Integer.parseInt(parts[2]);
                Direction direction = Direction.parseDirection(parts[3]);
                sendMessageToAllExpect(clientId, Messages.SHOOT + SP_C + entityId + SP_C + direction);
                break;
            }
            case Messages.CREATE_ENTITY: {
                internalSleep(200);
                int clientId = Integer.parseInt(parts[1]);
                sendMessageToAllExpect(clientId, Messages.CREATE_ENTITY + SP_C + parts[2]);
                break;
            }
            case Messages.HP: {
                int clientId = Integer.parseInt(parts[1]);
                int entityId = Integer.parseInt(parts[2]);
                int hp = Integer.parseInt(parts[3]);
                sendMessageToAllExpect(clientId, Messages.HP + SP_C + entityId + SP_C + hp);
                break;
            }
            case Messages.MAX_HP: {
                int clientId = Integer.parseInt(parts[1]);
                int entityId = Integer.parseInt(parts[2]);
                int maxHp = Integer.parseInt(parts[3]);
                sendMessageToAllExpect(clientId, Messages.MAX_HP + SP_C + entityId + SP_C + maxHp);
                break;
            }
            case Messages.DAMAGE: {
                int clientId = Integer.parseInt(parts[1]);
                int entityId = Integer.parseInt(parts[2]);
                int damage = Integer.parseInt(parts[3]);
                sendMessageToAllExpect(clientId, Messages.DAMAGE + SP_C + entityId + SP_C + damage);
                break;
            }
            case Messages.ARMOR: {
                int clientId = Integer.parseInt(parts[1]);
                int entityId = Integer.parseInt(parts[2]);
                int armor = Integer.parseInt(parts[3]);
                sendMessageToAllExpect(clientId, Messages.ARMOR + SP_C + entityId + SP_C + armor);
                break;
            }
            default:
                throw new RuntimeException("Message not recognized: " + parts[0]);
        }
    }

    private boolean connectClient(DatagramPacket packet) {
        if (clientsConnected >= MAX_CLIENTS || clientExists(packet.getAddress(), packet.getPort())) {
            return false;
        }

        sendMessage(Messages.CONNECT + SP_C + clientsConnected, packet.getAddress(), packet.getPort());
        console("Client " + clientsConnected + " connected");
        clients[clientsConnected] = new ClientData(packet.getAddress(), packet.getPort(), clients.length);
        clientsConnected++;
        return true;
    }

    private boolean clientExists(InetAddress address, int port) {
        for (int i = 0; i < clientsConnected; i++) {
            if (clients[i].getIp().equals(address) && clients[i].getPort() == port) {
                return true;
            }
        }
        return false;
    }

    private void removeClient(int index) {
        if (clients[index] == null || !clientExists(clients[index].getIp(), clients[index].getPort())) {
            return;
        }

        boolean wereMaxClients = clientsConnected == MAX_CLIENTS;

        sendMessageToAll(Messages.END_GAME);
        sendMessage(Messages.DISCONNECT, index);
        console("Client " + index + " disconnected");
        clients[index] = null;
        clientsConnected--;
        System.arraycopy(clients, index + 1, clients, index, clientsConnected - index);
        clients[clientsConnected] = null;

        if (wereMaxClients && clientsConnected != MAX_CLIENTS) {
            clearClients();
        }
    }

    public void sendMessage(String msg, int index) {
        sendMessage(msg, clients[index].getIp(), clients[index].getPort());
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
