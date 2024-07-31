package menu.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import utilities.*;

public final class VideoSettingsScreen extends BasicOptionsScreen {
    private final SelectBox<String> resolutionSelectBox;
    private final SelectBox<String> fpsSelectBox;
    private final CheckBox fullscreenCheckBox;

    public VideoSettingsScreen() {
        super();
        Table table = new Table();
        table.setFillParent(true);

        Array<String> resolutions = new Array<>();
        resolutions.addAll(
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

        Array<String> fpsOptions = new Array<>();
        fpsOptions.addAll(
                "30",
                "60",
                "120",
                "144",
                "240"
        );

        resolutionSelectBox = new SelectBox<>();
        resolutionSelectBox.setItems(resolutions);

        fpsSelectBox = new SelectBox<>();
        fpsSelectBox.setItems(fpsOptions);

        fullscreenCheckBox = new CheckBox("Fullscreen");

        TextButton applyBtn = new TextButton("APPLY", this::applySettings);

        table.add(new Label("Resolution:")).left();
        table.add(resolutionSelectBox).padBottom(10f).row();
        table.add(new Label("FPS:")).left();
        table.add(fpsSelectBox).padBottom(10f).row();
        table.add(fullscreenCheckBox).colspan(2).left().padBottom(10f).row();
        table.add(applyBtn).colspan(2).center().padTop(20f);

        stage.addActor(table);
    }

    @Override
    public void show() {
        super.show();
        configureSettings();
    }

    private void applySettings() {
        String resolution = resolutionSelectBox.getSelected();
        String[] parts = resolution.split("x");
        int w = Integer.parseInt(parts[0]);
        int h = Integer.parseInt(parts[1]);

        if (fullscreenCheckBox.isChecked()) {
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        } else {
            Gdx.graphics.setWindowedMode(w, h);
        }

        int fps = Integer.parseInt(fpsSelectBox.getSelected());
        Gdx.graphics.setForegroundFPS(fps);
        Render.fps = fps;
    }

    private void configureSettings() {
        // Set the current resolution
        resolutionSelectBox.setSelected((int) Render.screenSize.width + "x" + (int) Render.screenSize.height);

        // Set the current FPS
        fpsSelectBox.setSelected(String.valueOf(Render.fps));

        // Set the fullScreen
        fullscreenCheckBox.setChecked(Gdx.graphics.isFullscreen());
    }

    @Override
    protected Screen getBackScreen() {
        return new OptionsScreen();
    }

    @Override
    protected String getTitleScreen() {
        return "VIDEO SETTINGS";
    }
}
