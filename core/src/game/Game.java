package game;

import game.entities.GameEntity;
import game.entities.characters.enemies.Skeleton;
import game.entities.characters.playables.Adel;
import game.entities.characters.playables.Playable;
import game.utilities.Camera2D;
import menu.BasicMainMenuScreen;
import menu.MainMenuScreen;
import utilities.Log;
import utilities.Render;
import utilities.SubScreen;
import utilities.io.Song;

import java.util.ArrayList;

public final class Game extends SubScreen {
    public static ArrayList<GameEntity> entities;
    private final Adel adel;
    private final Song song;

    public Game() {
        super(true);
        if (Render.isDebugging()) {
            stage.getCamera().zoom = 2f;
        }
        entities = new ArrayList<>();

        adel = new Adel();
        adel.setPosition(Render.screenSize.width / 2f, Render.screenSize.height / 2f);

        song = new Song("Music", "game/music/UndeadIntro.mp3", "game/music/Undead.mp3");

        Skeleton skeleton = new Skeleton();
        skeleton.setPosition(40f, adel.getY());

        stage.addActor(skeleton);
        stage.addActor(adel);

        stage.getActors().forEach(a -> {
            if (a instanceof GameEntity) {
                entities.add((GameEntity) a);
            }
        });
    }

    @Override
    public void show() {
        super.show();
        song.fadeIn(FADE_TIME, true);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if (!adel.collidesWith(stage.getCamera().getHitbox())) {
            moveCamera();
        }
    }

    @Override
    public void pause() {
        super.pause();
        entities.forEach(GameEntity::pause);
    }

    @Override
    public void resume() {
        super.resume();
        entities.forEach(GameEntity::resume);
    }

    private void moveCamera() {
        Camera2D camera = stage.getCamera();

        if (adel.getX() < camera.getLeft()) {
            camera.moveTo(camera.position.x - camera.viewportWidth, camera.position.y, FADE_TIME / 2f);
            Log.log("Moving camera left");
        } else if (adel.getX() > camera.getRight()) {
            camera.moveTo(camera.position.x + camera.viewportWidth, camera.position.y, FADE_TIME / 2f);
            Log.log("Moving camera right");
        }

        if (adel.getY() < camera.getBottom()) {
            camera.moveTo(camera.position.x, camera.position.y - camera.viewportHeight, FADE_TIME / 2f);
            Log.log("Moving camera down");
        } else if (adel.getY() > camera.getTop()) {
            camera.moveTo(camera.position.x, camera.position.y + camera.viewportHeight, FADE_TIME / 2f);
            Log.log("Moving camera up");
        }
    }

    private void exit() {
        song.fadeOut(FADE_TIME);
        BasicMainMenuScreen.backgroundSong.fadeIn(FADE_TIME, true);
        Render.setScreen(new MainMenuScreen());
    }

    public Playable getPlayer() {
        return adel;
    }
}
