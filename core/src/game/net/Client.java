package game.net;

import java.net.InetAddress;

public class Client {
    private final InetAddress ip;
    private final int port;
    private final int number;

    public Client(InetAddress ip, int port, int number) {
        this.ip = ip;
        this.port = port;
        this.number = number;
    }

    public InetAddress getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public int getNumber() {
        return number;
    }
}
