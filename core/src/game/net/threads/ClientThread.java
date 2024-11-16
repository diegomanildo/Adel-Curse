package game.net.threads;


import utilities.Render;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClientThread extends Thread {
    public static final int PORT = 22121;
    public static final String CONNECT_MESSAGE = "Connect";

    private InetAddress ip;
    private final DatagramSocket connexion;
    private boolean end;

    public ClientThread() {
        try {
            ip = InetAddress.getByName("255.255.255.255");
            connexion = new DatagramSocket();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        sendMessage(CONNECT_MESSAGE);
    }

    private void sendMessage(String message) {
        byte[] data = message.getBytes();
        DatagramPacket datagramPacket = new DatagramPacket(data, data.length, ip, PORT);
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
        if (message.equals(ServerThread.OK_MESSAGE)) {
            ip = packet.getAddress();
        } else if (message.equals(ServerThread.START_MESSAGE)) {
            Render.startGame = true;
        }
    }
}
