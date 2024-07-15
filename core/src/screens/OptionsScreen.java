package screens;

import com.badlogic.gdx.Input;
import utilities.Button;
import utilities.Options;
import utilities.Render;

public final class OptionsScreen extends Screen {
    private Options options;

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (Render.io.isKeyPressed(Input.Keys.ESCAPE)) {
            Render.setScreen(new MainMenuScreen());
        }
    }
}
