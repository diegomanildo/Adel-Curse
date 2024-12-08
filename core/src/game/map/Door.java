package game.map;

import game.entities.items.Item;
import game.utilities.Direction;
import game.utilities.Hitbox;

public class Door {
    private final Direction direction;
    private final Hitbox hitbox;
    private Item item;

    public Door(Direction direction, Hitbox hitbox) {
        this.direction = direction;
        this.hitbox = hitbox;
    }

    public Direction getDirection() {
        return direction;
    }

    public Hitbox getHitbox() {
        return hitbox;
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
