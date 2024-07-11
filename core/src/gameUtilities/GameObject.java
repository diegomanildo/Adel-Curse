package gameUtilities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import utilities.Render;
import com.badlogic.gdx.graphics.g2d.Batch;

public abstract class GameObject {
    private float x;
    private float y;
    private float width;
    private float height;
    private boolean showHitbox = true;
    private static ShapeRenderer shapeRenderer = new ShapeRenderer();

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
        return height + 5f;
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

    public boolean collidesIn(float pointX, float pointY) {
        return pointX >= x && pointX <= x + width
            && pointY >= y && pointY <= y + height;
    }

    public boolean collidesWith(GameObject other) {
        return x < other.x + other.width && x + width > other.x &&
                y < other.y + other.height && y + height > other.y;
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
        drawRectangle(x, y, width, height);
    }

    protected static void drawRectangle(float x, float y, float width, float height) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.rect(x, y, width, height);
        shapeRenderer.end();
    }
}
