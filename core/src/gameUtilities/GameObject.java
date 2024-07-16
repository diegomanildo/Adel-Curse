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
    private boolean showHitbox = false;
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
        drawHitbox(getX(), getY(), getMiddleX(), getMiddleY(), getWidth(), getHeight());
    }

    protected static void drawHitbox(float x, float y, float middleX, float middleY, float width, float height) {
        // Draw the hitbox
        SR.begin(ShapeRenderer.ShapeType.Line);
        SR.setColor(Color.GREEN);
        SR.rect(x, y, width, height);

        // Draw the circle in the middle
        SR.circle(middleX, middleY, 5f);

        // Draw middle of screen
        SR.line(Render.screenSize.width / 2f, 0f, Render.screenSize.width / 2f, Render.screenSize.height);
        SR.line(0f, Render.screenSize.height / 2f, Render.screenSize.width, Render.screenSize.height / 2f);

        SR.end();
    }

    public void dispose() {}
}
