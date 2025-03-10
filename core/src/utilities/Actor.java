package utilities;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;
import game.utilities.Camera2D;
import game.utilities.Hitbox;

public class Actor extends com.badlogic.gdx.scenes.scene2d.Actor implements Disposable {
    private final Hitbox hitbox;

    public Actor() {
        super();
        hitbox = new Hitbox();
    }

    public void show() {
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        hitbox.setPosition(getX() + (getWidth() - hitbox.width) / 2f, getY() + (getHeight() - hitbox.height) / 2f);
    }

    public float getMiddleX() {
        return getX() + getWidth() / 2f;
    }

    public float getMiddleY() {
        return getY() + getHeight() / 2f;
    }

    public void centerX(com.badlogic.gdx.scenes.scene2d.Actor other) {
        setX(other.getX() + (other.getWidth() - getWidth()) / 2f);
    }

    public void centerY(com.badlogic.gdx.scenes.scene2d.Actor other) {
        setY(other.getY() + (other.getHeight() - getHeight()) / 2f);
    }

    public void center(com.badlogic.gdx.scenes.scene2d.Actor other) {
        centerX(other);
        centerY(other);
    }

    public boolean collidesWith(Hitbox other) {
        return hitbox.collidesWith(other);
    }

    public boolean collidesWith(Actor other) {
        return other.getHitbox() == null ? collidesWith(new Hitbox(other.getX(), other.getY(), other.getWidth(), other.getHeight())) : collidesWith(other.getHitbox());
    }

    public boolean collidesWith(Rectangle other) {
        return collidesWith(new Hitbox(other.getX(), other.getY(), other.getWidth(), other.getHeight()));
    }

    public Hitbox getHitbox() {
        return hitbox;
    }

    public void setHitbox(float width, float height) {
        this.hitbox.setSize(width, height);
    }

    @Override
    public void drawDebug(ShapeRenderer shapes) {
        super.drawDebug(shapes);
        drawHitbox(shapes);
    }

    private void drawHitbox(ShapeRenderer shapes) {
        shapes.set(ShapeRenderer.ShapeType.Line);
        shapes.setColor(Color.PURPLE);
        shapes.rect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
    }

    @Override
    public void setPosition(float x, float y) {
        setX(x);
        setY(y);
    }

    @Override
    public void setSize(float width, float height) {
        setWidth(width);
        setHeight(height);
    }

    @Override
    public void setBounds(float x, float y, float width, float height) {
        setPosition(x, y);
        setSize(width, height);
    }

    public Camera2D getCamera() {
        Camera camera = getStage().getCamera();
        if (camera instanceof Camera2D) {
            return (Camera2D) camera;
        } else {
            return new Camera2D(camera);
        }
    }

    public float getOpacity() {
        return getColor().a;
    }

    public void setOpacity(float opacity) {
        Color c = getColor();
        setColor(c.r, c.g, c.b, opacity);
    }

    public void dispose() {

    }

    public Hitbox getBounds() {
        return new Hitbox(getX(), getY(), getWidth(), getHeight());
    }
}
