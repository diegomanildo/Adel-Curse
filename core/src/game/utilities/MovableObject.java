package game.utilities;

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
            case NONE:
                moveIndex = 0;
                break;
            case DOWN:
                moveIndex = 0;
                y -= velocity;
                break;
            case UP:
                moveIndex = 1;
                y += velocity;
                break;
            case RIGHT:
                moveIndex = 2;
                x += velocity;
                break;
            case LEFT:
                moveIndex = 3;
                x -= velocity;
                break;
            case DOWN_RIGHT:
                moveIndex = 0;
                x += diagonalVelocity;
                y -= diagonalVelocity;
                break;
            case DOWN_LEFT:
                moveIndex = 0;
                x -= diagonalVelocity;
                y -= diagonalVelocity;
                break;
            case UP_RIGHT:
                moveIndex = 1;
                x += diagonalVelocity;
                y += diagonalVelocity;
                break;
            case UP_LEFT:
                moveIndex = 1;
                x -= diagonalVelocity;
                y += diagonalVelocity;
                break;
            default:
                throw new RuntimeException("The direction " + direction + " is not valid");
        }

        setAnimation(moveIndex);
        setPosition(x, y);
    }
}
