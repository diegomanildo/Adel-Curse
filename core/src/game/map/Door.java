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
}
