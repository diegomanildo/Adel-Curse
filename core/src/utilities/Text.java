package utilities;

import com.badlogic.gdx.graphics.g2d.Batch;
import gameUtilities.GameObject;

public class Text extends GameObject {
    public Font font;
    private String text;

    public Text(Font font, String text, float x, float y) {
        super(x, y);
        this.font = font;
        this.font.setText(text);
        this.text = text;

        setSize(this.font.getWidth(), this.font.getHeight());
    }

    public Text(Font font, String text) {
        this(font, text, 0f, 0f);
    }

    public Text(Font font, float x, float y) {
        this(font, "" , x, y);
    }

    public Text(Font font) {
        this(font, "");
    }

    @Override
    public void draw(Batch batch) {
        font.draw(batch, text, getX(), getY());
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        font.setText(text);
        setSize(this.font.getWidth(), this.font.getHeight());
    }

    @Override
    public void drawHitbox() {
        drawHitbox(getX(), getY() - getHeight(), getMiddleX(), getMiddleY() - font.getHeight(), getWidth(), getHeight());
    }

    @Override
    public boolean collidesIn(float pointX, float pointY) {
        return pointX >= getX() && pointX <= getX() + getWidth() // verify on x
                && pointY >= getY() - getHeight() && pointY <= getY(); // verify on y
    }
}
