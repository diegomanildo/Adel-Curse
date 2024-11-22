package game.net.threads;


import game.net.GameData;
import game.net.Messages;
import utilities.Render;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClientThread extends Thread {
    public static final int PORT = 22121;
    public static final String SPECIAL_CHARACTER = ServerThread.SPECIAL_CHARACTER;

    private InetAddress ip;
    private final DatagramSocket socket;
    private boolean end;

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
            try {
                socket.receive(packet);
                processMessage(packet);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void processMessage(DatagramPacket packet) {
        String message = new String(packet.getData()).trim();
        String[] parts = message.split(SPECIAL_CHARACTER);

        switch (parts[0]) {
            case Messages.CONNECTION:
                handleConnection(parts[1], Integer.parseInt(parts[2]), packet.getAddress());
                break;
            case Messages.START_GAME:
                Render.startGame = true;
                break;
            case Messages.ENTITY:
                handleEntity(parts);
            case Messages.GAME_OVER:
                GameData.networkListener.gameOver();
            default:
                throw new RuntimeException("Message not recognized: " + parts[0]);
        }
    }

    private void handleConnection(String state, int clientNumber, InetAddress ip) {
        this.ip = ip;
        if (state.equals(Messages.SUCCESSFUL)) {
            GameData.clientNumber = clientNumber;
        }
    }

    private void handleEntity(String[] parts) {
        if (parts[1].equals(Messages.CHANGE)) {
            GameData.networkListener.moveEntity(Integer.parseInt(parts[2]), Float.parseFloat(parts[3]), Float.parseFloat(parts[4]));
        } else {
            throw new RuntimeException("Message not recognized: " + parts[1]);
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
        end = true;
        socket.close();
    }
}
