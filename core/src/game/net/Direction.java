package game.net;

import java.net.InetAddress;

public class Direction {
    private final InetAddress ip;
    private final int port;

    public Direction(InetAddress ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public InetAddress getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }
}
