package game.utilities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;
import utilities.Render;

public abstract class GameObject implements Hitbox, Disposable, Drawable {
    private float x;
    private float y;
    private float width;
    private float height;

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

    public boolean isShowingHitbox() {
        return Render.isDebugging();
    }

    public boolean collidesWith(Rectangle rect) {
        return getX() < rect.x + rect.width &&
               getX() + getWidth() > rect.x &&
               getY() < rect.y + rect.height &&
               getY() + getHeight() > rect.y;
    }

    public boolean collidesWith(GameObject other) {
        return collidesWith(new Rectangle(other.getX(), other.getY(), other.getWidth(), other.getHeight()));
    }

    public abstract void draw(Batch batch);

    @Override
    public void draw() {
        draw(Render.b);
        if (isShowingHitbox()) {
            drawHitbox();
        }
    }

    protected void drawHitbox() {
        drawHitbox(Color.GREEN);
    }

    protected void drawHitbox(Color color) {
        drawHitbox(getX(), getY(), getMiddleX(), getMiddleY(), getWidth(), getHeight(), color);
    }

    public void dispose() {}
}
