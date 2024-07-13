package gameUtilities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import utilities.Render;
import com.badlogic.gdx.graphics.g2d.Batch;

public abstract class GameObject implements Hitbox {
    private float x;
    private float y;
    private float width;
    private float height;
    private boolean showHitbox = true;
    private static final ShapeRenderer SR = new ShapeRenderer();

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

    public boolean isShowingHitbox() {
        return showHitbox;
    }

    public void setShowHitbox(boolean showHitbox) {
        this.showHitbox = showHitbox;
    }

    public boolean collidesWith(GameObject other) {
        return collidesIn(other.getX(), other.getY());
    }

    public abstract void draw(Batch batch);

    public void draw() {
        draw(Render.b);
        if (showHitbox) {
            Render.b.end();
            drawHitbox();
            Render.b.begin();
        }
    }

    protected void drawHitbox() {
        drawRectangle(getX(), getY(), getMiddleX(), getMiddleY(), getWidth(), getHeight());
    }

    protected static void drawRectangle(float x, float y, float middleX, float middleY, float width, float height) {
        // Draw the hitbox
        SR.begin(ShapeRenderer.ShapeType.Line);
        SR.setColor(Color.WHITE);
        SR.rect(x, y, width, height);
        SR.end();

        // Draw the cirlce in the middle
        SR.begin(ShapeRenderer.ShapeType.Filled);
        SR.setColor(new Color(0f, 0f, 255f, 255f));
        SR.circle(middleX, middleY, 5f);
        SR.end();
    }
}
