package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import gameUtilities.Options;
import utilities.*;
import utilities.io.Audio;
import utilities.io.Song;

public final class MainMenuScreen implements Screen {

    private final Image background;
    private final Song menuSong;
    private final Options menuOptions;

    public MainMenuScreen() {
        background = new Image(FilePaths.BACKGROUNDS + "loadingScreen.png");
        menuSong = new Song("menuMusic.mp3", 0.1f);

        menuOptions = new Options(
                new Audio("mouseHover.mp3"),

                new Button(Fonts.GOHU_FONT, "1 PLAYER", this::onePlayer),
                new Button(Fonts.GOHU_FONT, "2 PLAYER", this::onePlayer),
                new Button(Fonts.GOHU_FONT, "OPTIONS", this::options),
                new Button(Fonts.GOHU_FONT, "QUIT", this::quit)
        );
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
    public void show() {
        background.setSize(true);
        menuSong.playSong(true);
    }

    @Override
    public void render(float delta) {
        Render.b.begin();

        background.draw();

        menuOptions.draw();

        Render.b.end();
    }

    @Override
    public void resize(int w, int h) {}
    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}
    @Override
    public void dispose() {}
}
