package game;

import com.badlogic.gdx.Input;
import game.characters.Adel;
import menu.MainMenuScreen;
import utilities.Screen;
import utilities.Render;
import utilities.io.Song;

public final class GameScreen extends Screen {
    private Adel adel;
    private Song song;

    @Override
    public void show() {
        super.show();

        adel = new Adel();
        adel.setSize(100f, 100f);
        adel.center();

        song = new Song("game/music/UndeadIntro.mp3", "game/music/Undead.mp3");
        song.setVolume(0.1f);
        song.fadeIn(FADE_TIME);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if (Render.io.isKeyPressed(Input.Keys.ESCAPE)) {
            Render.setScreen(new MainMenuScreen());
        }

        adel.move();
    }

    @Override
    public void dispose() {
        super.dispose();
        song.dispose();
    }
}
