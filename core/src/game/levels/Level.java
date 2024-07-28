package game.levels;

import game.levels.rooms.Room;

import java.util.ArrayList;

public abstract class Level {
    protected abstract ArrayList<Room> getRooms();

    private final ArrayList<Room> rooms;

    public Level() {
        rooms = getRooms();
    }
}
