package game.characters.playables;

import game.characters.Character;
import game.utilities.Controls;
import game.utilities.GameAction;
import utilities.Direction;

public abstract class Playable extends Character {
    public Playable(String texturePath, String bulletTexturePath) {
        super(texturePath, bulletTexturePath);
    }

    public void move() {
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
