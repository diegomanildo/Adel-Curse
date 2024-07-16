package utilities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import utilities.io.Audio;

public class Button extends Text {
    private final ButtonAction action;
    private static final Audio ON_PRESSED_SOUND = new Audio("buttonClicked.mp3");
    private static final Audio MOUSE_HOVER = new Audio("mouseHover.mp3");

    private boolean previouslyHovered = false;

    public Button(Font font, String text, float x, float y, ButtonAction action) {
        super(font, text, x, y);
        this.action = action;
    }

    public Button(Font font, String text, ButtonAction action) {
        this(font, text, 0f, 0f, action);
    }

    public void execute() {
        ON_PRESSED_SOUND.play();
        action.onPressed();
    }

    public boolean isHovered() {
        float x = Render.io.getMouseX();
        float y = Render.io.getMouseY();

        return collidesIn(x, y);
    }

    @Override
    public void draw(Batch batch) {
        boolean hovered = isHovered();

        if (hovered) {
            font.setColor(Color.YELLOW);
            if (!previouslyHovered) {
                Button.MOUSE_HOVER.play();
                previouslyHovered = true;
            }
        } else {
            font.setColor(Color.WHITE);
            previouslyHovered = false;
        }

        if (Render.io.isLeftPressed() && hovered) {
            execute();
        }
        super.draw(batch);
    }
}