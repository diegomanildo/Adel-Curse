package screens;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.*;
import utilities.Render;
import utilities.Size;

public abstract class Screen implements com.badlogic.gdx.Screen {
    protected Viewport viewport;
    public static OrthographicCamera camera;

    public void show() {
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(Render.screenSize.width, Render.screenSize.height, camera);
    }

    public void resize(int w, int h) {
        Render.screenSize = new Size(w, h);
        viewport.update(w, h);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0f);
        camera.update();
    }

    public void render(float delta) {
        Render.clear();
        viewport.apply();
    }

    public void pause() {}
    public void resume() {}
    public void hide() {}
    public void dispose() {}
}
