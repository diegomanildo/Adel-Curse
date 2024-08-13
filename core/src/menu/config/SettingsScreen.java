package menu.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import languages.Language;
import utilities.*;
import utilities.io.Channels;

public final class SettingsScreen extends BasicOptionsScreen {
    private final SelectBox<String> resolutionSelectBox;
    private final SelectBox<String> fpsSelectBox;
    private final SelectBox<String> languageSelectBox;
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

        Array<String> languages = new Array<>();
        for (Language l : Language.getLanguages()) {
            languages.add(l.name());
        }

        globalVolumeEditor = new VolumeEditor(Channels.GLOBAL_CHANNEL);
        musicVolumeEditor = new VolumeEditor("Music");
        sfxVolumeEditor = new VolumeEditor("Sfx");

        resolutionSelectBox = new SelectBox<>();
        resolutionSelectBox.setItems(resolutions);

        fpsSelectBox = new SelectBox<>();
        fpsSelectBox.setItems(fpsOptions);

        languageSelectBox = new SelectBox<>();
        languageSelectBox.setItems(languages);

        languageSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Render.currentLanguage = Language.get(languageSelectBox.getSelected());
            }
        });

        fullscreenCheckBox = new CheckBox(Render.currentLanguage.fullScreen());

        TextButton applyBtn = new TextButton(Render.currentLanguage.apply(), this::applySettings);

        table.add(new Label(Render.currentLanguage.language())).padTop(20f).padBottom(20f).left().row();
        table.add(languageSelectBox).padBottom(10f).row();


        table.add(new Label(Render.currentLanguage.video())).padTop(20f).padBottom(20f).left().row();
        table.add(new Label(Render.currentLanguage.resolution() + ":")).left();
        table.add(resolutionSelectBox).padBottom(10f).row();
        table.add(new Label(Render.currentLanguage.fps() + ":")).left();
        table.add(fpsSelectBox).padBottom(10f).row();
        table.add(fullscreenCheckBox).colspan(2).left().padBottom(10f).row();

        table.add(new Label(Render.currentLanguage.volume())).padTop(20f).padBottom(20f).left().row();
        globalVolumeEditor.addToTable(table);
        musicVolumeEditor.addToTable(table);
        sfxVolumeEditor.addToTable(table);
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

        // Set the language
        languageSelectBox.setSelected(Render.currentLanguage.name());

        // Set the fullScreen
        fullscreenCheckBox.setChecked(Gdx.graphics.isFullscreen());

        // Set the volumes
        globalVolumeEditor.slider.setValue(s.globalVolume);
        musicVolumeEditor.slider.setValue(s.musicVolume);
        sfxVolumeEditor.slider.setValue(s.sfxVolume);
    }

    @Override
    protected String getTitleScreen() {
        return Render.currentLanguage.settingsBtn();
    }
}
