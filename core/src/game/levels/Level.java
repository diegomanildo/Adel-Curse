package game.levels;

import game.rooms.Room;
import game.utilities.Drawable;
import game.utilities.GameObject;

import java.util.ArrayList;

public abstract class Level implements Drawable {
    private final ArrayList<Room> rooms;

    public Level() {
        rooms = generateRooms();
    }

    protected abstract ArrayList<Room> generateRooms();

    @Override
    public void draw() {
        rooms.forEach(GameObject::draw);
    }

    @Override
    public void dispose() {
        rooms.forEach(Room::dispose);
    }

    public float getInitX() {
        return rooms.get(0).getMiddleX();
    }

    public float getInitY() {
        return rooms.get(0).getMiddleY();
    }
}
