package game.net.threads;

import game.Game;
import game.net.GameData;
import game.net.Messages;
import game.utilities.Direction;
import utilities.Render;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Base64;

public class ClientThread extends Thread {
    public static final int PORT = 22121;
    public static final String SP_C = ServerThread.SP_C; // Special character

    private InetAddress ip;
    private final DatagramSocket socket;
    private boolean end;
    private boolean connected;

    public ClientThread() {
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
                handleConnection(parts[1], Integer.parseInt(parts[2]), packet.getAddress());
                break;
            case Messages.DISCONNECT:
                connected = false;
                break;
            case Messages.POSITION:
                GameData.networkListener.moveEntity(Integer.parseInt(parts[1]), Direction.parseDirection(parts[2]));
                break;
            case Messages.SHOOT:
                GameData.networkListener.shootEntity(Integer.parseInt(parts[1]), Direction.parseDirection(parts[2]));
                break;
            case Messages.ROOM_CHANGED:
                GameData.networkListener.changeRoom(Direction.parseDirection(parts[1]));
                break;
            case Messages.START_GAME:
                Render.startGame = true;
                break;
            case Messages.END_GAME:
                if (Game.deathScreen != null) {
                    Game.deathScreen.playerDead();
                    System.out.println("[Client " + GameData.clientNumber + "] Ended game");
                }
                break;
            default:
                throw new RuntimeException("Message not recognized: " + parts[0]);
        }
    }

    private void handleConnection(String state, int clientNumber, InetAddress ip) {
        this.ip = ip;
        if (state.equals(Messages.SUCCESSFUL)) {
            GameData.clientNumber = clientNumber + 1;
            connected = true;
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
        return connected;
    }

    public void updateEntityPosition(int entityId, Direction direction) {
        sendMessage(Messages.POSITION + SP_C + GameData.clientNumber + SP_C + entityId + SP_C + direction);
    }

    public void updateEntityShoot(int entityId, Direction direction) {
        sendMessage(Messages.SHOOT + SP_C + GameData.clientNumber + SP_C + entityId + SP_C + direction);
    }

    public void roomChanged(Direction direction) {
        sendMessage(Messages.ROOM_CHANGED + SP_C + GameData.clientNumber + SP_C + direction);
    }

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
}
