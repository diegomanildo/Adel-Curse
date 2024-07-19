package screens;

import characters.Adel;
import com.badlogic.gdx.Input;
import utilities.FilePaths;
import utilities.Image;
import utilities.Render;
import utilities.io.Song;

public final class GameScreen extends Screen {
    private Adel adel;
    private Song song;

    @Override
    public void show() {
        super.show();

        adel = new Adel();
        adel.center();
        adel.setSize(100f, 100f);

        song = new Song("GameMusic/Undead.mp3", 0.1f);
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
