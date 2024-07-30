package game;

import com.badlogic.gdx.Input;
import game.entities.characters.playables.Adel;
import menu.BasicMainMenuScreen;
import menu.MainMenuScreen;
import utilities.Render;
import utilities.Screen;
import utilities.io.Song;

public final class GameScreen extends Screen {
    private final Adel adel;
    private final Song song;

    public GameScreen() {
        adel = new Adel();
        song = new Song("Music", "game/music/UndeadIntro.mp3", "game/music/Undead.mp3");

        adel.setPosition(Render.screenSize.width / 2f, Render.screenSize.height / 2f);

        stage.addActor(adel);
    }

    @Override
    public void show() {
        super.show();
        BasicMainMenuScreen.backgroundSong.fadeOut(FADE_TIME);
        song.fadeIn(FADE_TIME, true);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if (stage.isKeyPressed(Input.Keys.ESCAPE)) {
            song.fadeOut(FADE_TIME);
            BasicMainMenuScreen.backgroundSong.fadeIn(FADE_TIME, true);
            Render.setScreen(new MainMenuScreen());
        }
    }

    private void moveCamera() {
        if (adel.getX() < Render.camera.getLeft()) {
            Render.camera.moveTo(Render.camera.position.x - Render.camera.viewportWidth, Render.camera.position.y, FADE_TIME / 2f);
        } else if (adel.getX() > Render.camera.getRight()) {
            Render.camera.moveTo(Render.camera.position.x + Render.camera.viewportWidth, Render.camera.position.y, FADE_TIME / 2f);
        }

        if (adel.getY() < Render.camera.getBottom()) {
            Render.camera.moveTo(Render.camera.position.x, Render.camera.position.y - Render.camera.viewportHeight, FADE_TIME / 2f);
        } else if (adel.getY() > Render.camera.getTop()) {
            Render.camera.moveTo(Render.camera.position.x, Render.camera.position.y + Render.camera.viewportHeight, FADE_TIME / 2f);
        }
    }
}
