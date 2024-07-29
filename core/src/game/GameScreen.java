package game;

import game.entities.characters.playables.Adel;
import game.levels.Level1;
import utilities.Render;
import utilities.Screen;
import utilities.io.Song;

public final class GameScreen extends Screen {
    private final Song song;
    private final Level1 level;
    private final Adel adel;

    public GameScreen() {
        if (Render.isDebugging()) {
            Render.camera.zoom = 4f;
        }
        song = new Song("Music", "game/music/UndeadIntro.mp3", "game/music/Undead.mp3");
        level = new Level1();
        adel = new Adel();

        adel.setPosition(level.getInitX() - adel.getWidth() / 2f, level.getInitY() - adel.getHeight() / 2f);
        Render.camera.setPosition(level.getInitX(), level.getInitY());
    }

    @Override
    public void show() {
        super.show();
        song.fadeIn(FADE_TIME, true);
        Render.b.setProjectionMatrix(Render.camera.combined);
        Render.sr.setProjectionMatrix(Render.camera.combined);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
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
}
