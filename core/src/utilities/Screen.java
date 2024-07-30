package utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import utilities.io.Channels;

public abstract class Screen extends ScreenAdapter {
    protected final Stage stage;
    protected static final float FADE_TIME = 1f;

    protected Screen() {
        Channels.setChannelVolume("Sfx", 1f);
        Channels.setChannelVolume("Music", 0.05f);

        stage = new Stage(new ExtendViewport(Render.screenSize.width, Render.screenSize.height));
        Gdx.input.setInputProcessor(stage);
        stage.setDebugAll(Render.isDebugging());
    }

    public void render(float delta) {
        Render.clear(new Color(0x800000FF));
        stage.act(delta);
        stage.draw();
    }

    public void resize(int w, int h) {
        Render.screenSize = new Size(w, h);
        stage.getViewport().update(w, h, true);
    }

    public void dispose() {
        stage.dispose();
    }
}