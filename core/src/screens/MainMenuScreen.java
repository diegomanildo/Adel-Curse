package screens;

import com.badlogic.gdx.Gdx;
import utilities.Options;
import utilities.*;
import utilities.io.Song;

public final class MainMenuScreen extends Screen {
    private Image background;
    private Song menuSong;
    private Options options;

    @Override
    public void show() {
        super.show();
        background = new Image(FilePaths.BACKGROUNDS + "loadingScreen.png");
        menuSong = new Song("menuMusic.mp3", 0.1f);

        options = new Options(
                20f,
                new Button(Fonts.GOHU_FONT, "1 PLAYER", this::onePlayer),
                new Button(Fonts.GOHU_FONT, "2 PLAYER", this::onePlayer),
                new Button(Fonts.GOHU_FONT, "OPTIONS", this::options),
                new Button(Fonts.GOHU_FONT, "QUIT" , this::quit)
        );

        background.setSize(true);
        menuSong.playSong(true);
    }

    private void onePlayer() {
        Render.setScreen(new GameScreen());
        menuSong.stopSong();
    }

    private void options() {
        Render.setScreen(new OptionsScreen());
    }

    private void quit() {
        Gdx.app.exit();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Render.b.begin();

        background.draw();

        options.draw();

        Render.b.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        menuSong.dispose();
        options.dispose();
    }
}
