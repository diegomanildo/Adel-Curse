package gameUtilities;

import com.badlogic.gdx.Input.Keys;

public class Controls {
    private static final Control[] CONTROLS = new Control[] {
            new Control(CAction.UP, Keys.W),
            new Control(CAction.DOWN, Keys.S),
            new Control(CAction.LEFT, Keys.A),
            new Control(CAction.RIGHT, Keys.D),

            new Control(CAction.SHOOT_UP, Keys.UP),
            new Control(CAction.SHOOT_DOWN, Keys.DOWN),
            new Control(CAction.SHOOT_LEFT, Keys.LEFT),
            new Control(CAction.SHOOT_RIGHT, Keys.RIGHT)
    };

    public static int size() {
        return CONTROLS.length;
    }

    public static int get(CAction action) {
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

    public static void set(CAction action, int value) {
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

        CONTROLS[index] = new Control(action, value);
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
