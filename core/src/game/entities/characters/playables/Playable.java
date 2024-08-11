package game.entities.characters.playables;

import game.entities.characters.Character;
import game.entities.items.Item;
import game.utilities.Controls;
import game.utilities.Direction;
import game.utilities.GameAction;

import java.util.ArrayList;

public abstract class Playable extends Character {
    public ArrayList<Item> itemList = new ArrayList<Item>();

    public Playable(String texturePath, String bulletTexturePath, int columns, int rows) {
        super(texturePath, bulletTexturePath, columns, rows);
    }

    public Playable(String texturePath, String bulletTexturePath) {
        super(texturePath, bulletTexturePath);
    }

    @Override
    public void update(float delta) {
        moveCharacter();
        shoot();
    }

    private void shoot() {
        Direction direction = Direction.NONE;

        boolean shootEnabled = true;

        if (Controls.isPressed(GameAction.SHOOT_UP)) {
            direction = Direction.UP;
        } else if (Controls.isPressed(GameAction.SHOOT_DOWN)) {
            direction = Direction.DOWN;
        } else if (Controls.isPressed(GameAction.SHOOT_RIGHT)) {
            direction = Direction.RIGHT;
        } else if (Controls.isPressed(GameAction.SHOOT_LEFT)) {
            direction = Direction.LEFT;
        } else {
            shootEnabled = false;
        }

        if (shootEnabled) {
            shoot(direction);
        }
    }

    private void moveCharacter() {
        Direction direction = Direction.NONE;

        if (Controls.isPressed(GameAction.UP)) {
            if (Controls.isPressed(GameAction.LEFT)) {
                direction = Direction.UP_LEFT;
            } else if (Controls.isPressed(GameAction.RIGHT)) {
                direction = Direction.UP_RIGHT;
            } else {
                direction = Direction.UP;
            }
        } else if (Controls.isPressed(GameAction.DOWN)) {
            if (Controls.isPressed(GameAction.LEFT)) {
                direction = Direction.DOWN_LEFT;
            } else if (Controls.isPressed(GameAction.RIGHT)) {
                direction = Direction.DOWN_RIGHT;
            } else {
                direction = Direction.DOWN;
            }
        } else if (Controls.isPressed(GameAction.RIGHT)) {
            direction = Direction.RIGHT;
        } else if (Controls.isPressed(GameAction.LEFT)) {
            direction = Direction.LEFT;
        }

        move(direction);
    }
}
