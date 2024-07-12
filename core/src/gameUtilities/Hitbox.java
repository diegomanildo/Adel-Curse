package gameUtilities;

public interface Hitbox {
    float getX();
    float getY();

    void setX();
    void setY();

    float getWidth();
    float getHeight();

    void setWidth();
    void setHeight();

    void setPosition(float x, float y);
    void setSize(float x, float y);

    default float getMiddleX() {
        return getX() + getWidth() / 2f;
    }

    default float getMiddleY() {
        return getY() + getHeight() / 2f;
    }

    default boolean collidesIn(float pointX, float pointY) {
        return pointX >= getX() && pointX <= getX() + getWidth()
                && pointY >= getY() && pointY <= getY() + getHeight();
    }
}