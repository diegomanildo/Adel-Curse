package game.utilities;

import com.badlogic.gdx.Gdx;

public abstract class MovableObject extends GameAnimation {
    private float velocity;
    protected Direction direction = Direction.NONE;

    public MovableObject(String texturePath, int columns, int rows, float frameDuration) {
        super(texturePath, columns, rows, frameDuration);
    }

    // Move character with a direction chosen
    public void move(Direction direction) {
        this.direction = direction;
        float x = getX();
        float y = getY();
        float velocity = getVelocity() * Gdx.graphics.getDeltaTime();
        float diagonalVelocity = (float) (velocity / Math.sqrt(2));

        switch (direction) {
            case NONE:
                break;
            case DOWN:
                y -= velocity;
                break;
            case UP:
                y += velocity;
                break;
            case RIGHT:
                x += velocity;
                break;
            case LEFT:
                x -= velocity;
                break;
            case DOWN_RIGHT:
                x += diagonalVelocity;
                y -= diagonalVelocity;
                break;
            case DOWN_LEFT:
                x -= diagonalVelocity;
                y -= diagonalVelocity;
                break;
            case UP_RIGHT:
                x += diagonalVelocity;
                y += diagonalVelocity;
                break;
            case UP_LEFT:
                x -= diagonalVelocity;
                y += diagonalVelocity;
                break;
            default:
                throw new RuntimeException("The direction " + direction + " is not valid");
        }

        setAnimation(getMoveIndex(direction));
        setPosition(x, y);
    }

    private int getMoveIndex(Direction direction) {
        switch (direction) {
            case NONE:
            case DOWN:
            case DOWN_RIGHT:
            case DOWN_LEFT:
                return 0;
            case UP:
            case UP_RIGHT:
            case UP_LEFT:
                return 1;
            case RIGHT:
                return 2;
            case LEFT:
                return 3;
            default:
                throw new RuntimeException("The direction " + direction + " is not valid");
        }
    }

    public float getVelocity() {
        return velocity;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
        setAnimation(getMoveIndex(direction));
    }
}
