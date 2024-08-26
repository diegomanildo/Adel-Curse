package utilities;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class Group extends com.badlogic.gdx.scenes.scene2d.Group {
    public void show() {
        Actor[] actors = getChildren().begin();
        for (int i = 0, n = getChildren().size; i < n; i++) {
            Actor actor = actors[i];

            if (actor instanceof utilities.Actor) {
                ((utilities.Actor) actor).show();
            }

            if (actor instanceof utilities.Group) {
                ((utilities.Group) actor).show();
            }
        }
        getChildren().end();
    }
}
