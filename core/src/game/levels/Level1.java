package game.levels;

import game.rooms.Room;
import game.rooms.ShopRoom;
import game.rooms.StoneRoom;
import game.utilities.Direction;
import utilities.Utils;

import java.util.ArrayList;

public final class Level1 extends Level {
    private final int roomsQuantity = 4;

    @Override
    protected ArrayList<Room> generateRooms() {
        ArrayList<Room> rooms = new ArrayList<>();

        for (int i = 0; i < roomsQuantity; i++) {
            Room room;

            int roomNumber = Utils.r.nextInt(0, 1);

            Direction direction = Direction.convert(Utils.r.nextInt(0, Direction.getMaxValidDirection().ordinal()));

            switch (roomNumber) {
                case 0:
                    room = new StoneRoom();
                    break;
                case 1:
                    room = new ShopRoom();
                    break;
                default:
                    throw new RuntimeException("Invalid room number: " + roomNumber);
            }

            switch (direction) {
                case UP:
                    room.setY(i * (room.getHeight() - Room.OFFSET * 2f));
                    break;
                case DOWN:
                    room.setY(-(i * (room.getHeight() - Room.OFFSET * 2f)));
                    break;
                case RIGHT:
                    room.setX(i * (room.getWidth() - Room.OFFSET * 2f));
                    break;
                case LEFT:
                    room.setX(-(i * (room.getWidth() - Room.OFFSET * 2f)));
                    break;
            }

            rooms.add(room);
        }

        return rooms;
    }
}
