package menu;

import utilities.io.Song;

public abstract class BasicMainMenuScreen extends Screen {
    protected static Song backgroundSong = new Song("mainTheme.mp3", 0.1f);

    @Override
    public void show() {
        super.show();

        if (!backgroundSong.isPlayingSong()) {
            backgroundSong.playSong();
        }
    }
}
