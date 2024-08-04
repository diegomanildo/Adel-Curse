package utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import game.utilities.Camera2D;

public abstract class SubScreen extends ScreenAdapter {
    private static final Camera2D DEFAULT_CAMERA = new Camera2D();
    protected static final float FADE_TIME = 1f;

    private boolean show;
    protected final Stage stage;

    protected SubScreen(Camera camera) {
        stage = new Stage(new ExtendViewport(Render.screenSize.width, Render.screenSize.height, camera));
        show = true;
        if (camera.equals(DEFAULT_CAMERA)) {
            Gdx.input.setInputProcessor(stage);
        }
    }

    protected SubScreen(boolean isSub) {
        this(new OrthographicCamera());
    }

    protected SubScreen() {
        this(DEFAULT_CAMERA);
    }

    public void render(float delta) {
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

    public boolean isShowing() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;

        if (show) {
            Gdx.input.setInputProcessor(stage);
        }
    }
}
