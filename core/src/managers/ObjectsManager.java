package managers;

import gameUtilities.GameObject;

public class ObjectsManager extends Manager<GameObject> {
    public void draw() {
        objects.forEach(GameObject::draw);
    }
}
