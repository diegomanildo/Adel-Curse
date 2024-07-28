package managers;

import game.utilities.Drawable;
import utilities.Render;

public final class ObjectsManager extends Manager<Drawable> {
    public void draw() {
        Render.b.begin();
        objects.forEach(o -> o.draw());
        Render.b.end();
    }
}
