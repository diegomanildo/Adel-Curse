package utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import utilities.io.Stage;

public abstract class Screen extends ScreenAdapter {
    private static final OrthographicCamera DEFAULT_CAMERA = new OrthographicCamera();
    protected final Stage stage;
    protected static final float FADE_TIME = 1f;

    protected Screen(Camera camera) {
        stage = new Stage(new ExtendViewport(Render.screenSize.width, Render.screenSize.height, camera));
        if (camera.equals(DEFAULT_CAMERA)) {
            Render.camera = stage.getCamera();
            Gdx.input.setInputProcessor(stage);
        }
    }

    protected Screen() {
        this(DEFAULT_CAMERA);
    }

    public void render(float delta) {
        render(delta, true);

        if (stage.isKeyPressed(Input.Keys.J)) {
            stage.setDebugAll(!stage.isDebugAll());
            Utils.sleep(100);
        }
    }

    protected void render(float delta, boolean clearScreen) {
        if (clearScreen) {
            Render.clear();
        }
        stage.act(delta);
        stage.draw();
    }

    public void resize(int w, int h) {
        Render.screenSize = new Size(w, h);
        stage.getViewport().update(w, h, true);
    }

    public void dispose() {
        stage.dispose();
        stage.clear();
    }
}