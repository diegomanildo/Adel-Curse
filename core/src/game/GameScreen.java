package game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Matrix4;
import game.characters.Adel;
import menu.MainMenuScreen;
import utilities.Screen;
import utilities.Render;
import utilities.io.Song;

public final class GameScreen extends Screen {
    private final Adel adel;
    private final Song song;

    private final Camera2D camera;

    public GameScreen() {
        adel = new Adel();
        song = new Song("game/music/UndeadIntro.mp3", "game/music/Undead.mp3");
        camera = new Camera2D(Render.screenSize.width, Render.screenSize.height);
    }

    @Override
    public void show() {
        super.show();
        camera.setPosition(camera.viewportWidth / 2f, camera.viewportHeight / 2f);
        camera.update();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        Render.b.setProjectionMatrix(camera.combined);
        Render.sr.setProjectionMatrix(camera.combined);

        if (!song.isPlaying()) {
            song.fadeIn(FADE_TIME);
        }

        if (Render.io.isKeyPressed(Input.Keys.ESCAPE)) {
            song.fadeOut(FADE_TIME);
            MainMenuScreen.backgroundSong.fadeIn(FADE_TIME);
            Render.setScreen(new MainMenuScreen());
        }

        adel.move();

        if (!adel.collidesWith(camera.getHitbox())) {
            fixCamera();
        }
    }

    private void fixCamera() {
        if (adel.getX() < camera.getLeft()) {
            camera.transitionCamera(camera.position.x - camera.viewportWidth, camera.position.y, FADE_TIME / 2f);
        } else if (adel.getX() > camera.getRight()) {
            camera.transitionCamera(camera.position.x + camera.viewportWidth, camera.position.y, FADE_TIME / 2f);
        }

        if (adel.getY() < camera.getBottom()) {
            camera.transitionCamera(camera.position.x, camera.position.y - camera.viewportHeight, FADE_TIME / 2f);
        } else if (adel.getY() > camera.getTop()) {
            camera.transitionCamera(camera.position.x, camera.position.y + camera.viewportHeight, FADE_TIME / 2f);
        }
    }


    @Override
    public void resize(int w, int h) {
        super.resize(w, h);
        adel.setSize(100f, 100f);
        adel.center();
    }

    @Override
    public void dispose() {
        super.dispose();
        Render.b.setProjectionMatrix(new Matrix4());
        Render.sr.setProjectionMatrix(new Matrix4());
    }
}
