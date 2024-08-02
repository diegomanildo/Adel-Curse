package utilities;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class Slider extends com.badlogic.gdx.scenes.scene2d.ui.Slider {
    public Slider(float min, float max, float stepSize, boolean vertical) {
        super(min, max, stepSize, vertical, Render.skin);
    }

    public void addListener(Runnable action) {
        addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                action.run();
            }
        });
    }
}
