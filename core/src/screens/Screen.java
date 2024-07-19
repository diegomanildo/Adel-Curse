package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.*;
import managers.ObjectsManager;
import utilities.Render;
import utilities.Size;

public abstract class Screen implements com.badlogic.gdx.Screen {
    protected Viewport viewport;
    public static OrthographicCamera camera;

    private static final int INIT_WIDTH = Gdx.graphics.getWidth();
    private static final int INIT_HEIGHT = Gdx.graphics.getHeight();

    public void show() {
        ObjectsManager.reset();
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(INIT_WIDTH, INIT_HEIGHT, camera);
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
        Render.b.begin();
        ObjectsManager.draw();
        Render.b.end();
    }

    public void pause() {}
    public void resume() {}
    public void hide() {}

    public void dispose() {
        ObjectsManager.dispose();
    }
}
