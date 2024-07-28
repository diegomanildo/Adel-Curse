package game.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;

public class Camera2D extends OrthographicCamera implements Hitbox {
    public Camera2D(float viewportWidth, float viewportHeight) {
        super(viewportWidth, viewportHeight);
    }

    public Camera2D() {
        this(0f, 0f);
    }

    @Override
    public float getX() {
        return position.x;
    }

    @Override
    public float getY() {
        return position.y;
    }

    @Override
    public void setX(float x) {
        position.x = x + viewportWidth / 2f;
        update();
    }

    @Override
    public void setY(float y) {
        position.y = y + viewportHeight / 2f;
        update();
    }

    @Override
    public float getWidth() {
        return viewportWidth;
    }

    @Override
    public float getHeight() {
        return viewportHeight;
    }

    @Override
    public void setWidth(float width) {
        viewportWidth = width;
        update();
    }

    @Override
    public void setHeight(float height) {
        viewportHeight = height;
        update();
    }

    public void moveTo(float targetX, float targetY, float transitionTime) {
        // TODO: Thread is interrupted sometimes and the camera doesn't move correctly

        Thread transitionThread = new Thread(() -> {
            final float startX = position.x;
            final float startY = position.y;
            final float deltaX = targetX - startX;
            final float deltaY = targetY - startY;

            float elapsed = 0;
            while (elapsed < transitionTime) {
                float delta = Gdx.graphics.getDeltaTime();
                elapsed += delta;
                float progress = Math.min(elapsed / transitionTime, 1);
                position.x = startX + deltaX * progress;
                position.y = startY + deltaY * progress;
                update();
                try {
                    Thread.sleep((long) (delta * 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        transitionThread.start();
    }

    public float getLeft() {
        return position.x - viewportWidth / 2f;
    }

    public float getRight() {
        return position.x + viewportWidth / 2f;
    }

    public float getBottom() {
        return position.y - viewportHeight / 2f;
    }

    public float getTop() {
        return position.y + viewportHeight / 2f;
    }

    public Rectangle getHitbox() {
        return new Rectangle(getLeft(), getBottom(), getRight(), getTop());
    }
}
