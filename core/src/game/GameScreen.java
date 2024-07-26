package game;

import com.badlogic.gdx.Input;
import game.characters.playable.Adel;
import game.rooms.StoneRoom;
import game.utilities.Camera2D;
import menu.MainMenuScreen;
import utilities.Render;
import utilities.Screen;
import utilities.io.Song;

public final class GameScreen extends Screen {
    private final Adel adel;
    private final Song song;

    private static Camera2D camera;
    private final StoneRoom room;

    public GameScreen() {
        adel = new Adel();
        song = new Song("Music", "game/music/UndeadIntro.mp3", "game/music/Undead.mp3");
        camera = new Camera2D(Render.screenSize.width, Render.screenSize.height);
        room = new StoneRoom(camera);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if (!song.isPlaying()) {
            song.fadeIn(FADE_TIME);
        }

        if (Render.io.isKeyPressed(Input.Keys.ESCAPE)) {
            song.fadeOut(FADE_TIME);
            MainMenuScreen.backgroundSong.fadeIn(FADE_TIME);
            Render.setScreen(new MainMenuScreen());
        }

        adel.move();
    }

    private void moveCamera() {
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

    @Override
    public void resize(int w, int h) {
        super.resize(w, h);
        adel.setSize(100f, 100f);
        adel.center();
    }

    public static Camera2D getCamera() {
        return camera;
    }
}
