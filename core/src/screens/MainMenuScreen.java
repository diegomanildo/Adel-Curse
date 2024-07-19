package screens;

import com.badlogic.gdx.Gdx;
import screens.config.OptionsScreen;
import utilities.Options;
import utilities.*;
import utilities.io.Song;

public final class MainMenuScreen extends MainMenuScreenBasic {
    private Song menuSong;
    private Options options;

    @Override
    public void show() {
        super.show();
        menuSong = new Song("menuMusic.mp3", 0.1f);

        options = new Options(
                20f,
                new Button(Fonts.DEFAULT, "PLAY", this::play),
                new Button(Fonts.DEFAULT, "OPTIONS", () -> Render.setScreen(new OptionsScreen())),
                new Button(Fonts.DEFAULT, "QUIT" , () -> Gdx.app.exit())
        );

        options.setAlign(AlignMode.CENTERED);
        options.center();
    }

    private void play() {
        Render.setScreen(new GameScreen());
        backgroundSong.stopSong();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        options.update();
    }

    @Override
    public void dispose() {
        super.dispose();
        menuSong.dispose();
    }
}
