package menu.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import utilities.Render;
import utilities.Screen;
import utilities.TextButton;

public final class ResolutionScreen extends BasicOptionsScreen {
    private Array<TextButton> options;

    private String textSave;
    private int optionSelected;

    public ResolutionScreen() {
        super();
        Table table = new Table();
        table.setFillParent(true);

        options = new Array<>();
        options.addAll(
                new TextButton("1920x1080"),
                new TextButton("1600x900"),
                new TextButton("1366x768"),
                new TextButton("1360x768"),
                new TextButton("1280x720"),
                new TextButton("1176x664")
        );

        TextButton applyBtn = new TextButton("APPLY", () -> setWindowSize(textSave));

        for (int i = 0; i < options.size; i++) {
            int auxI = i;

            options.get(i).addChangeListener(() -> {
                if (auxI != optionSelected) {
                    options.get(optionSelected).setText(textSave);
                    optionSelected = auxI;
                    textSave = options.get(auxI).getText().toString();
                    options.get(auxI).setText(">" + textSave + "<");
                }
            });

            table.add(options.get(i))
                    .center()
                    .padBottom(10f);

            table.row();
        }

        table.add(applyBtn)
                .bottom()
                .padTop(20f);

        stage.addActor(table);
    }

    @Override
    public void show() {
        super.show();
        configureResolution();
    }

    private void setWindowSize(String resolution) {
        String[] parts = resolution.split("x");
        int w = Integer.parseInt(parts[0]);
        int h = Integer.parseInt(parts[1]);

        Gdx.graphics.setWindowedMode(w, h);
    }

    private void configureResolution() {
        String resolution = (int)Render.screenSize.width + "x" + (int)Render.screenSize.height;

        for (int i = 0; i < options.size; i++) {
            String r = options.get(i).getText().toString();
            if (r.equals(resolution)) {
                optionSelected = i;
                textSave = options.get(i).getText().toString();
                options.get(i).setText(">" + textSave + "<");
            }
        }

        if (textSave == null) {
            throw new RuntimeException("Code error resolution not found: " + resolution);
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