package game.utilities;

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

    @Override
    public String toString() {
        return "[x=" + x + ", y=" + y + ", width=" + width + ", height=" + height + "]";
    }
}
