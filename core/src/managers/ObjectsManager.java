package managers;

import game.utilities.GameObject;
import utilities.Render;

public class ObjectsManager extends Manager<GameObject> {
    public void draw() {
        Render.b.begin();
        objects.forEach(GameObject::draw);
        Render.b.end();
    }
}
