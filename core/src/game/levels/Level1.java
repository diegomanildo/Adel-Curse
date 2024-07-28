package game.levels;

import game.levels.rooms.Room;
import game.levels.rooms.StoneRoom;

import java.util.ArrayList;

public final class Level1 extends Level {
    private final int roomsQuantity = 7;

    @Override
    protected ArrayList<Room> generateRooms() {
        ArrayList<Room> rooms = new ArrayList<>();

        for (int i = 0; i < roomsQuantity; i++) {
            Room room = new StoneRoom();

            room.setPosition(0, i * room.getHeight() - Room.OFFSET * 2f);

            rooms.add(room);
        }

        return rooms;
    }
}
