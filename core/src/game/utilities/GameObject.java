package game.utilities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;
import utilities.Render;
import com.badlogic.gdx.graphics.g2d.Batch;

public abstract class GameObject implements Hitbox, Disposable {
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
        Render.sr.begin(ShapeRenderer.ShapeType.Line);
        Render.sr.setColor(Color.GREEN);
        Render.sr.rect(x, y, width, height);

        // Draw middle of screen
        Render.sr.line(Render.screenSize.width / 2f, 0f, Render.screenSize.width / 2f, Render.screenSize.height);
        Render.sr.line(0f, Render.screenSize.height / 2f, Render.screenSize.width, Render.screenSize.height / 2f);
        Render.sr.end();

        // Draw the circle in the middle
        Render.sr.begin(ShapeRenderer.ShapeType.Filled);
        Render.sr.circle(middleX, middleY, 5f);
        Render.sr.end();

        Render.b.begin();
    }

    public void dispose() {}
}
