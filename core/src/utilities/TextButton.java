package utilities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import utilities.audio.Sound;

public final class TextButton extends com.badlogic.gdx.scenes.scene2d.ui.TextButton {
    private final Sound pressedSound;
    private final Sound hoveredSound;

    public TextButton(String text, Runnable action) {
        super(text, Render.skin);
        pressedSound = new Sound("Sfx", "clickSound.mp3");
        hoveredSound = new Sound("Sfx", "hoverSound.mp3");
        addChangeListener(action);
        addHoverListener();
    }

    public TextButton(String text) {
        this(text, () -> {});
    }

    public TextButton(Actor actor) {
        this("");
        setSize(actor.getWidth(), actor.getHeight());
        add(actor).center();
    }

    public void addChangeListener(Runnable action) {
        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                pressedSound.play();
                action.run();
            }
        });
    }

    private void addHoverListener() {
        addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                hoveredSound.play();
                getLabel().setColor(Color.YELLOW);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                getLabel().setColor(Color.WHITE);
            }
        });
    }
}