package utilities;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public final class TextButton extends com.badlogic.gdx.scenes.scene2d.ui.TextButton {
    public TextButton(String text) {
        super(text, Render.skin);
    }

    public TextButton(String text, ButtonAction action) {
        this(text);
        addChangeListener(action);
    }

    public void addChangeListener(ButtonAction action) {
        addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                action.onPressed();
            }
        });
    }
}