package game.levels;

import game.levels.rooms.Room;

import java.util.ArrayList;

public abstract class Level {
    private final ArrayList<Room> rooms;

    public Level() {
        rooms = getRooms();
    }

    protected abstract ArrayList<Room> getRooms();
}
