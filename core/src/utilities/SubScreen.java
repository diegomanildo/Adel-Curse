package utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import game.utilities.Camera2D;

public abstract class SubScreen extends ScreenAdapter {
    public static final float FADE_TIME = 1f;

    private boolean show;
    protected final Stage stage;

    protected SubScreen() {
        stage = new Stage(new ExtendViewport(Render.screenSize.width, Render.screenSize.height));
        stage.getViewport().setCamera(new Camera2D());
        stage.setDebugAll(Render.isDebugging());
        show = true;
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        super.show();
        stage.show();
    }

    public void render(float delta) {
        stage.act(delta);
        stage.draw();

        handleInput();
    }

    protected void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.F11)) {
            Settings.SettingPack s = Settings.getSettings();
            s.fullscreen = !s.fullscreen;

            Settings.add(s);
            Settings.applySettings(Settings.getSettings());
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.J)) {
            Render.setDebug(!Render.isDebugging());
            stage.setDebugAll(Render.isDebugging());
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
