package game.net;

import java.net.InetAddress;

public class Client {
    private final InetAddress ip;
    private final int port;
    private final int id;

    public Client(InetAddress ip, int port, int id) {
        this.ip = ip;
        this.port = port;
        this.id = id;
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
}