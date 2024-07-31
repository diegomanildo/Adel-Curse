package utilities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import game.entities.characters.enemies.Enemy;
import game.entities.characters.playables.Playable;

public class Actor extends com.badlogic.gdx.scenes.scene2d.Actor {
    private Rectangle hitbox;

    public Actor() {
        super();
        hitbox = new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    public float getMiddleX() {
        return getX() + getWidth() / 2f;
    }

    public float getMiddleY() {
        return getY() + getHeight() / 2f;
    }

    public boolean collidesWith(Rectangle rect) {
        return hitbox.x < rect.x + rect.width &&
                hitbox.x + hitbox.width > rect.x &&
                hitbox.y < rect.y + rect.height &&
                hitbox.y + hitbox.height > rect.y;
    }

    public boolean collidesWith(Actor other) {
        return collidesWith(new Rectangle(other.getX(), other.getY(), other.getWidth(), other.getHeight()));
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void setHitbox(Rectangle hitbox) {
        this.hitbox = hitbox;
    }

    public void hitboxReduce(float factor) {
        float w = hitbox.width * factor;
        float h = hitbox.height * factor;

        hitbox.setSize(w, h);
        hitbox.setPosition(getX() + (getWidth() - w) / 2f, getY() + (getHeight() - h) / 2f);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        hitbox.setPosition(getX() + (getWidth() - hitbox.width) / 2, getY() + (getHeight() - hitbox.height) / 2);
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

    private static boolean isIntance(com.badlogic.gdx.scenes.scene2d.Actor actor, Class<?> search) {
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
        return isIntance(actor, Enemy.class);
    }

    public static boolean isPlayable(com.badlogic.gdx.scenes.scene2d.Actor actor) {
        return isIntance(actor, Playable.class);
    }
}
