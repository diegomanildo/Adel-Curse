package utilities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import utilities.io.Audio;

public final class Button extends Text {
    private static final float OFFSET = 5f;
    private static final ShapeRenderer SR = new ShapeRenderer();

    private final Audio onPressedSound;
    private final Audio mouseHovered;
    private final ButtonAction action;

    private boolean previouslyHovered = false;
    private boolean showBackground;

    public Button(Font font, String text, ButtonAction action) {
        super(font, text);
        this.action = action;
        showBackground = true;
        onPressedSound = new Audio("buttonClicked.mp3");
        mouseHovered = new Audio("mouseHover.mp3");
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
        float x = Render.io.getMouseX();
        float y = Render.io.getMouseY();

        return collidesIn(x, y);
    }

    public boolean isClicked() {
        return Render.io.isLeftPressed() && isHovered();
    }

    @Override
    public void draw(Batch batch) {
        if (isHovered()) {
            SR.setColor(Color.DARK_GRAY);
            setTextColor(Color.YELLOW);
            if (!previouslyHovered) {
                mouseHovered.play();
                previouslyHovered = true;
            }
        } else {
            SR.setColor(Color.GRAY);
            setTextColor(Color.WHITE);
            previouslyHovered = false;
        }

        if (showBackground) {
            batch.end();
            float x = getX() - OFFSET;
            float y = getY() - getHeight() - OFFSET;
            float width = getWidth() + (OFFSET * 2f);
            float height = getHeight() + (OFFSET * 2f);

            SR.begin(ShapeRenderer.ShapeType.Filled);
            SR.rect(x, y, width, height);
            SR.end();

            SR.begin(ShapeRenderer.ShapeType.Line);
            SR.setColor(Color.WHITE);
            SR.rect(x, y, width, height);
            SR.end();
            batch.begin();
        }

        super.draw(batch);
    }

    private void setTextColor(Color color) {

    }

    public void update() {
        if (isClicked()) {
            execute();
        }
    }
}