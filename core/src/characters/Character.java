package characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import utilities.FilePaths;
import gameUtilities.GameAnimation;

public abstract class Character extends GameAnimation {
    private static final float WIDTH = 150f;
    private static final float HEIGHT = WIDTH;

    protected static final int DOWN = 0;
    protected static final int UP = 1;
    protected static final int RIGHT = 2;
    protected static final int LEFT = 3;

    private final float velocity;

    public Character(String texture, float x, float y) {
        super(FilePaths.CHARACTERS + texture, x, y, WIDTH, HEIGHT, 2, 4, 0.5f);
        velocity = WIDTH / 12;
    }

    public void move() {
        // Down is the default movement
        int moveIndex = DOWN;

        float x = getX();
        float y = getY();

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            moveIndex = UP;
            y += velocity;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            moveIndex = RIGHT;
            x += velocity;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            moveIndex = LEFT;
            x -= velocity;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            y -= velocity;
        }

        setPosition(x, y);
        setAnimation(moveIndex);
    }
}
