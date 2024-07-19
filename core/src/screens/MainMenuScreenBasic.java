package screens;

import utilities.FilePaths;
import utilities.Image;
import utilities.io.Song;

public abstract class MainMenuScreenBasic extends Screen {
    protected static Song backgroundSong = new Song("mainTheme.mp3", 0.1f);

    private Image background;

    @Override
    public void show() {
        super.show();

        if (!backgroundSong.isPlayingSong()) {
            backgroundSong.playSong();
        }

        background = new Image(FilePaths.BACKGROUNDS + "loadingScreen.png");
        background.setFullScreen();
    }
}
