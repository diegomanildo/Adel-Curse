package menu.config;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import menu.MainMenuScreen;
import utilities.SelectBox;
import utilities.io.Song;

public class SongSelectorScreen extends BasicOptionsScreen {
    public SongSelectorScreen(MainMenuScreen backScreen) {
        super(backScreen);
        String[] songNames = {
                "Trying to Scape",
                "Spider Funk",
                "Lost Souls",
                "Piano Man",
                "Death to Eva",
        };

        SelectBox<String>songSelectBox = new SelectBox<>();
        songSelectBox.setItems(songNames);
        songSelectBox.setSelected(songNames[0]);

        songSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                backgroundSong.stop();
                backgroundSong = new Song("Music", "Songs/" + songSelectBox.getSelected() + ".mp3");
                backgroundSong.play(true);
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.add(songSelectBox).center();

        stage.addActor(table);
    }

    @Override
    protected String getTitleScreen() {
        return "SONG SELECTOR";
    }
}
