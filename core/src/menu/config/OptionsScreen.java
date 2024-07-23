package menu.config;

import menu.MainMenuScreen;
import utilities.Screen;
import utilities.*;

public final class OptionsScreen extends BasicOptionsScreen {
    private final Options options;

    public OptionsScreen() {
        super();
        options = new Options(
                20f,
                new Button(Fonts.DEFAULT, "CONTROLS", this::controles),
                new Button(Fonts.DEFAULT, "RESOLUTION", this::resolution)
        );
    }

    @Override
    public void show() {
        super.show();
        options.setAlign(AlignMode.CENTERED);
    }

    private void controles() {
        Render.setScreen(new ControlsScreen());
    }

    private void resolution() {
        Render.setScreen(new ResolutionScreen());
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        options.update();
    }

    @Override
    public void resize(int w, int h) {
        super.resize(w, h);
        options.center();
    }

    @Override
    protected Screen getBackScreen() {
        return new MainMenuScreen();
    }

    @Override
    protected String getTitleScreen() {
        return "OPTIONS";
    }
}
