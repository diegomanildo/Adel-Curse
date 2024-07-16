package screens.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import screens.Screen;
import utilities.*;

public class ResolutionScreen extends Screen {
    private Text screenTitle;
    private Options options;
    private Button backBtn;
    private Button applyBtn;

    private String textSave;
    private int optionSelected;

    @Override
    public void show() {
        super.show();
        screenTitle = new Text(Fonts.GOHU_FONT, "RESOLUTIONS");

        options = new Options(
                20f,
                new Button(Fonts.GOHU_FONT, "1920x1080", () -> {}),
                new Button(Fonts.GOHU_FONT, "1680x1050", () -> {}),
                new Button(Fonts.GOHU_FONT, "1280x720", () -> {})
        );

        options.setAlign(AlignMode.CENTERED);

        backBtn = new Button(Fonts.GOHU_FONT, "BACK", () -> Render.setScreen(new OptionsScreen()));
        applyBtn = new Button(Fonts.GOHU_FONT, "APPLY", () -> setWindowSize(textSave));

        configureResolution();
    }

    private void setWindowSize(String resolution) {
        String[] parts = resolution.split("x");
        int w = Integer.parseInt(parts[0]);
        int h = Integer.parseInt(parts[1]);

        Render.b.end();
        Gdx.graphics.setWindowedMode(w, h);
        Render.b.begin();
    }

    private void configureResolution() {
        for (int i = 0; i < options.buttons.length; i++) {
            if (options.buttons[i].getText().equals((int)Render.screenSize.width + "x" + (int)Render.screenSize.height)) {
                optionSelected = i;
                textSave = options.buttons[i].getText();
                options.buttons[i].setText(">" + textSave + "<");
            }
        }

        if (textSave == null) {
            throw new RuntimeException("Code error resolution not found");
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        selectResolution();

        screenTitle.centerX();
        screenTitle.setY(Render.screenSize.height - 10f);
        screenTitle.font.setColor(Color.RED);

        options.center();

        backBtn.setX(10f);
        backBtn.setY(backBtn.getHeight() + 10f);

        applyBtn.centerX();
        applyBtn.setY(options.getLastButton().getY() - options.getLastButton().getHeight() - options.getButtonsSpace());

        Render.b.begin();
        backBtn.draw();
        screenTitle.draw();
        applyBtn.draw();
        options.draw();
        Render.b.end();
    }

    private void selectResolution() {
        for (int i = 0; i < options.buttons.length; i++) {
            if (options.buttons[i].isClicked() && i != optionSelected) {
                options.buttons[optionSelected].setText(textSave);
                optionSelected = i;
                textSave = options.buttons[i].getText();
                options.buttons[i].setText(">" + textSave + "<");
            }
        }
    }
}