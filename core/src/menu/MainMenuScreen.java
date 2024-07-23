package menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import game.GameScreen;
import menu.config.OptionsScreen;
import utilities.Options;
import utilities.*;
import utilities.io.Song;

public final class MainMenuScreen extends BasicMainMenuScreen {
    private final Options options;

    public MainMenuScreen() {
        super();
        options = new Options(
                20f,
                new Button(Fonts.DEFAULT, "PLAY", this::play),
                new Button(Fonts.DEFAULT, "OPTIONS", () -> Render.setScreen(new OptionsScreen())),
                new Button(Fonts.DEFAULT, "QUIT" , () -> Gdx.app.exit())
        );
    }

    private void play() {
        backgroundSong.fadeOut(FADE_TIME);
        Render.setScreen(new GameScreen());
    }

    @Override
    public void show() {
        super.show();
        options.setAlign(AlignMode.CENTERED);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        options.update();
    }

    @Override
    public void resize(int w, int h) {
        super.resize(w, h);
        options.center();
    }
}
