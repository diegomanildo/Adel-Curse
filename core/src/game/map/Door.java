package game.map;

import game.entities.GameEntity;
import game.items.Item;
import game.utilities.Direction;
import game.utilities.Hitbox;

import java.util.ArrayList;

public class Door {
    private final Direction direction;
    private final Hitbox hitbox;
    private final float rotation;
    private Item item;

    public Door(Direction direction, Hitbox hitbox) {
        this.direction = direction;
        this.hitbox = hitbox;
        switch (direction) {
            case DOWN:
                rotation = 180f;
                break;
            case UP:
                rotation = 0f;
                break;
            case LEFT:
                rotation = 90f;
                break;
            case RIGHT:
                rotation = 270f;
                break;
            default:
                throw new RuntimeException("Invalid direction: " + direction);
        }
    }

    public Direction getDirection() {
        return direction;
    }

    public Hitbox getHitbox() {
        return hitbox;
    }

    public float getRotation() {
        return rotation;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public boolean hasItem() {
        return item != null;
    }

    private static final String DOOR_SPLIT_CHAR = "'";

    @Override
    public String toString() {
        return direction + DOOR_SPLIT_CHAR + hitbox + DOOR_SPLIT_CHAR + item;
    }

    public static Door parseDoor(String doorStr) {
        String[] parts = doorStr.split(DOOR_SPLIT_CHAR);
        Direction direction = Direction.parseDirection(parts[0]);
        Hitbox hitbox = Hitbox.parseHitbox(parts[1]);
        Item item = (Item) GameEntity.parseEntity(parts[2]);

        Door door = new Door(direction, hitbox);
        door.setItem(item);
        return door;
    }

    private static final String DOORS_SPLIT_CHAR = "@@";

    public static String toString(ArrayList<Door> doors) {
        StringBuilder doorsStr = new StringBuilder(doors.get(0).toString());

        for (int i = 1; i < doors.size(); i++) {
            doorsStr.append(DOORS_SPLIT_CHAR).append(doors.get(i).toString());
        }

        return doorsStr.toString();
    }

    public static ArrayList<Door> toDoors(String doorsStr) {
        ArrayList<Door> doors = new ArrayList<>();
        String[] doorsParts = doorsStr.split(DOORS_SPLIT_CHAR);

        for (String doorsPart : doorsParts) {
            Door door = parseDoor(doorsPart);
            doors.add(door);
        }

        return doors;
    }
}
