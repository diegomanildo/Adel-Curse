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
        screenTitle = new Text(Fonts.DEFAULT, "RESOLUTIONS");

        options = new Options(
                10f,
                new Button(Fonts.DEFAULT, "1920x1080"),
                new Button(Fonts.DEFAULT, "1600x900"),
                new Button(Fonts.DEFAULT, "1366x768"),
                new Button(Fonts.DEFAULT, "1360x768"),
                new Button(Fonts.DEFAULT, "1280x720"),
                new Button(Fonts.DEFAULT, "1176x664")
        );

        options.setAlign(AlignMode.CENTERED);

        backBtn = new Button(Fonts.DEFAULT, "BACK", () -> Render.setScreen(new OptionsScreen()));
        applyBtn = new Button(Fonts.DEFAULT, "APPLY", () -> setWindowSize(textSave));

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
        applyBtn.setY(options.getLastButton().getY() - options.getLastButton().getHeight() - (options.getButtonsSpace() * 4f));

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