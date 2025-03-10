package menu.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import utilities.*;
import utilities.audio.Channels;
import utilities.audio.Song;

public final class SettingsScreen extends BasicOptionsScreen {
    private final SelectBox<String> resolutionSelectBox;
    private final SelectBox<String> fpsSelectBox;
    private final CheckBox fullscreenCheckBox;

    private static class VolumeEditor {
        private final String name;
        private final Label label;
        private final Slider slider;
        private final TextField field;

        public VolumeEditor(String channelName, String labelText) {
            name = channelName;
            label = new Label(labelText);
            slider = new Slider(0f, 1f, 0.01f, false);
            slider.setValue(Channels.getChannelVolume(name));
            slider.addListener(this::sliderChanged);
            field = new TextField();
            field.setDisabled(true);
            sliderChanged();
        }

        public VolumeEditor(String channelName) {
            this(channelName, channelName + " volume");
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

        public float getValue() {
            return slider.getValue();
        }
    }

    private final VolumeEditor globalVolumeEditor;
    private final VolumeEditor musicVolumeEditor;
    private final VolumeEditor sfxVolumeEditor;

    public SettingsScreen(Screen backScreen) {
        super(backScreen);
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

        SelectBox<String> songSelectBox = getStringSelectBox();

        globalVolumeEditor = new VolumeEditor(Channels.GLOBAL_CHANNEL, "Volumen Global");
        musicVolumeEditor = new VolumeEditor("Music", "Volumen Musica");
        sfxVolumeEditor = new VolumeEditor("Sfx", "Volumen sfx");

        resolutionSelectBox = new SelectBox<>();
        resolutionSelectBox.setItems(resolutions);

        fpsSelectBox = new SelectBox<>();
        fpsSelectBox.setItems(fpsOptions);

        fullscreenCheckBox = new CheckBox("Pantalla completa");

        TextButton applyBtn = new TextButton("Aplicar", this::applySettings);

        table.add(new Label("Video")).padTop(20f).padBottom(20f).left().row();
        table.add(new Label("Resolucion:")).left();
        table.add(resolutionSelectBox).padBottom(10f).row();
        table.add(new Label("Fps:")).left();
        table.add(fpsSelectBox).padBottom(10f).row();
        table.add(fullscreenCheckBox).colspan(2).left().padBottom(10f).row();
        table.add(new Label("Song selector")).padTop(20f).padBottom(20f).left().row();
        table.add(songSelectBox).left().row();

        table.add(new Label("Volumen")).padTop(20f).padBottom(20f).left().row();
        globalVolumeEditor.addToTable(table);
        musicVolumeEditor.addToTable(table);
        sfxVolumeEditor.addToTable(table);
        table.add(applyBtn).center().width(200f).height(45f).padBottom(10f).colspan(2).center().padTop(20f);

        stage.addActor(table);
    }

    private SelectBox<String> getStringSelectBox() {
        String[] songNames = {
                "Trying to scape",
                "Spider Funk",
                "Lost Souls",
                "Piano Man",
                "Death to Eva",
        };

        SelectBox<String> songSelectBox = new SelectBox<>();
        songSelectBox.setItems(songNames);
        songSelectBox.setSelected(songNames[0]);

        songSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                backgroundSong.stop();
                backgroundSong = new Song("Music", "songs/" + Utils.toCamelCase(songSelectBox.getSelected()) + ".mp3");
                backgroundSong.play(true);
            }
        });
        return songSelectBox;
    }

    @Override
    public void show() {
        super.show();
        configureSettings();
    }

    private void applySettings() {
        Size resolution = Size.getResolution(resolutionSelectBox.getSelected());
        int fps = Integer.parseInt(fpsSelectBox.getSelected());
        float globalVolume = globalVolumeEditor.getValue();
        float musicVolume = musicVolumeEditor.getValue();
        float sfxVolume = sfxVolumeEditor.getValue();

        Settings.add(new Settings.SettingPack(resolution, fps, fullscreenCheckBox.isChecked(), globalVolume, musicVolume, sfxVolume));
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

        // Set the volumes
        globalVolumeEditor.slider.setValue(s.globalVolume);
        musicVolumeEditor.slider.setValue(s.musicVolume);
        sfxVolumeEditor.slider.setValue(s.sfxVolume);
    }

    @Override
    protected String getTitleScreen() {
        return "Configuracion";
    }
}
