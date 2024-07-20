package utilities.io;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import menu.Screen;
import utilities.exceptions.NotValidKeyException;

public class IOProcessor implements InputProcessor {
    private final boolean[] keysPressed;
    private int mouseX;
    private int mouseY;

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
        Vector3 worldCoordinates = Screen.camera.unproject(new Vector3(screenX, screenY, 0));
        mouseX = (int) worldCoordinates.x;
        mouseY = (int) worldCoordinates.y;
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

    public int getKeyPressed() {
        for (int i = 0; i < keysPressed.length; i++) {
            if (keysPressed[i]) {
                return i;
            }
        }

        return -1;
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
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
