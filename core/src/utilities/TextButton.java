package utilities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import utilities.io.Sound;

public final class TextButton extends com.badlogic.gdx.scenes.scene2d.ui.TextButton {
    private final Sound pressedSound;
    private final Sound hoveredSound;

    public TextButton(String text, Action action) {
        super(text, Render.skin);
        pressedSound = new Sound("Sfx", "clickSound.mp3");
        hoveredSound = new Sound("Sfx", "hoverSound.mp3");
        addChangeListener(action);
        addHoverListener();
    }

    public TextButton(String text) {
        this(text, () -> {});
    }

    public void addChangeListener(Action action) {
        addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                pressedSound.play();
                action.execute();
            }
        });
    }

    private void addHoverListener() {
        addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                hoveredSound.play();
                getLabel().setColor(Color.YELLOW);
                super.enter(event, x, y, pointer, fromActor);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                getLabel().setColor(Color.WHITE);
                super.exit(event, x, y, pointer, toActor);
            }
        });
    }
}