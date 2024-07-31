package game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
        super();
        adel = new Adel();
        song = new Song("Music", "game/music/UndeadIntro.mp3", "game/music/Undead.mp3");

        adel.setPosition(Render.screenSize.width / 2f, Render.screenSize.height / 2f);

        stage.addActor(adel);

        if (Render.isDebugging()) {
            ((OrthographicCamera) stage.getCamera()).zoom = 3f;
        }
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

        if (!adel.collidesWith(Camera2D.getBounds(Render.camera))) {
            moveCamera();
        }
    }

    private void moveCamera() {
        OrthographicCamera camera = Render.camera;

        if (adel.getX() < (camera.position.x - camera.viewportWidth / 2f)) {
            Camera2D.moveTo(camera, camera.position.x - camera.viewportWidth, camera.position.y, FADE_TIME / 2f);
        } else if (adel.getX() > (camera.position.x + camera.viewportWidth / 2f)) {
            Camera2D.moveTo(camera, camera.position.x + camera.viewportWidth, camera.position.y, FADE_TIME / 2f);
        }

        if (adel.getY() < (camera.position.y - camera.viewportHeight / 2f)) {
            Camera2D.moveTo(camera, camera.position.x, camera.position.y - camera.viewportHeight, FADE_TIME / 2f);
        } else if (adel.getY() > (camera.position.y + camera.viewportHeight / 2f)) {
            Camera2D.moveTo(camera, camera.position.x, camera.position.y + camera.viewportHeight, FADE_TIME / 2f);
        }
    }
}
