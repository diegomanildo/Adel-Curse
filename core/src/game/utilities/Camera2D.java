package game.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import utilities.Utils;

public class Camera2D {
    public static final float OFFSET = 100f;

    public static Hitbox getHitbox(Camera camera) {
        return new Hitbox(getLeft(camera), getBottom(camera), getRight(camera), getTop(camera));
    }

    public static float getLeft(Camera camera) {
        return camera.position.x - camera.viewportWidth / 2f;
    }

    public static float getBottom(Camera camera) {
        return camera.position.y - camera.viewportHeight / 2f;
    }

    public static float getRight(Camera camera) {
        return camera.position.x + camera.viewportWidth / 2f;
    }

    public static float getTop(Camera camera) {
        return camera.position.y + camera.viewportHeight / 2f;
    }

    public static void moveTo(Camera camera, float targetX, float targetY, float transitionTime) {
        // TODO: Thread is interrupted sometimes and the camera doesn't move correctly

        Thread transitionThread = new Thread(() -> {
            final float startX = camera.position.x;
            final float startY = camera.position.y;
            final float deltaX = targetX - startX;
            final float deltaY = targetY - startY;

            float elapsed = 0f;
            while (elapsed < transitionTime) {
                float delta = Gdx.graphics.getDeltaTime();
                elapsed += delta;
                float progress = Math.min(elapsed / transitionTime, 1f);
                camera.position.x = startX + deltaX * progress;
                camera.position.y = startY + deltaY * progress;
                camera.update();
                Utils.sleep(delta * 1000f);
            }
        });
        transitionThread.start();
    }
}