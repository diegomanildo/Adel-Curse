package screens;

import com.badlogic.gdx.Gdx;
import screens.config.OptionsScreen;
import utilities.Options;
import utilities.*;
import utilities.io.Song;

public final class MainMenuScreen extends Screen {
    private Song menuSong;
    private Options options;

    @Override
    public void show() {
        super.show();
        menuSong = new Song("menuMusic.mp3", 0.1f);

        options = new Options(
                20f,
                new Button(Fonts.DEFAULT, "1 PLAYER", this::onePlayer),
                new Button(Fonts.DEFAULT, "2 PLAYER", this::onePlayer),
                new Button(Fonts.DEFAULT, "OPTIONS", this::options),
                new Button(Fonts.DEFAULT, "QUIT" , this::quit)
        );

        options.setAlign(AlignMode.CENTERED);
        options.center();
    }

    private void onePlayer() {
        Render.setScreen(new GameScreen());
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
        options.draw();
        Render.b.end();
    }

    @Override
    public void resize(int w, int h) {
        super.resize(w, h);

    }

    @Override
    public void dispose() {
        super.dispose();
        menuSong.dispose();
        options.dispose();
    }
}
