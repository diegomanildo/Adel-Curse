package menu;

import utilities.Screen;
import utilities.io.Song;

public abstract class BasicMainMenuScreen extends Screen {
    protected static Song backgroundSong;

    static {
        backgroundSong = new Song("mainTheme.mp3");
    }

    @Override
    public void show() {
        super.show();

        if (!backgroundSong.isPlaying()) {
            backgroundSong.play(true);
            backgroundSong.setVolume(0.1f);
        }
    }
}
