package gameUtilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import utilities.Direction;
import utilities.exceptions.DirectionNotValidException;

public class Bullet extends GameAnimation {
    private static final float SPEED = 500f;
    private final Direction bulletDirection;

    public Bullet(String texturePath, Direction bulletDirection, float width, float height, float frameDuration) {
        super(texturePath, 0f, 0f, width, height, 2, 4, frameDuration);
        this.bulletDirection = bulletDirection;
    }

    // Update bullet position
    public void update(float deltaTime) {
        float x = getX();
        float y = getY();

        switch (bulletDirection) {
            case Down:
                y -= SPEED * deltaTime;
                break;
            case Up:
                y += SPEED * deltaTime;
                break;
            case Right:
                x += SPEED * deltaTime;
                break;
            case Left:
                x -= SPEED * deltaTime;
                break;
            default:
                throw new DirectionNotValidException("The direction " + bulletDirection + " is not valid");
        }

        setPosition(x, y);
    }

    // Bullet is no more in the screen, and you should have not render it
    public boolean outOfBounds() {
        return getX() > Gdx.graphics.getWidth() || getX() < -100f || getY()  > Gdx.graphics.getHeight() || getY() < -100f;
    }
}
