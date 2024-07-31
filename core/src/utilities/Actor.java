package utilities;

import com.badlogic.gdx.math.Rectangle;

public class Actor extends com.badlogic.gdx.scenes.scene2d.Actor {
    public float getMiddleX() {
        return getX() + getWidth() / 2f;
    }

    public float getMiddleY() {
        return getY() + getHeight() / 2f;
    }

    public boolean collidesWith(Rectangle rect) {
        return getX() < rect.x + rect.width &&
                getX() + getWidth() > rect.x &&
                getY() < rect.y + rect.height &&
                getY() + getHeight() > rect.y;
    }

    public boolean collidesWith(Actor other) {
        return collidesWith(new Rectangle(other.getX(), other.getY(), other.getWidth(), other.getHeight()));
    }
}
