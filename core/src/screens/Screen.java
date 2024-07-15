package screens;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.utils.viewport.FitViewport;

public abstract class Screen implements com.badlogic.gdx.Screen {
    protected Viewport viewport;
    protected OrthographicCamera camera;

    public Screen() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(1360, 766, camera);
    }

    public void show() {}
    public void render(float delta) {}
    public void resize(int w, int h) {
        viewport.update(w, h);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
    }
    public void pause() {}
    public void resume() {}
    public void hide() {}
    public void dispose() {}
}
