package screens;

import com.badlogic.gdx.Gdx;
import gameUtilities.Options;
import utilities.*;
import utilities.io.Song;

public final class MainMenuScreen extends Screen {
    private final Image background;
    private final Song menuSong;
    private final Options options;

    public MainMenuScreen() {
        background = new Image(FilePaths.BACKGROUNDS + "loadingScreen.png");
        menuSong = new Song("menuMusic.mp3", 0.1f);

        options = new Options(
                new Button(Fonts.GOHU_FONT, "1 PLAYER", this::onePlayer),
                new Button(Fonts.GOHU_FONT, "2 PLAYER", this::onePlayer),
                new Button(Fonts.GOHU_FONT, "OPTIONS", this::options)
        );

        background.setSize(true);
        menuSong.playSong(true);
    }

    private void onePlayer() {
        Render.app.setScreen(new GameScreen());
        menuSong.stopSong();
    }

    private void options() {
    }

    private void quit() {
        Gdx.app.exit();
    }

    @Override
    public void render(float delta) {
        Render.b.begin();

        background.draw();

        options.draw();

        Render.b.end();
    }
}
