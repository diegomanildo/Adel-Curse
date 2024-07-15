package gameUtilities;

import utilities.Direction;
import utilities.Render;
import utilities.exceptions.DirectionNotValidException;

public final class Bullet extends MovableObject {
    private static final float PIXELS_DELAY = 100f;

    private final Direction bulletDirection;

    public Bullet(String texturePath, Direction bulletDirection, float width, float height, float frameDuration) {
        super(texturePath, 0f, 0f, width, height, 2, 4, frameDuration);
        this.bulletDirection = bulletDirection;
        setVelocity(1000f);
    }

    // Update bullet position
    public void update(float deltaTime) {
        float x = getX();
        float y = getY();
        float velocity = getVelocity();

        switch (bulletDirection) {
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
                throw new DirectionNotValidException("The direction " + bulletDirection + " is not valid");
        }

        setPosition(x, y);
    }

    // Bullet is no more in the screen, and you should have not render it
    public boolean outOfBounds() {
        return getX() > Render.screenSize.width + PIXELS_DELAY || getX() < -PIXELS_DELAY
            || getY()  > Render.screenSize.height + PIXELS_DELAY || getY() < -PIXELS_DELAY;
    }
}
