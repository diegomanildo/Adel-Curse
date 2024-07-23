package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;

public class Camera2D extends OrthographicCamera {
    public Camera2D(float viewportWidth, float viewportHeight) {
        super(viewportWidth, viewportHeight);
    }

    public void setPosition(float x, float y) {
        position.set(x, y, 0f);
    }

    public void transitionCamera(float targetX, float targetY, float transitionTime) {
        new Thread(() -> {
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
        }).start();
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
