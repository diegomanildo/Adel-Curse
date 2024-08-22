package game.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;

public class Camera2D extends OrthographicCamera {
    private boolean isMoving = false;

    public Camera2D() {
        super();
    }

    public Camera2D(Camera camera) {
        super(camera.viewportWidth, camera.viewportHeight);
    }

    public void setPosition(float x, float y) {
        position.set(x, y, 0f);
        update();
    }

    public void moveTo(float targetX, float targetY, float transitionTime, Runnable onEnd) {
        isMoving = true;
        final float startX = position.x;
        final float startY = position.y;
        final float deltaX = targetX - startX;
        final float deltaY = targetY - startY;

        final float[] elapsed = {0f};

        Runnable moveStep = new Runnable() {
            @Override
            public void run() {
                float delta = Gdx.graphics.getDeltaTime();
                elapsed[0] += delta;
                float progress = Math.min(elapsed[0] / transitionTime, 1f);

                setPosition(startX + deltaX * progress, startY + deltaY * progress);
                update();

                if (progress < 1f) {
                    Gdx.app.postRunnable(this);
                } else {
                    isMoving = false;
                    onEnd.run();
                }
            }
        };

        Gdx.app.postRunnable(moveStep);
    }

    public void moveTo(float targetX, float targetY, float transitionTime) {
        moveTo(targetX, targetY, transitionTime, () -> {});
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

    public boolean isMoving() {
        return isMoving;
    }
}