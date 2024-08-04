package game.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import utilities.Utils;

public class Camera2D extends OrthographicCamera {
    public Camera2D() {
        super();
    }

    public void setPosition(float x, float y) {
        position.set(x, y, 0f);
        update();
    }

    public void moveTo(float targetX, float targetY, float transitionTime) {
        new Thread(() -> {
            final float startX = position.x;
            final float startY = position.y;
            final float deltaX = targetX - startX;
            final float deltaY = targetY - startY;

            float elapsed = 0f;
            while (elapsed < transitionTime) {
                float delta = Gdx.graphics.getDeltaTime();
                elapsed += delta;
                float progress = Math.min(elapsed / transitionTime, 1f);
                setPosition(startX + deltaX * progress, startY + deltaY * progress);
                update();
                Utils.sleep(delta * 1000f);
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