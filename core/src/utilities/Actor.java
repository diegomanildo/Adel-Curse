package utilities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import game.entities.characters.enemies.Enemy;
import game.entities.characters.playables.Playable;
import game.utilities.Hitbox;

public class Actor extends com.badlogic.gdx.scenes.scene2d.Actor {
    private final Hitbox hitbox;

    public Actor() {
        super();
        hitbox = new Hitbox();
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

    public boolean collidesWith(Hitbox other) {
        return hitbox.x < other.x + other.width &&
                hitbox.x + hitbox.width > other.x &&
                hitbox.y < other.y + other.height &&
                hitbox.y + hitbox.height > other.y;
    }

    public boolean collidesWith(Actor other) {
        return collidesWith(other.getHitbox());
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

    private static boolean isInstance(com.badlogic.gdx.scenes.scene2d.Actor actor, Class<?> search) {
        Class<?> currentClass = actor.getClass();

        while (currentClass != null) {
            if (currentClass.equals(search)) {
                return true;
            }

            currentClass = currentClass.getSuperclass();
        }

        return false;
    }

    public static boolean isEnemy(com.badlogic.gdx.scenes.scene2d.Actor actor) {
        return isInstance(actor, Enemy.class);
    }

    public static boolean isPlayable(com.badlogic.gdx.scenes.scene2d.Actor actor) {
        return isInstance(actor, Playable.class);
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
}
