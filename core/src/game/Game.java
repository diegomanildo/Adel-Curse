package game;

import game.entities.GameEntity;
import game.entities.characters.enemies.Skeleton;
import game.entities.characters.playables.Adel;
import game.entities.characters.playables.Playable;
import game.levels.Level1;
import game.utilities.Camera2D;
import game.utilities.Direction;
import utilities.Log;
import utilities.SubScreen;
import utilities.io.Song;

import java.util.ArrayList;

public final class Game extends SubScreen {
    public static ArrayList<GameEntity> entities;

    private final Level1 level;
    private final Adel adel;
    private final Song song;

    public Game() {
        super();
        entities = new ArrayList<>();

        level = new Level1();
        adel = new Adel();
        adel.setPosition(level.getInitX(), level.getInitY());

        song = new Song("Music", "game/music/UndeadIntro.mp3", "game/music/Undead.mp3");

        Skeleton skeleton = new Skeleton();
        skeleton.setPosition(40f, adel.getY());
        
        stage.addActor(level);
        stage.addActor(skeleton);
        stage.addActor(adel);

        stage.getActors().forEach(actor -> {
            if (actor instanceof GameEntity) {
                entities.add((GameEntity) actor);
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

        if (!adel.collidesWith(level.getCamera().getHitbox())) {
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
        Camera2D camera = level.getCamera();
        if (camera.isMoving())
            return;
        float transitionTime = FADE_TIME / 2f;

        if (adel.getX() < camera.getLeft()) {
            camera.moveTo(camera.position.x - camera.viewportWidth, camera.position.y, transitionTime, () -> {
                camera.setPosition(camera.position.x + 2f * camera.viewportWidth, camera.position.y);
                camera.moveTo(camera.position.x - camera.viewportWidth, camera.position.y, transitionTime, () -> {
                    adel.setPosition(camera.getRight() - adel.getWidth() / 2f, adel.getY());
                    level.changeRoom(Direction.LEFT);
                });
            });
            Log.debug("Moving camera left");
        } else if (adel.getX() > camera.getRight()) {
            camera.moveTo(camera.position.x + camera.viewportWidth, camera.position.y, transitionTime, () -> {
                camera.setPosition(camera.position.x - 2f * camera.viewportWidth, camera.position.y);
                camera.moveTo(camera.position.x + camera.viewportWidth, camera.position.y, transitionTime, () -> {
                    adel.setPosition(camera.getLeft() + adel.getWidth() / 2f, adel.getY());
                    level.changeRoom(Direction.RIGHT);
                });
            });
            Log.debug("Moving camera right");
        }

        if (adel.getY() < camera.getBottom()) {
            camera.moveTo(camera.position.x, camera.position.y - camera.viewportHeight, transitionTime, () -> {
                camera.setPosition(camera.position.x, camera.position.y + 2f * camera.viewportHeight);
                camera.moveTo(camera.position.x, camera.position.y - camera.viewportHeight, transitionTime, () -> {
                    adel.setPosition(adel.getX(), camera.getTop() - adel.getHeight() / 2f);
                    level.changeRoom(Direction.DOWN);
                });
            });
            Log.debug("Moving camera down");
        } else if (adel.getY() > camera.getTop()) {
            camera.moveTo(camera.position.x, camera.position.y + camera.viewportHeight, transitionTime, () -> {
                camera.setPosition(camera.position.x, camera.position.y - 2f * camera.viewportHeight);
                camera.moveTo(camera.position.x, camera.position.y + camera.viewportHeight, transitionTime, () -> {
                    adel.setPosition(adel.getX(), camera.getBottom() + adel.getHeight() / 2f);
                    level.changeRoom(Direction.UP);
                });
            });
            Log.debug("Moving camera up");
        }
    }

    public Playable getPlayer() {
        return adel;
    }

    public Song getSong() {
        return song;
    }
}
