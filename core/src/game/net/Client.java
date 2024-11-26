package game.net;

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
    private boolean connected;

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

        while (!end) {
            DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
            if (!connected) {
                sendMessage(Messages.CONNECT);
            }
            try {
                socket.receive(packet);
                processMessage(packet);
            } catch (Exception e) {
                throw new RuntimeException(e);
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
                connected = true;
                ip = packet.getAddress();
                break;
            case Messages.DISCONNECT:
                GameData.clientNumber = -1;
                connected = false;
                break;
            case Messages.CREATE_LEVEL:
                sendMessage(Messages.INIT_LEVEL + SP_C + MapConverter.convertToString(RoomMap.map));
                break;
            case Messages.INIT_LEVEL:
                GameData.networkListener.initializeLevel(MapConverter.convertToMap(parts[1]));
                break;
            case Messages.POSITION:
                GameData.networkListener.moveEntity(Integer.parseInt(parts[1]), Float.parseFloat(parts[2]), Float.parseFloat(parts[3]));
                break;
            case Messages.SHOOT:
                GameData.networkListener.createShoot(Integer.parseInt(parts[1]), Direction.parseDirection(parts[2]));
                break;
            case Messages.ROOM_CHANGED:
                GameData.networkListener.changeRoom(Direction.parseDirection(parts[1]));
                break;
            case Messages.START_GAME:
                Render.startGame = true;
                break;
            case Messages.END_GAME:
                GameData.networkListener.endGame();
                break;
            default:
                throw new RuntimeException("Message not recognized: " + parts[0]);
        }
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
        sendMessage(Messages.DISCONNECT + SP_C + GameData.clientNumber);
        end = true;
        connected = false;
    }

    public boolean isConnected() {
        return GameData.clientNumber != GameData.NOT_CONNECTED;
    }

    public void updateEntityPosition(int entityId, float x, float y) {
        sendMessage(Messages.POSITION + SP_C + GameData.clientNumber + SP_C + entityId + SP_C + x + SP_C + y);
    }

    public void roomChanged(Direction direction) {
        sendMessage(Messages.ROOM_CHANGED + SP_C + GameData.clientNumber + SP_C + direction);
    }

    public void createShoot(int entityId, Direction direction) {
        sendMessage(Messages.SHOOT + SP_C + GameData.clientNumber + SP_C + entityId + SP_C + direction);
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
