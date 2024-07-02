package gameUtilities;

import com.ac.Render;
import com.badlogic.gdx.graphics.g2d.Batch;

public abstract class GameObject {
    private float x;
    private float y;

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

    public void setPosition(float x, float y) {
        setX(x);

        setY(y);
    }

    public abstract void draw(Batch batch);

    public void draw() {
        draw(Render.batch);
    }
}
