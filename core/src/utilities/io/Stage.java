package utilities.io;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Stage extends com.badlogic.gdx.scenes.scene2d.Stage {
    private final boolean[] keysPressed = new boolean[256];

    public Stage() {
        super();
    }

	public Stage(Viewport viewport) {
        super();
    }

	public Stage(Viewport viewport, Batch batch) {
        super();
    }

    public OrthographicCamera getCamera() {
        return (OrthographicCamera) super.getCamera();
    }

    public boolean keyDown(int keycode) {
        if (isValidKey(keycode)) {
            keysPressed[keycode] = true;
        } else {
            throw new RuntimeException("Invalid key: \"" + keycode + "\"");
        }

        return super.keyDown(keycode);
    }

    public boolean keyUp(int keycode) {
        if (isValidKey(keycode)) {
            keysPressed[keycode] = false;
        } else {
            throw new RuntimeException("Invalid key: \"" + keycode + "\"");
        }

        return super.keyUp(keycode);
    }

    public boolean isValidKey(int keycode) {
        return keycode >= 0 && keycode < keysPressed.length;
    }

    public boolean isKeyPressed(int keycode) {
        final boolean validKey = isValidKey(keycode);

        if (validKey) {
            return keysPressed[keycode];
        }

        throw new RuntimeException("keycode");
    }

    public int getKeyPressed() {
        for (int i = 0; i < keysPressed.length; i++) {
            if (keysPressed[i]) {
                return i;
            }
        }

        return -1;
    }

    public boolean isRightPressed() {
        return Gdx.input.isButtonPressed(Input.Buttons.RIGHT);
    }

    public boolean isLeftPressed() {
        return Gdx.input.isButtonPressed(Input.Buttons.LEFT);
    }

    public boolean isAnyKeyPressed() {
        for (boolean b : keysPressed) {
            if (b) {
                return true;
            }
        }

        return false;
    }
}
