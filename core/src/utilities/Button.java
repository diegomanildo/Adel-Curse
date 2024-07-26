package utilities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import utilities.io.Sound;

public final class Button extends Text {
    private static final float OFFSET = 5f;

    private final Sound onPressedSound;
    private final Sound mouseHovered;
    private final ButtonAction action;

    private boolean previouslyHovered = false;
    private boolean showBackground;

    public Button(Font font, String text, ButtonAction action) {
        super(font, text);
        this.action = action;
        showBackground = true;
        onPressedSound = new Sound("Sfx", "button/buttonClicked.mp3");
        mouseHovered = new Sound("Sfx", "button/mouseHover.mp3");
    }

    public Button(Font font, String text) {
        this(font, text, () -> {});
    }

    public boolean isShowBackground() {
        return showBackground;
    }

    public void showBackground(boolean showBackground) {
        this.showBackground = showBackground;
    }

    public void execute() {
        onPressedSound.play();
        action.onPressed();
    }

    public boolean isHovered() {
        return collidesIn(Render.io.getMouseX(), Render.io.getMouseY());
    }

    public boolean isClicked() {
        return Render.io.isLeftPressed() && isHovered();
    }

    @Override
    public void draw(Batch batch) {
        if (isHovered()) {
            Render.sr.setColor(Color.DARK_GRAY);
            setTextColor(Color.YELLOW);
            if (!previouslyHovered) {
                mouseHovered.play();
                previouslyHovered = true;
            }
        } else {
            Render.sr.setColor(Color.GRAY);
            setTextColor(Color.WHITE);
            previouslyHovered = false;
        }

        if (showBackground) {
            batch.end();
            float x = getX() - OFFSET;
            float y = getY() - getHeight() - OFFSET;
            float width = getWidth() + (OFFSET * 2f);
            float height = getHeight() + (OFFSET * 2f);

            Render.sr.begin(ShapeRenderer.ShapeType.Filled);
            Render.sr.rect(x, y, width, height);
            Render.sr.end();

            Render.sr.begin(ShapeRenderer.ShapeType.Line);
            Render.sr.setColor(Color.WHITE);
            Render.sr.rect(x, y, width, height);
            Render.sr.end();
            batch.begin();
        }

        super.draw(batch);
        setColor(Color.WHITE);
    }

    private void setTextColor(Color color) {
        setColor(color);
    }

    public void update() {
        if (isClicked()) {
            execute();
        }
    }
}