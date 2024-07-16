package screens.config;

import screens.MainMenuScreen;
import screens.Screen;
import utilities.*;

public final class OptionsScreen extends Screen {
    private Options options;
    private Button backBtn;

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
        backBtn = new Button(Fonts.GOHU_FONT, "VOLVER", () -> Render.setScreen(new MainMenuScreen()));
        backBtn.centerX();
        backBtn.setY(backBtn.getHeight() + 10f);
    }

    private void controles() {
    }

    private void resolution() {
        Render.setScreen(new ResolutionScreen());
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        Render.b.begin();
        backBtn.draw();
        options.draw();
        Render.b.end();
    }
}