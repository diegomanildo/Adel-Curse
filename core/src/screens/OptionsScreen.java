package screens;

import com.badlogic.gdx.Input;
import utilities.*;

public final class OptionsScreen extends Screen {
    private Options options;

    @Override
    public void show() {
        super.show();
        options = new Options(
                20f,
                new Button(Fonts.GOHU_FONT, "CONTROLS", this::controles),
                new Button(Fonts.GOHU_FONT, "RESOLUTION", this::resolution)
        );

        options.setAlign(AlignMode.CENTERED);
        options.center();
    }

    private void controles() {
    }

    private void resolution() {
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (Render.io.isKeyPressed(Input.Keys.ESCAPE)) {
            Render.setScreen(new MainMenuScreen());
        }

        Render.b.begin();
        options.draw();
        Render.b.end();
    }
}
