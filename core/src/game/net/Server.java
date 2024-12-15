package game.net;

import com.ac.ServerConsoleScreen;
import game.map.Door;
import game.net.utilities.Thread;
import game.utilities.Direction;
import utilities.Timer;
import utilities.Utils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Server extends Thread {
    public static final int PORT = 22121;
    public static final int OWNER = 0;
    public static final String SP_C = "!"; // Special character
    public static final int MAX_CLIENTS = 2;
    public static final float TIMEOUT_TIME = 4;

    private final ServerConsoleScreen chat;
    private final DatagramSocket socket;
    private boolean end;
    private final ClientData[] clients = new ClientData[MAX_CLIENTS];
    private int clientsConnected;

    public Server(ServerConsoleScreen chat) {
        this.chat = chat;
        try {
            socket = new DatagramSocket(PORT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void console(String tag, Object obj) {
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        String message = "[" + time + "] [" + tag + "] [SERVER] " + obj;
        System.out.println(message);
        chat.addMessage(message);
    }

    private void consoleInfo(Object obj) {
        console("INFO", obj);
    }

    private void consoleWarning(Object obj) {
        console("WARNING", obj);
    }

    private void consoleError(Object obj) {
        console("ERROR", obj);
    }

    @Override
    public void run() {
        super.run();
        consoleInfo("Started on port " + PORT);

        while (!end && !socket.isClosed()) {
            DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
            try {
                socket.receive(packet);
                processMessage(packet);
                if (!Utils.isDebugging()) {
                    checkTimeouts();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void processMessage(DatagramPacket packet) {
        String message = new String(packet.getData()).trim();
        String[] parts = message.split(SP_C);
        try {
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
                case Messages.STILL_CONNECTED: {
                    int clientId = Integer.parseInt(parts[1]);
                    if (clients[clientId] != null) {
                        Timer timer = clients[clientId].getTimer();
                        timer.reset();
                        timer.start();
                    }
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
                case Messages.CREATE_ITEMS: {
                    int clientId = Integer.parseInt(parts[1]);
                    int roomId = Integer.parseInt(parts[2]);
                    ArrayList<Door> doors = Door.toDoors(parts[3]);
                    sendMessageToAllExpect(clientId, Messages.CREATE_ITEMS + SP_C + roomId + SP_C + Door.toString(doors));
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
                    internalSleep(25); // Sleep for wait until entities appear
                    int clientId = Integer.parseInt(parts[1]);
                    sendMessageToAllExpect(clientId, Messages.CREATE_ENTITY + SP_C + parts[2]);
                    break;
                }
                case Messages.REMOVE_ENTITY: {
                    int clientId = Integer.parseInt(parts[1]);
                    int entityId = Integer.parseInt(parts[2]);
                    sendMessageToAllExpect(clientId, Messages.REMOVE_ENTITY + SP_C + entityId);
                    break;
                }
                case Messages.REVIVE_PLAYER: {
                    int clientId = Integer.parseInt(parts[1]);
                    int entityId = Integer.parseInt(parts[2]);
                    sendMessageToAllExpect(clientId, Messages.REVIVE_PLAYER + SP_C + entityId);
                    break;
                }
                case Messages.CHANGE_FRAMES: {
                    int clientId = Integer.parseInt(parts[1]);
                    int entityId = Integer.parseInt(parts[2]);
                    String texturePath = parts[3];
                    int column = Integer.parseInt(parts[4]);
                    int rows = Integer.parseInt(parts[5]);
                    float frameDuration = Float.parseFloat(parts[6]);
                    sendMessageToAllExpect(clientId, Messages.CHANGE_FRAMES + SP_C + entityId + SP_C + texturePath + SP_C + column + SP_C + rows + SP_C + frameDuration);
                    break;
                }
                case Messages.CHANGE_TEXTURE: {
                    int clientId = Integer.parseInt(parts[1]);
                    int entityId = Integer.parseInt(parts[2]);
                    String texturePath = parts[3];
                    sendMessageToAllExpect(clientId, Messages.CHANGE_TEXTURE + SP_C + entityId + SP_C + texturePath);
                    break;
                }
                case Messages.VELOCITY: {
                    int clientId = Integer.parseInt(parts[1]);
                    int entityId = Integer.parseInt(parts[2]);
                    float velocity = Float.parseFloat(parts[3]);
                    sendMessageToAllExpect(clientId, Messages.VELOCITY + SP_C + entityId + SP_C + velocity);
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
                    consoleError("Message not recognized: " + message);
            }
        } catch (Exception e) {
            consoleError("Message \"" + message + "\" has an error: " + e);
            e.printStackTrace();
        }
    }

    private boolean connectClient(DatagramPacket packet) {
        if (clientsConnected >= MAX_CLIENTS || clientExists(packet.getAddress(), packet.getPort())) {
            return false;
        }

        sendMessage(Messages.CONNECT + SP_C + clientsConnected, packet.getAddress(), packet.getPort());
        clients[clientsConnected] = new ClientData(packet.getAddress(), packet.getPort(), clientsConnected);
        clients[clientsConnected].getTimer().start();
        consoleInfo("Client " + clients[clientsConnected].getId() + " connected");
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
        consoleInfo("Client " + clients[index].getId() + " disconnected");
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

    private void checkTimeouts() {
        for (int i = 0; i < clients.length; i++) {
            if (clients[i] != null && clients[i].getTimer().getSeconds() >= TIMEOUT_TIME) {
                consoleWarning("Client " + clients[i].getId() + " was timed out for being absent for " + TIMEOUT_TIME + " seconds");
                removeClient(i);
            }
        }
    }

    public void end() {
        clearClients();
        consoleInfo("Closed");
        end = true;
        socket.close();
    }
}
