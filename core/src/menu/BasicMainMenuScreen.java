package menu;

import utilities.Screen;
import utilities.io.Song;

public abstract class BasicMainMenuScreen extends Screen {
    public static Song backgroundSong;

    static {
        backgroundSong = new Song("Music", "songs/Trying to Scape.mp3");
    }

    public BasicMainMenuScreen() {
        super();
    }

    @Override
    public void show() {
        super.show();

        if (!backgroundSong.isPlaying()) {
            backgroundSong.play(true);
        }
    }
}
