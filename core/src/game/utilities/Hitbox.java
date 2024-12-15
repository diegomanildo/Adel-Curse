package game.utilities;

import com.badlogic.gdx.graphics.Color;
import utilities.ShapeRenderer;

public final class Hitbox {
    private static final String SPLIT_CHAR = "¨¨";

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
        return x + SPLIT_CHAR + y + SPLIT_CHAR + width + SPLIT_CHAR + height;
    }

    public static Hitbox parseHitbox(String s) {
        String[] parts = s.split(SPLIT_CHAR);
        float x = Float.parseFloat(parts[0]);
        float y = Float.parseFloat(parts[1]);
        float w = Float.parseFloat(parts[2]);
        float h = Float.parseFloat(parts[3]);
        return new Hitbox(x, y, w, h);
    }

    public void drawShape(ShapeRenderer sr) {
        sr.begin();
        sr.setColor(Color.GREEN);
        sr.set(ShapeRenderer.ShapeType.Line);
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

    public boolean contains(float x, float y, float width, float height) {
        float right = x + width;
        float top = y + height;

        return x >= getLeft() && right <= getRight() &&
                y >= getBottom() && top <= getTop();
    }
}
