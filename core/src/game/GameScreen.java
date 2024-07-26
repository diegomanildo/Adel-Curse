package game;

import com.badlogic.gdx.Input;
import game.characters.playable.Adel;
import game.rooms.StoneRoom;
import menu.MainMenuScreen;
import utilities.Render;
import utilities.Screen;
import utilities.io.Song;

public final class GameScreen extends Screen {
    private final StoneRoom room;

    private final Adel adel;
    private final Song song;

    public GameScreen() {
        room = new StoneRoom();
        adel = new Adel();
        song = new Song("Music", "game/music/UndeadIntro.mp3", "game/music/Undead.mp3");
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        Render.b.setProjectionMatrix(Render.camera.combined);
        Render.sr.setProjectionMatrix(Render.camera.combined);

        if (!song.isPlaying()) {
            song.fadeIn(FADE_TIME);
        }

        if (Render.io.isKeyPressed(Input.Keys.ESCAPE)) {
            song.fadeOut(FADE_TIME);
            MainMenuScreen.backgroundSong.fadeIn(FADE_TIME);
            Render.setScreen(new MainMenuScreen());
        }

        adel.move();

        if (!adel.collidesWith(Render.camera.getHitbox())) {
            moveCamera();
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

    @Override
    public void resize(int w, int h) {
        super.resize(w, h);
        adel.setX((room.getWidth() - adel.getWidth()) / 2f);
        adel.setY((room.getHeight() - adel.getHeight()) / 2f);
    }
}
