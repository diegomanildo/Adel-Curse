package managers;

import gameUtilities.GameObject;

public class ObjectsManager {
    private static final int INIT_SIZE = 10;
    private static final int EXPAND_MULTIPLIER = 2;

    private static GameObject[] objects = new GameObject[INIT_SIZE];

    public static void register(GameObject newObject) {
        int i = 0;
        boolean registered = false;

        while (i < objects.length && !registered) {
            if (objects[i] == null) {
                objects[i] = newObject;
                registered = true;
            }
            i++;
        }

        if (!registered) {
            expandArray();
            register(newObject);
        }
    }

    public static void reset() {
        objects = new GameObject[INIT_SIZE];
    }

    private static void expandArray() {
        GameObject[] newObjects = new GameObject[objects.length * EXPAND_MULTIPLIER];
        System.arraycopy(objects, 0, newObjects, 0, objects.length);
        objects = newObjects;
    }

    public static void draw() {
        for (GameObject object : objects) {
            if (object != null) {
                object.draw();
            }
        }
    }

    public static void dispose() {
        for (GameObject object : objects) {
            if (object != null) {
                object.dispose();
            }
        }
    }
}
