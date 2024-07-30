package menu.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import utilities.Render;
import utilities.Screen;
import utilities.SelectBox;
import utilities.TextButton;

public final class ResolutionScreen extends BasicOptionsScreen {
    private final Array<String> options;
    private final SelectBox<String> resolutionSelectBox;

    public ResolutionScreen() {
        super();
        Table table = new Table();
        table.setFillParent(true);

        options = new Array<>();
        options.addAll(
                "1920x1080",
                "1600x900",
                "1366x768",
                "1360x768",
                "1280x720",
                "1176x664",
                "1024x768",
                "800x600",
                "640x480"
        );

        resolutionSelectBox = new SelectBox<>();
        resolutionSelectBox.setItems(options);

        TextButton applyBtn = new TextButton("APPLY", () -> setWindowSize(resolutionSelectBox.getSelected()));

        table.add(resolutionSelectBox)
                .center()
                .padBottom(10f);

        table.row();

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
        String resolution = (int) Render.screenSize.width + "x" + (int) Render.screenSize.height;

        for (int i = 0; i < options.size; i++) {
            if (options.get(i).equals(resolution)) {
                resolutionSelectBox.setSelected(options.get(i));
                return;
            }
        }

        throw new RuntimeException("Code error resolution not found: " + resolution);
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
