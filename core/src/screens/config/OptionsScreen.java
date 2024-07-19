package screens.config;

import screens.MainMenuScreen;
import screens.Screen;
import utilities.*;

public final class OptionsScreen extends BasicOptionsScreen {
    private Options options;
    private Button backBtn;

    @Override
    public void show() {
        super.show();
        options = new Options(
                20f,
                new Button(Fonts.DEFAULT, "CONTROLS", this::controles),
                new Button(Fonts.DEFAULT, "RESOLUTION", this::resolution)
        );

        options.setAlign(AlignMode.CENTERED);
        options.center();
        backBtn = new Button(Fonts.DEFAULT, "BACK", () -> Render.setScreen(new MainMenuScreen()));
        backBtn.setX(10f);
        backBtn.setY(backBtn.getHeight() + 10f);
    }

    private void controles() {
        Render.setScreen(new ControlsScreen());
    }

    private void resolution() {
        Render.setScreen(new ResolutionScreen());
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
