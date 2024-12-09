package game.net;

import game.Game;
import game.entities.GameEntity;
import game.map.MapHelper;
import game.map.RoomMap;
import game.net.utilities.Thread;
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
            } else {
                sendMessage(Messages.STILL_CONNECTED + SP_C + GameData.clientNumber);
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
        isSendingData = true;

        switch (parts[0]) {
            case Messages.CONNECT:
                GameData.clientNumber = Integer.parseInt(parts[1]);
                ip = packet.getAddress();
                break;
            case Messages.DISCONNECT:
                end = true;
                break;
            case Messages.START_GAME:
                Render.startGame = true;
                break;
            case Messages.RESTART_GAME:
                Game.restart();
                break;
            case Messages.END_GAME:
                if (GameData.networkListener != null) {
                    GameData.networkListener.endGame();
                }
                break;
            case Messages.CREATE_LEVEL:
                sendMessage(Messages.INIT_LEVEL + SP_C + MapHelper.convertToString(RoomMap.map));
                break;
            case Messages.INIT_LEVEL:
                if (GameData.networkListener != null) {
                    GameData.networkListener.initializeLevel(MapHelper.convertToMap(parts[1]));
                }
                break;
            case Messages.ROOM_CHANGED:
                if (GameData.networkListener != null) {
                    GameData.networkListener.changeRoom(Direction.parseDirection(parts[1]));
                }
                break;
            case Messages.POSITION:
                if (GameData.networkListener != null) {
                    GameData.networkListener.moveEntity(Integer.parseInt(parts[1]), Float.parseFloat(parts[2]), Float.parseFloat(parts[3]), Direction.parseDirection(parts[4]));
                }
                break;
            case Messages.SIZE:
                if (GameData.networkListener != null) {
                    GameData.networkListener.changeSizeEntity(Integer.parseInt(parts[1]), Float.parseFloat(parts[2]), Float.parseFloat(parts[3]));
                }
                break;
            case Messages.SHOOT:
                if (GameData.networkListener != null) {
                    GameData.networkListener.createShoot(Integer.parseInt(parts[1]), Direction.parseDirection(parts[2]));
                }
                break;
            case Messages.CREATE_ENTITY:
                if (GameData.networkListener != null) {
                    GameData.networkListener.createEntity(GameEntity.parseEntity(parts[1]));
                }
                break;
            case Messages.HP:
                if (GameData.networkListener != null) {
                    GameData.networkListener.updateHp(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
                }
                break;
            case Messages.MAX_HP:
                if (GameData.networkListener != null) {
                    GameData.networkListener.updateMaxHp(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
                }
                break;
            case Messages.DAMAGE:
                if (GameData.networkListener != null) {
                    GameData.networkListener.updateDamage(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
                }
                break;
            case Messages.ARMOR:
                if (GameData.networkListener != null) {
                    GameData.networkListener.updateArmor(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
                }
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

    public void restart() {
        sendMessage(Messages.RESTART_GAME + SP_C + GameData.clientNumber);
    }

    public void roomChanged(Direction direction) {
        sendMessage(Messages.ROOM_CHANGED + SP_C + GameData.clientNumber + SP_C + direction);
    }

    public void updateEntityPosition(int entityId, float x, float y, Direction direction) {
        sendMessage(Messages.POSITION + SP_C + GameData.clientNumber + SP_C + entityId + SP_C + x + SP_C + y + SP_C + direction);
    }

    public void updateEntitySize(int entityId, float width, float height) {
        sendMessage(Messages.SIZE + SP_C + GameData.clientNumber + SP_C + entityId + SP_C + width + SP_C + height);
    }

    public void createShoot(int entityId, Direction direction) {
        sendMessage(Messages.SHOOT + SP_C + GameData.clientNumber + SP_C + entityId + SP_C + direction);
    }

    public void createEntity(GameEntity e) {
        sendMessage(Messages.CREATE_ENTITY + SP_C + GameData.clientNumber + SP_C + e);
    }

    public void updateHp(int id, int hp) {
        sendMessage(Messages.HP + SP_C + GameData.clientNumber + SP_C + id + SP_C + hp);
    }

    public void updateMaxHp(int id, int maxHp) {
        sendMessage(Messages.MAX_HP + SP_C + GameData.clientNumber + SP_C + id + SP_C + maxHp);
    }

    public void updateDamage(int id, int damage) {
        sendMessage(Messages.DAMAGE + SP_C + GameData.clientNumber + SP_C + id + SP_C + damage);
    }

    public void updateArmor(int id, int armor) {
        sendMessage(Messages.ARMOR + SP_C + GameData.clientNumber + SP_C + id + SP_C + armor);
    }

    public boolean isConnected() {
        return GameData.clientNumber != GameData.NOT_CONNECTED;
    }

    public boolean isSendingData() {
        return isSendingData;
    }

    public void end() {
        if (!isConnected()) {
            throw new RuntimeException("The client is not connected to the NET");
        }
        sendMessage(Messages.DISCONNECT + SP_C + GameData.clientNumber);
        end = true;
    }
}
