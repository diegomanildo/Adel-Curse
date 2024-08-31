package game.utilities;

import com.badlogic.gdx.graphics.Color;
import utilities.ShapeRenderer;

public final class Hitbox {
    public float x;
    public float y;
    public float width;
    public float height;

    public Hitbox(float x, float y, float width, float height) {
        setPosition(x, y);
        setSize(width, height);
    }

    public Hitbox() {
        this(0f, 0f, 0f, 0f);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public void setBounds(float x, float y, float width, float height) {
        setPosition(x, y);
        setSize(width, height);
    }

    @Override
    public String toString() {
        return "[x=" + x + ", y=" + y + ", width=" + width + ", height=" + height + "]";
    }

    public void drawShape(ShapeRenderer sr) {
        sr.begin();
        sr.set(ShapeRenderer.ShapeType.Line);
        sr.setColor(Color.WHITE);
        sr.rect(x, y, width, height);
        sr.end();
    }

    public float getLeft() {
        return x;
    }

    public float getRight() {
        return getLeft() + width;
    }

    public float getBottom() {
        return y;
    }

    public float getTop() {
        return getBottom() + height;
    }

    public boolean collidesWith(Hitbox other) {
        return x < other.x + other.width &&
                x + width > other.x &&
                y < other.y + other.height &&
                y + height > other.y;
    }
}
