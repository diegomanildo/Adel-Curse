package utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import game.utilities.Camera2D;

public abstract class SubScreen extends ScreenAdapter {
    protected static final float FADE_TIME = 1f;

    private boolean show;
    protected final Stage stage;

    protected SubScreen() {
        stage = new Stage(new ExtendViewport(Render.screenSize.width, Render.screenSize.height, new Camera2D()));
        stage.setDebugAll(Render.isDebugging());
        show = true;
        Gdx.input.setInputProcessor(stage);
    }

    public void render(float delta) {
        stage.act(delta);
        stage.draw();

        if (Gdx.input.isKeyPressed(Input.Keys.J)) {
            stage.setDebugAll(!stage.isDebugAll());
            Utils.sleep(50);
        }
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
