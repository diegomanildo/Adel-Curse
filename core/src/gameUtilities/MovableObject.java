package gameUtilities;

import utilities.Direction;
import utilities.exceptions.DirectionNotValidException;

public abstract class MovableObject extends GameAnimation {
    private float velocity;

    public MovableObject(String texturePath, int columns, int rows, float frameDuration) {
        super(texturePath, columns, rows, frameDuration);
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
        float diagonalVelocity = (float) (velocity / Math.sqrt(2));

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
            case DownRight:
                moveIndex = 0;
                x += diagonalVelocity;
                y -= diagonalVelocity;
                break;
            case DownLeft:
                moveIndex = 0;
                x -= diagonalVelocity;
                y -= diagonalVelocity;
                break;
            case UpRight:
                moveIndex = 1;
                x += diagonalVelocity;
                y += diagonalVelocity;
                break;
            case UpLeft:
                moveIndex = 1;
                x -= diagonalVelocity;
                y += diagonalVelocity;
                break;
            default:
                throw new DirectionNotValidException("The direction " + direction + " is not valid");
        }

        setAnimation(moveIndex);
        setPosition(x, y);
    }
}
