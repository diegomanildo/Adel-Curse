package game.map;

import game.entities.items.Item;
import game.utilities.Direction;
import game.utilities.Hitbox;

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
}
