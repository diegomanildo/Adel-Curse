package game.net;

import utilities.Timer;

import java.net.InetAddress;

public class ClientData {
    private final InetAddress ip;
    private final int port;
    private final int id;
    private final Timer timer;

    public ClientData(InetAddress ip, int port, int id) {
        this.ip = ip;
        this.port = port;
        this.id = id;
        this.timer = new Timer();
    }

    public InetAddress getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public int getId() {
        return id;
    }

    public Timer getTimer() {
        return timer;
    }
}