package game.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Preferences;

public class Controls {
    private static final String PREFS_NAME = "game_controls";
    private static final Preferences PREFS = Gdx.app.getPreferences(PREFS_NAME);

    private static final Control[] CONTROLS = new Control[] {
            new Control(GameAction.UP, loadKey(GameAction.UP, Keys.W)),
            new Control(GameAction.DOWN, loadKey(GameAction.DOWN, Keys.S)),
            new Control(GameAction.LEFT, loadKey(GameAction.LEFT, Keys.A)),
            new Control(GameAction.RIGHT, loadKey(GameAction.RIGHT, Keys.D)),

            new Control(GameAction.SHOOT_UP, loadKey(GameAction.SHOOT_UP, Keys.UP)),
            new Control(GameAction.SHOOT_DOWN, loadKey(GameAction.SHOOT_DOWN, Keys.DOWN)),
            new Control(GameAction.SHOOT_LEFT, loadKey(GameAction.SHOOT_LEFT, Keys.LEFT)),
            new Control(GameAction.SHOOT_RIGHT, loadKey(GameAction.SHOOT_RIGHT, Keys.RIGHT))
    };

    private static int loadKey(GameAction action, int defaultKey) {
        return PREFS.getInteger(action.name(), defaultKey);
    }

    private static void saveKey(GameAction action, int key) {
        PREFS.putInteger(action.name(), key);
        PREFS.flush(); // Guarda inmediatamente las preferencias
    }

    public static int size() {
        return CONTROLS.length;
    }

    public static int get(GameAction action) {
        int i = 0;
        int index = -1;

        do {
            if (CONTROLS[i].getAction() == action) {
                index = i;
            }
        } while (++i < CONTROLS.length && index == -1);

        if (index == -1) {
            throw new RuntimeException("Action not found: " + action);
        }

        return CONTROLS[index].getKey();
    }

    public static Control at(int i) {
        return CONTROLS[i];
    }

    public static void set(GameAction action, int newKey) {
        int i = 0;
        int index = -1;

        do {
            if (CONTROLS[i].getAction() == action) {
                index = i;
            }
        } while (++i < CONTROLS.length && index == -1);

        if (index == -1) {
            throw new RuntimeException("Action not found: " + action);
        }

        CONTROLS[index].setKey(newKey);
        saveKey(action, newKey); // Guardar la nueva configuración en Preferences
    }

    public static boolean exists(int keyPressed) {
        int i = 0;
        int index = -1;

        do {
            if (CONTROLS[i].getKey() == keyPressed) {
                index = i;
            }
        } while (++i < CONTROLS.length && index == -1);

        return index != -1;
    }

    public static boolean isPressed(GameAction action) {
        return Gdx.input.isKeyPressed(get(action));
    }
}
