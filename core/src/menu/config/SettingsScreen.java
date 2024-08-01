package menu.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import menu.MainMenuScreen;
import utilities.*;
import utilities.io.Channels;

public final class SettingsScreen extends BasicOptionsScreen {
    private final SelectBox<String> resolutionSelectBox;
    private final SelectBox<String> fpsSelectBox;
    private final CheckBox fullscreenCheckBox;
    private static class VolumeEditor {
        private final String name;
        private final Label label;
        private final Slider slider;
        private final TextField field;

        public VolumeEditor(String channelName) {
            name = channelName;
            label = new Label(name + " volume");
            slider = new Slider(0f, 1f, 0.01f, false);
            slider.setValue(Channels.getChannelVolume(name));
            slider.addListener(this::sliderChanged);
            field = new TextField();
            field.setDisabled(true);
            sliderChanged();
        }

        private void sliderChanged() {
            Channels.setChannelVolume(name, slider.getValue());
            field.setText((int)(slider.getPercent() * 100f) + "%");
        }

        public void addToTable(Table t) {
            t.add(label).left().padBottom(10f);
            t.add(slider).padLeft(10f);
            t.add(field).width(50f);
            t.row();
        }
    }

    public SettingsScreen() {
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

        Array<VolumeEditor> volumes = new Array<>();
        volumes.addAll(
                new VolumeEditor(Channels.GLOBAL_CHANNEL),
                new VolumeEditor("Music"),
                new VolumeEditor("Sfx")
        );

        resolutionSelectBox = new SelectBox<>();
        resolutionSelectBox.setItems(resolutions);

        fpsSelectBox = new SelectBox<>();
        fpsSelectBox.setItems(fpsOptions);

        fullscreenCheckBox = new CheckBox("Fullscreen");

        TextButton applyBtn = new TextButton("APPLY", this::applySettings);

        table.add(new Label("Video")).padTop(20f).padBottom(20f).left().row();
        table.add(new Label("Resolution:")).left();
        table.add(resolutionSelectBox).padBottom(10f).row();
        table.add(new Label("FPS:")).left();
        table.add(fpsSelectBox).padBottom(10f).row();
        table.add(fullscreenCheckBox).colspan(2).left().padBottom(10f).row();

        table.add(new Label("Volume")).padTop(20f).padBottom(20f).left().row();
        volumes.forEach(v -> v.addToTable(table));
        table.add(applyBtn).center().width(200f).height(45f).padBottom(10f).colspan(2).center().padTop(20f);

        stage.addActor(table);
    }

    @Override
    public void show() {
        super.show();
        configureSettings();
    }

    private void applySettings() {
        Size resolution = Size.getResolution(resolutionSelectBox.getSelected());
        int fps = Integer.parseInt(fpsSelectBox.getSelected());
        Settings.add(new Settings.SettingPack(resolution, fps, fullscreenCheckBox.isChecked()));
        Settings.applySettings(Settings.getSettings());
    }

    private void configureSettings() {
        Settings.SettingPack s = Settings.getSettings();

        // Set the current resolution
        resolutionSelectBox.setSelected((int) Render.screenSize.width + "x" + (int) Render.screenSize.height);

        // Set the current FPS
        fpsSelectBox.setSelected(String.valueOf(Render.fps));

        // Set the fullScreen
        fullscreenCheckBox.setChecked(Gdx.graphics.isFullscreen());
    }

    @Override
    protected Screen getBackScreen() {
        return new MainMenuScreen();
    }

    @Override
    protected String getTitleScreen() {
        return "SETTINGS";
    }
}
