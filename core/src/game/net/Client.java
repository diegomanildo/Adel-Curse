package game.net;

import game.Game;
import game.entities.GameEntity;
import game.map.MapConverter;
import game.map.RoomMap;
import game.utilities.Direction;
import utilities.Render;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client extends Thread {
    public static final int PORT = 22121;
    public static final String SP_C = Server.SP_C; // Special character

    private InetAddress ip;
    private DatagramSocket socket;
    private boolean end;
    private boolean isSendingData;

    public Client() {
        initAll();
    }

    private void initAll() {
        try {
            ip = InetAddress.getByName("255.255.255.255");
            socket = new DatagramSocket();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        super.run();
        initAll();

        while (!end && !socket.isClosed()) {
            DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
            if (!isConnected()) {
                sendMessage(Messages.CONNECT);
            }
            try {
                socket.receive(packet);
                processMessage(packet);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("\nClient " + GameData.clientNumber + ": " + e.getMessage());
            }
        }

        socket.close();
    }

    private void processMessage(DatagramPacket packet) {
        String message = new String(packet.getData()).trim();
        String[] parts = message.split(SP_C);

        switch (parts[0]) {
            case Messages.CONNECT:
                GameData.clientNumber = Integer.parseInt(parts[1]);
                ip = packet.getAddress();
                break;
            case Messages.DISCONNECT:
                break;
            case Messages.CREATE_LEVEL:
                sendMessage(Messages.INIT_LEVEL + SP_C + MapConverter.convertToString(RoomMap.map));
                break;
            case Messages.INIT_LEVEL:
                isSendingData = true;
                GameData.networkListener.initializeLevel(MapConverter.convertToMap(parts[1]));
                break;
            case Messages.CREATE_ENTITY:
                isSendingData = true;
                GameData.networkListener.createEntity(GameEntity.parseEntity(parts[1]));
                break;
            case Messages.POSITION:
                isSendingData = true;
                GameData.networkListener.moveEntity(Integer.parseInt(parts[1]), Float.parseFloat(parts[2]), Float.parseFloat(parts[3]), Direction.parseDirection(parts[4]));
                break;
            case Messages.SHOOT:
                isSendingData = true;
                GameData.networkListener.createShoot(Integer.parseInt(parts[1]), Direction.parseDirection(parts[2]));
                break;
            case Messages.HP:
                isSendingData = true;
                GameData.networkListener.updateHp(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
                break;
            case Messages.ROOM_CHANGED:
                isSendingData = true;
                GameData.networkListener.changeRoom(Direction.parseDirection(parts[1]));
                break;
            case Messages.START_GAME:
                Render.startGame = true;
                break;
            case Messages.RESTART_GAME:
                isSendingData = true;
                Game.restart();
                break;
            case Messages.END_GAME:
                isSendingData = true;
                sendMessage(Messages.DISCONNECT + SP_C + GameData.clientNumber);
                GameData.networkListener.endGame();
                break;
            default:
                throw new RuntimeException("Message not recognized: " + parts[0]);
        }

        isSendingData = false;
    }

    private void sendMessage(String message) {
        byte[] data = message.getBytes();
        DatagramPacket datagramPacket = new DatagramPacket(data, data.length, ip, PORT);
        try {
            socket.send(datagramPacket);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void end() {
        if (!isConnected()) {
            throw new RuntimeException("The client is not connected to the NET");
        }
        sendMessage(Messages.DISCONNECT + SP_C + GameData.clientNumber);
        end = true;
    }

    public boolean isConnected() {
        return GameData.clientNumber != GameData.NOT_CONNECTED;
    }

    public void updateEntityPosition(int entityId, float x, float y, Direction direction) {
        sendMessage(Messages.POSITION + SP_C + GameData.clientNumber + SP_C + entityId + SP_C + x + SP_C + y + SP_C + direction);
    }

    public void roomChanged(Direction direction) {
        sendMessage(Messages.ROOM_CHANGED + SP_C + GameData.clientNumber + SP_C + direction);
    }

    public void createShoot(int entityId, Direction direction) {
        sendMessage(Messages.SHOOT + SP_C + GameData.clientNumber + SP_C + entityId + SP_C + direction);
    }

    public void createEntity(GameEntity e) {
        sendMessage(Messages.CREATE_ENTITY + SP_C + GameData.clientNumber + SP_C + e.toString());
    }

    public void updateHp(int id, int hp) {
        sendMessage(Messages.HP + SP_C + GameData.clientNumber + SP_C + id + SP_C + hp);
    }

    public boolean isSendingData() {
        return isSendingData;
    }

    public void restart() {
        sendMessage(Messages.RESTART_GAME + SP_C + GameData.clientNumber);
    }

    /*
    private String getEncrypted(Object obj) {
        try {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            ObjectOutputStream objStream = new ObjectOutputStream(byteStream);
            objStream.writeObject(obj);
            objStream.flush();

            byte[] gameBytes = byteStream.toByteArray();

            return Base64.getEncoder().encodeToString(gameBytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T decrypt(String text) {
        try {
            byte[] gameBytes = Base64.getDecoder().decode(text);

            ByteArrayInputStream byteStream = new ByteArrayInputStream(gameBytes);
            ObjectInputStream objStream = new ObjectInputStream(byteStream);
            return (T) objStream.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    */
}
