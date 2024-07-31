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
            label = new Label(channelName + " volume");
            slider = new Slider(0f, 1f, 0.01f, false);
            field = new TextField();

            slider.addListener(() -> Channels.setChannelVolume(name, slider.getValue()));

            field.setDisabled(true);
        }

        public void addToTable(Table t) {
            t.add(label).left().padBottom(10f);
            t.add(slider);
            t.add(field).width(50f);
            t.row();
        }

        public String getName() {
            return name;
        }
    }

    private final Array<VolumeEditor> volumes = new Array<>();

    public SoundSettingsScreen() {
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
    public void show() {
        super.show();
        volumes.forEach(v -> v.slider.setValue(Channels.getChannelVolume(v.getName())));
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        volumes.forEach(v -> v.field.setText((int)(v.slider.getPercent() * 100f) + "%"));
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
