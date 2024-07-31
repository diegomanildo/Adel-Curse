package game.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Rectangle;
import utilities.Utils;

public class Camera2D {
    public static Rectangle getBounds(Camera camera) {
        return new Rectangle(camera.position.x - camera.viewportWidth / 2f, camera.position.x + camera.viewportWidth / 2f, camera.position.y - camera.viewportHeight / 2f, camera.position.y + camera.viewportHeight / 2f);
    }

    public static void moveTo(Camera camera, float targetX, float targetY, float transitionTime) {
        // TODO: Thread is interrupted sometimes and the camera doesn't move correctly

        Thread transitionThread = new Thread(() -> {
            final float startX = camera.position.x;
            final float startY = camera.position.y;
            final float deltaX = targetX - startX;
            final float deltaY = targetY - startY;

            float elapsed = 0;
            while (elapsed < transitionTime) {
                float delta = Gdx.graphics.getDeltaTime();
                elapsed += delta;
                float progress = Math.min(elapsed / transitionTime, 1);
                camera.position.x = startX + deltaX * progress;
                camera.position.y = startY + deltaY * progress;
                camera.update();
                Utils.sleep(delta * 1000f);
            }
        });
        transitionThread.start();
    }
}
