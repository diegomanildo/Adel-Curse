package screens;

import characters.Adel;
import com.badlogic.gdx.Input;
import utilities.FilePaths;
import utilities.Image;
import utilities.Render;
import utilities.io.Song;

public final class GameScreen extends Screen {
    private Adel adel;
    private Image background;
    private Song song;

    @Override
    public void show() {
        super.show();

        adel = new Adel();
        adel.center();

        background = new Image(FilePaths.BACKGROUNDS + "backgroundsAux.png");
        background.setSize(true);

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
}
