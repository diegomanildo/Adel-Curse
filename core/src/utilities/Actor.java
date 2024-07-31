package utilities;

import com.badlogic.gdx.math.Rectangle;

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
}
