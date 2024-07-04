package gameUtilities;

import utilities.Render;
import com.badlogic.gdx.graphics.g2d.Batch;

public abstract class GameObject {
    private float x;
    private float y;
    private float width;
    private float height;

    protected GameObject(float x, float y) {
        setPosition(x, y);
    }

    protected GameObject() {
        this(0f, 0f);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setPosition(float x, float y) {
        setX(x);
        setY(y);
    }

    public void setSize(float width, float height) {
        setWidth(width);
        setHeight(height);
    }

    public boolean isInBounds(float pointX, float pointY) {
        return pointX >= x && pointX <= x + width
            && pointY >= y && pointY <= y + height;
    }

    public abstract void draw(Batch batch);

    public void draw() {
        draw(Render.b);
    }
}
