package game;

import com.badlogic.gdx.Input;
import game.entities.characters.playables.Adel;
import game.utilities.Camera2D;
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
        Camera2D camera = (Camera2D) Render.camera;

        if (adel.getX() < camera.getLeft()) {
            camera.moveTo(camera.position.x - camera.viewportWidth, camera.position.y, FADE_TIME / 2f);
        } else if (adel.getX() > camera.getRight()) {
            camera.moveTo(camera.position.x + camera.viewportWidth, camera.position.y, FADE_TIME / 2f);
        }

        if (adel.getY() < camera.getBottom()) {
            camera.moveTo(camera.position.x, camera.position.y - camera.viewportHeight, FADE_TIME / 2f);
        } else if (adel.getY() > camera.getTop()) {
            camera.moveTo(camera.position.x, camera.position.y + camera.viewportHeight, FADE_TIME / 2f);
        }
    }
}
