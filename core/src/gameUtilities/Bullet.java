package gameUtilities;

import com.badlogic.gdx.Gdx;
import utilities.Direction;
import utilities.exceptions.DirectionNotValidException;

public class Bullet extends GameAnimation {
    private static final float SPEED = 500f;
    private final Direction bulletDirection;

    public Bullet(String texturePath, Direction bulletDirection, float x, float y, float width, float height, float frameDuration) {
        super(texturePath, x, y, width, height, 2, 4, frameDuration);
        this.bulletDirection = bulletDirection;
    }

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

    public boolean outOfBounds() {
        return getX() > Gdx.graphics.getWidth() || getY()  > Gdx.graphics.getHeight();
    }
}
