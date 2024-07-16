package gameUtilities;

import utilities.Render;

public interface Hitbox {
    float getX();
    float getY();

    void setX(float x);
    void setY(float y);

    float getWidth();
    float getHeight();

    void setWidth(float width);
    void setHeight(float height);

    default void setPosition(float x, float y) {
        setX(x);
        setY(y);
    }

    default void setSize(float width, float height) {
        setWidth(width);
        setHeight(height);
    }

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

    default void centerX() {
        setX((Render.screenSize.width - getWidth()) / 2f);
    }

    default void centerY() {
        setY((Render.screenSize.height - getHeight()) / 2f);
    }

    default void center() {
        centerX();
        centerY();
    }
}