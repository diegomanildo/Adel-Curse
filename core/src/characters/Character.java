package characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import utilities.FilePaths;
import gameUtilities.GameAnimation;

public abstract class Character extends GameAnimation {
    private static final float WIDTH = 150f;
    private static final float HEIGHT = WIDTH;
    private final float velocity;

    public Character(String texture, float x, float y) {
        super(FilePaths.CHARACTERS + texture, x, y, WIDTH, HEIGHT, 2, 4, 0.5f);
        velocity = WIDTH / 12;
    }
//ksdjskjdskjdlsdjfldskfjdslkfd
    public void move() {
        int moveIndex = 0;
        float x = getX();
        float y = getY();

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            moveIndex = 1;
            y += velocity;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            moveIndex = 2;
            x += velocity;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            moveIndex = 3;
            x -= velocity;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            y -= velocity;
        }

        setPosition(x, y);
        setAnimation(moveIndex);
    }
}
