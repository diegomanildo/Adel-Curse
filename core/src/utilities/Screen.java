package utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import utilities.io.Stage;

public abstract class Screen extends ScreenAdapter {
    protected final Stage stage;
    protected static final float FADE_TIME = 1f;

    protected Screen() {
        stage = new Stage(new ExtendViewport(Render.screenSize.width, Render.screenSize.height));
        Render.camera = stage.getCamera();
        Gdx.input.setInputProcessor(stage);
        stage.setDebugAll(Render.isDebugging());
    }

    public void render(float delta) {
        Render.clear();
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