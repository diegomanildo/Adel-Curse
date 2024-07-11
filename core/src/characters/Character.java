package characters;

import utilities.Direction;
import utilities.FilePaths;
import gameUtilities.GameAnimation;
import utilities.exceptions.DirectionNotValidException;

public abstract class Character extends GameAnimation {
    private static final float WIDTH = 150f;
    private static final float HEIGHT = WIDTH;
    private final float velocity;

    public Character(String texture, float x, float y) {
        super(FilePaths.CHARACTERS + texture, x, y, WIDTH, HEIGHT, 2, 8, 0.5f);
        velocity = WIDTH / 12;
    }

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

    protected void shoot(Direction direction) {
        int moveIndex;

        switch (direction) {
            case None:
                moveIndex = 0;
                break;
            case Down:
                moveIndex = 4;
                break;
            case Up:
                moveIndex = 5;
                break;
            case Right:
                moveIndex = 6;
                break;
            case Left:
                moveIndex = 7;
                break;
            default:
                throw new DirectionNotValidException("The direction " + direction + " is not valid");
        }

        setAnimation(moveIndex);
    }
}
