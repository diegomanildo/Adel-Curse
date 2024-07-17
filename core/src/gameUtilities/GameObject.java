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
        return Render.debugMode;
    }

    public boolean collidesWith(GameObject other) {
        return collidesIn(other.getX(), other.getY());
    }

    public abstract void draw(Batch batch);

    public void draw() {
        draw(Render.b);
        if (isShowingHitbox()) {
            drawHitbox();
        }
    }

    protected void drawHitbox() {
        drawHitbox(getX(), getY(), getMiddleX(), getMiddleY(), getWidth(), getHeight());
    }

    protected static void drawHitbox(float x, float y, float middleX, float middleY, float width, float height) {
        Render.b.end();
        // Draw the hitbox
        SR.begin(ShapeRenderer.ShapeType.Line);
        SR.setColor(Color.GREEN);
        SR.rect(x, y, width, height);

        // Draw middle of screen
        SR.line(Render.screenSize.width / 2f, 0f, Render.screenSize.width / 2f, Render.screenSize.height);
        SR.line(0f, Render.screenSize.height / 2f, Render.screenSize.width, Render.screenSize.height / 2f);
        SR.end();

        // Draw the circle in the middle
        SR.begin(ShapeRenderer.ShapeType.Filled);
        SR.circle(middleX, middleY, 5f);
        SR.end();

        Render.b.begin();
    }

    public void dispose() {}
}
