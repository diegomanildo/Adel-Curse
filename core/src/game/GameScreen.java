package game;

import game.entities.characters.playables.Adel;
import utilities.Render;
import utilities.Screen;
import utilities.io.Song;

public final class GameScreen extends Screen {
    private final Adel adel;
    private final Song song;

    public GameScreen() {
        adel = new Adel();
        song = new Song("Music", "game/music/UndeadIntro.mp3", "game/music/Undead.mp3");
        stage.addActor(adel);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
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
