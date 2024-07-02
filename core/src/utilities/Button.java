package utilities;

import com.badlogic.gdx.graphics.g2d.Batch;
import gameUtilities.GameObject;

public class Button extends GameObject {
    public Font font;
    private String text;
    private ButtonAction action;

    public Button(Font font, String text, float x, float y, ButtonAction action) {
        super(x, y);
        this.font = font;
        this.text = text;
        this.action = action;
    }

    public Button(Font font, String text, ButtonAction action) {
        this(font, text, 0f, 0f, action);
    }

    @Override
    public void draw(Batch batch) {
        font.draw(batch, text, getX(), getY());
    }

    public void execute() {
        action.onPressed();
    }
}
