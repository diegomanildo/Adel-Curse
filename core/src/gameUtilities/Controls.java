package gameUtilities;

import com.badlogic.gdx.Input.Keys;

public class Controls {
    private static final Control[] CONTROLS = new Control[] {
            new Control(GameAction.UP, Keys.W),
            new Control(GameAction.DOWN, Keys.S),
            new Control(GameAction.LEFT, Keys.A),
            new Control(GameAction.RIGHT, Keys.D),

            new Control(GameAction.SHOOT_UP, Keys.UP),
            new Control(GameAction.SHOOT_DOWN, Keys.DOWN),
            new Control(GameAction.SHOOT_LEFT, Keys.LEFT),
            new Control(GameAction.SHOOT_RIGHT, Keys.RIGHT)
    };

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
}
