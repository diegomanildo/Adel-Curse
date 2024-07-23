package game.utilities;

import com.badlogic.gdx.graphics.Camera;
import game.GameScreen;
import utilities.Direction;
import utilities.exceptions.DirectionNotValidException;

public final class Bullet extends MovableObject {
    private static final float PIXELS_DELAY = 100f;

    private final Direction direction;

    public Bullet(String texturePath, Direction direction, float frameDuration) {
        super(texturePath, 2, 4, frameDuration);
        this.direction = direction;
        setVelocity(500f);
    }

    // Update bullet position
    public void update(float deltaTime) {
        float x = getX();
        float y = getY();
        float velocity = getVelocity();

        switch (direction) {
            case Down:
                y -= velocity * deltaTime;
                break;
            case Up:
                y += velocity * deltaTime;
                break;
            case Right:
                x += velocity * deltaTime;
                break;
            case Left:
                x -= velocity * deltaTime;
                break;
            default:
                throw new DirectionNotValidException("The direction " + direction + " is not valid");
        }

        setPosition(x, y);
    }

    // Bullet is no more in the screen, and you should have not render it
    public boolean outOfBounds() {
        Camera2D camera = GameScreen.getCamera();
        return (getX() < camera.getLeft() - PIXELS_DELAY || getX() > camera.getRight() + PIXELS_DELAY
                || getY() < camera.getBottom() - PIXELS_DELAY || getY() > camera.getTop() + PIXELS_DELAY);
    }

    public Direction getDirection() {
        return direction;
    }
}
