package game;

import com.badlogic.gdx.Input;
import game.characters.Adel;
import menu.MainMenuScreen;
import menu.Screen;
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

        song = new Song("gameMusic/Undead.mp3", 0.1f);
        song.playSong(true);
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
