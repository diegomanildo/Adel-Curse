package screens.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import screens.Screen;
import utilities.*;

public class ResolutionScreen extends Screen {
    private Text screenTitle;
    private Options options;
    private Button backBtn;

    @Override
    public void show() {
        super.show();
        screenTitle = new Text(Fonts.GOHU_FONT, "RESOLUTIONS");
        screenTitle.centerX();
        screenTitle.setY(Render.screenSize.height - 10f);
        screenTitle.font.setColor(Color.RED);

        options = new Options(
                20f,
                new Button(Fonts.GOHU_FONT, "1920x1080", () -> setWindowSize(1920, 1080)),
                new Button(Fonts.GOHU_FONT, "1680x1050", () -> setWindowSize(1680, 1050)),
                new Button(Fonts.GOHU_FONT, "1280x720", () -> setWindowSize(1280, 720))
        );

        options.setAlign(AlignMode.CENTERED);
        options.center();

        backBtn = new Button(Fonts.GOHU_FONT, "VOLVER", () -> Render.setScreen(new OptionsScreen()));
        backBtn.centerX();
        backBtn.setY(backBtn.getHeight() + 10f);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        Render.b.begin();
        backBtn.draw();
        screenTitle.draw();
        options.draw();
        Render.b.end();
    }

    private void setWindowSize(int w, int h) {
        Render.b.end();
        Gdx.graphics.setWindowedMode(w, h);
        Render.b.begin();
    }
}