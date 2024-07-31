package menu.config;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import utilities.Label;
import utilities.Screen;
import utilities.Slider;
import utilities.TextField;
import utilities.io.Channels;

public class SoundSettingsScreen extends BasicOptionsScreen {
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
            t.add(slider);
            t.add(field).width(50f);
            t.row();
        }
    }

    public SoundSettingsScreen() {
        Array<VolumeEditor> volumes = new Array<>();
        volumes.addAll(
                new VolumeEditor(Channels.GLOBAL_CHANNEL),
                new VolumeEditor("Music"),
                new VolumeEditor("Sfx")
        );

        Table table = new Table();
        table.setFillParent(true);

        volumes.forEach(v -> v.addToTable(table));

        stage.addActor(table);
    }

    @Override
    protected Screen getBackScreen() {
        return new OptionsScreen();
    }

    @Override
    protected String getTitleScreen() {
        return "SOUND SETTINGS";
    }
}
