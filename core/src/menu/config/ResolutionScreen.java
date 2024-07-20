package menu.config;

import com.badlogic.gdx.Gdx;
import menu.Screen;
import utilities.*;

public final class ResolutionScreen extends BasicOptionsScreen {
    private Options options;
    private Button applyBtn;

    private String textSave;
    private int optionSelected;

    @Override
    public void show() {
        super.show();

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
        options.center();
        for (int i = 0; i < options.buttons.length; i++) {
            options.buttons[i].showBackground(false);
        }

        applyBtn = new Button(Fonts.DEFAULT, "APPLY", () -> setWindowSize(textSave));
        applyBtn.centerX();
        applyBtn.setY(options.getLastButton().getY() - options.getLastButton().getHeight() - (options.getButtonsSpace() * 4f));

        configureResolution();
    }

    private void setWindowSize(String resolution) {
        String[] parts = resolution.split("x");
        int w = Integer.parseInt(parts[0]);
        int h = Integer.parseInt(parts[1]);

        Gdx.graphics.setWindowedMode(w, h);
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
        options.center();
        options.update();
        applyBtn.update();
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

    @Override
    protected Screen getBackScreen() {
        return new OptionsScreen();
    }

    @Override
    protected String getTitleScreen() {
        return "RESOLUTION";
    }
}