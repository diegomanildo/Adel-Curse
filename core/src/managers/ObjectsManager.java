package managers;

import gameUtilities.ObjectFunctions;

public class ObjectsManager extends Manager<ObjectFunctions> {
    public void draw() {
        for (ObjectFunctions object : objects) {
            if (object != null) {
                object.draw();
            }
        }
    }
}
