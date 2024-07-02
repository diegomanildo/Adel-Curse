package utilities.io;

import com.badlogic.gdx.InputProcessor;
import utilities.exceptions.NotValidKeyException;

public class IOProcessor implements InputProcessor {
    private boolean[] keysPressed;

    public IOProcessor() {
        keysPressed = new boolean[256];
    }

    @Override
    public boolean keyDown(int keycode) {
        if (isValidKey(keycode)) {
            keysPressed[keycode] = true;
            return true;
        }
        throw new NotValidKeyException(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        if (isValidKey(keycode)) {
            keysPressed[keycode] = false;
            return true;
        }
        throw new NotValidKeyException(keycode);
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }

    public boolean isValidKey(int keycode) {
        return keycode >= 0 && keycode < keysPressed.length;
    }

    public boolean isKeyPressed(int keycode) {
        final boolean validKey = isValidKey(keycode);

        if (validKey) {
            return keysPressed[keycode];
        }

        throw new NotValidKeyException(keycode);
    }
}
