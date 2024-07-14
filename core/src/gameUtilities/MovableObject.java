package gameUtilities;

import utilities.Direction;
import utilities.exceptions.DirectionNotValidException;

public class MovableObject extends GameAnimation {
    private float velocity;

    public MovableObject(String texturePath, int columns, int rows, float frameDuration) {
        super(texturePath, columns, rows, frameDuration);
    }

    public MovableObject(String texturePath, float x, float y, int columns, int rows, float frameDuration) {
        super(texturePath, x, y, columns, rows, frameDuration);
    }

    public MovableObject(String texturePath, float x, float y, float width, float height, int columns, int rows, float frameDuration) {
        super(texturePath, x, y, width, height, columns, rows, frameDuration);
    }

    public float getVelocity() {
        return velocity;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    // Move character with a direction chosen
    protected void move(Direction direction) {
        int moveIndex;
        float x = getX();
        float y = getY();

        switch (direction) {
            case None:
                moveIndex = 0;
                break;
            case Down:
                moveIndex = 0;
                y -= velocity;
                break;
            case Up:
                moveIndex = 1;
                y += velocity;
                break;
            case Right:
                moveIndex = 2;
                x += velocity;
                break;
            case Left:
                moveIndex = 3;
                x -= velocity;
                break;
            default:
                throw new DirectionNotValidException("The direction " + direction + " is not valid");
        }

        setAnimation(moveIndex);
        setPosition(x, y);
    }
}
