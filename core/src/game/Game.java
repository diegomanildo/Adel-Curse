package game;

import game.entities.GameEntity;
import game.entities.characters.playables.Adel;
import game.entities.characters.playables.Playable;
import game.levels.Level;
import game.levels.Level1;
import game.utilities.Camera2D;
import game.utilities.Direction;
import game.utilities.Entities;
import utilities.SubScreen;
import utilities.Timer;
import utilities.io.Song;

public final class Game extends SubScreen {
    public static Entities entities;

    private final Timer timer;
    public static ChatScreen chat;
    private final Level1 level;
    private final Adel adel;
    public static Song song;

    public Game(ChatScreen chat) {
        super();
        timer = new Timer();
        Game.chat = chat;
        entities = new Entities();

        level = new Level1();
        adel = new Adel();
        adel.setPosition(level.getInitX(), level.getInitY());

        song = new Song("Music", "game/music/UndeadIntro.mp3", "game/music/Undead.mp3");
        
        stage.addActor(level);
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
        timer.start();
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
        timer.pause();
    }

    @Override
    public void resume() {
        super.resume();
        entities.forEach(GameEntity::resume);
        timer.resume();
    }

    private void moveCamera() {
        Camera2D camera = level.getCamera();
        if (camera.isMoving())
            return;
        float transitionTime = FADE_TIME / 6f;

        if (adel.getY() < camera.getBottom()) {
            moveCameraDown(camera, transitionTime);
        } else if (adel.getY() > camera.getTop()) {
            moveCameraUp(camera, transitionTime);
        }

        if (adel.getX() < camera.getLeft()) {
            moveCameraLeft(camera, transitionTime);
        } else if (adel.getX() > camera.getRight()) {
            moveCameraRight(camera, transitionTime);
        }
    }

    private void moveCameraDown(Camera2D camera, float transitionTime) {
        camera.moveTo(camera.position.x, camera.position.y - camera.viewportHeight, transitionTime, () -> {
            camera.setPosition(camera.position.x, camera.position.y + 2f * camera.viewportHeight);
            level.changeRoom(Direction.DOWN);
            camera.moveTo(camera.position.x, camera.position.y - camera.viewportHeight, transitionTime, () -> {
                adel.setPosition(adel.getX(), camera.getTop() - adel.getHeight() / 2f);
            });
        });
    }

    private void moveCameraUp(Camera2D camera, float transitionTime) {
        camera.moveTo(camera.position.x, camera.position.y + camera.viewportHeight, transitionTime, () -> {
            camera.setPosition(camera.position.x, camera.position.y - 2f * camera.viewportHeight);
            level.changeRoom(Direction.UP);
            camera.moveTo(camera.position.x, camera.position.y + camera.viewportHeight, transitionTime, () -> {
                adel.setPosition(adel.getX(), camera.getBottom() + adel.getHeight() / 2f);
            });
        });
    }

    private void moveCameraRight(Camera2D camera, float transitionTime) {
        camera.moveTo(camera.position.x + camera.viewportWidth, camera.position.y, transitionTime, () -> {
            camera.setPosition(camera.position.x - 2f * camera.viewportWidth, camera.position.y);
            level.changeRoom(Direction.RIGHT);
            camera.moveTo(camera.position.x + camera.viewportWidth, camera.position.y, transitionTime, () -> {
                adel.setPosition(camera.getLeft() + adel.getWidth() / 2f, adel.getY());
            });
        });
    }

    private void moveCameraLeft(Camera2D camera, float transitionTime) {
        camera.moveTo(camera.position.x - camera.viewportWidth, camera.position.y, transitionTime, () -> {
            camera.setPosition(camera.position.x + 2f * camera.viewportWidth, camera.position.y);
            level.changeRoom(Direction.LEFT);
            camera.moveTo(camera.position.x - camera.viewportWidth, camera.position.y, transitionTime, () -> {
                adel.setPosition(camera.getRight() - adel.getWidth() / 2f, adel.getY());
            });
        });
    }

    public Playable getPlayer() {
        return adel;
    }

    public Song getSong() {
        return song;
    }

    public Level getLevel() {
        return level;
    }

    public Timer getTimer() {
        return timer;
    }
}
