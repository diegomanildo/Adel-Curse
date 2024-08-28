package utilities;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import utilities.audio.Sound;

public class CheckBox extends com.badlogic.gdx.scenes.scene2d.ui.CheckBox {
    private final Sound pressedSound;

    public CheckBox(String text) {
        super(text, Render.skin);
        pressedSound = new Sound("Sfx", "clickSound.mp3");
        addChangeListener(() -> {});
    }

    public void addChangeListener(Runnable action) {
        addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                pressedSound.play();
                action.run();
            }
        });
    }
}
