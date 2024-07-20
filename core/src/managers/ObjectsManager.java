package managers;

import gameUtilities.ObjectFunctions;

public class ObjectsManager {
    private static final int INIT_SIZE = 10;
    private static final int EXPAND_MULTIPLIER = 2;

    private ObjectFunctions[] objects;

    public ObjectsManager() {
        objects = new ObjectFunctions[INIT_SIZE];
    }

    public void register(ObjectFunctions newObject) {
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

    public void reset() {
        objects = new ObjectFunctions[INIT_SIZE];
    }

    private void expandArray() {
        ObjectFunctions[] newObjects = new ObjectFunctions[objects.length * EXPAND_MULTIPLIER];
        System.arraycopy(objects, 0, newObjects, 0, objects.length);
        objects = newObjects;
    }

    public void draw() {
        for (ObjectFunctions object : objects) {
            if (object != null) {
                object.draw();
            }
        }
    }

    public void dispose() {
        for (ObjectFunctions object : objects) {
            if (object != null) {
                object.dispose();
            }
        }
    }

    public boolean empty() {
        for (ObjectFunctions object : objects) {
            if (object != null) {
                return false;
            }
        }

        return true;
    }
}
