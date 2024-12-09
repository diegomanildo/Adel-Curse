package menu;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import utilities.FilePaths;
import utilities.Image;
import utilities.Screen;
import utilities.audio.Song;

public abstract class BasicMainMenuScreen extends Screen {
    public static Song backgroundSong = new Song("Music", "songs/tryingToScape.mp3");

    public BasicMainMenuScreen() {
        super();
        Table table = new Table();
        table.setFillParent(true);
        Image background = new Image(FilePaths.BACKGROUNDS + "bgScreen.png");
        table.add(background);
        stage.addActor(table);
    }

    @Override
    public void show() {
        super.show();

        if (!backgroundSong.isPlaying()) {
            backgroundSong.play(true);
        }
    }
}
