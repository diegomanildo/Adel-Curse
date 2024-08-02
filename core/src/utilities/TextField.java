package utilities;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class TextField extends com.badlogic.gdx.scenes.scene2d.ui.TextField {
    public TextField(String text) {
        super(text, Render.skin);
    }

    public TextField() {
        this("");
    }

    public void addListener(Runnable action) {
        addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                action.run();
            }
        });
    }
}
