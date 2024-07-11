package characters;

import com.badlogic.gdx.Input;
import utilities.Direction;
import utilities.Render;

public abstract class CharacterController extends Character {
    public CharacterController(String texture, float x, float y) {
        super(texture, x, y);
    }

    public void move() {
        moveCharacter();
        shoot();
    }

    private void shoot() {
        Direction direction = Direction.None;

        boolean shootEnabled = true;

        if (Render.io.isKeyPressed(Input.Keys.UP)) {
            direction = Direction.Up;
        } else if (Render.io.isKeyPressed(Input.Keys.DOWN)) {
            direction = Direction.Down;
        } else if (Render.io.isKeyPressed(Input.Keys.RIGHT)) {
            direction = Direction.Right;
        } else if (Render.io.isKeyPressed(Input.Keys.LEFT)) {
            direction = Direction.Left;
        } else {
            shootEnabled = false;
        }

        if (shootEnabled) {
            shoot(direction);
        }
    }

    private void moveCharacter() {
        Direction direction = Direction.None;

        if (Render.io.isKeyPressed(Input.Keys.W)) {
            direction = Direction.Up;
        } else if (Render.io.isKeyPressed(Input.Keys.S)) {
            direction = Direction.Down;
        } else if (Render.io.isKeyPressed(Input.Keys.D)) {
            direction = Direction.Right;
        } else if (Render.io.isKeyPressed(Input.Keys.A)) {
            direction = Direction.Left;
        }

        move(direction);
    }
}