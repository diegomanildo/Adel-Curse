package game.screens;

import game.Game;
import game.entities.GameEntity;
import game.entities.characters.playables.Playable;
import game.levels.Level;
import game.map.Door;
import game.utilities.*;
import utilities.Actor;
import utilities.SubScreen;
import utilities.Timer;

import java.util.ArrayList;

public class AbstractGameScreen extends SubScreen {
    protected static final float TRANSITION_TIME = FADE_TIME / 3f;

    public static Entities entities;

    private final Timer timer;
    protected Level level;
    public Func onDoorsChanged;

    public AbstractGameScreen() {
        super();
        timer = new Timer();
    }

    @Override
    public void show() {
        super.show();
        timer.start();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        correctPositions();
        checkDoors();
    }

    private void correctPositions() {
        Hitbox roomHitbox = level.getHitbox();
        entities.getCharacters().forEach(c -> c.correctPosition(roomHitbox));
    }

    private void checkDoors() {
        level.getHitbox();
        for (Playable player : entities.getPlayers()) {
            for (Door door : level.getDoors()) {
                if (player.getBounds().collidesWith(door.getHitbox())) {
                    Game.chat.createTiny(door.getDirection().name(), "Press " + Controls.getCharacter(GameAction.INTERACT));

                    if (!level.getCamera().isMoving() && Controls.isJustPressed(GameAction.INTERACT)) {
                        if (onDoorsChanged != null) {
                            onDoorsChanged.run(door.getDirection());
                        }

                        moveCamera(door.getDirection());
                    }
                } else {
                    Game.chat.removeChat(door.getDirection().name());
                }
            }
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

    private void moveCamera(Direction direction) {
        Camera2D camera = level.getCamera();

        switch (direction) {
            case DOWN:
                moveCameraDown(camera);
                break;
            case UP:
                moveCameraUp(camera);
                break;
            case RIGHT:
                moveCameraRight(camera);
                break;
            case LEFT:
                moveCameraLeft(camera);
                break;
            default:
                throw new RuntimeException("Invalid direction: " + direction);
        }
    }

    private void moveCameraDown(Camera2D camera) {
        camera.moveTo(camera.position.x, camera.position.y - camera.viewportHeight, TRANSITION_TIME, () -> {
            camera.setPosition(camera.position.x, camera.position.y + 2f * camera.viewportHeight);
            level.changeRoom(Direction.DOWN);
            camera.moveTo(camera.position.x, camera.position.y - camera.viewportHeight, TRANSITION_TIME, () -> {
                getPlayers().forEach(p -> p.setPosition(p.getX(), camera.getTop() - p.getHeight() / 2f));
            });
        });
    }

    private void moveCameraUp(Camera2D camera) {
        camera.moveTo(camera.position.x, camera.position.y + camera.viewportHeight, TRANSITION_TIME, () -> {
            camera.setPosition(camera.position.x, camera.position.y - 2f * camera.viewportHeight);
            level.changeRoom(Direction.UP);
            camera.moveTo(camera.position.x, camera.position.y + camera.viewportHeight, TRANSITION_TIME, () -> {
                getPlayers().forEach(p -> p.setPosition(p.getX(), camera.getBottom() + p.getHeight() / 2f));
            });
        });
    }

    private void moveCameraRight(Camera2D camera) {
        camera.moveTo(camera.position.x + camera.viewportWidth, camera.position.y, TRANSITION_TIME, () -> {
            camera.setPosition(camera.position.x - 2f * camera.viewportWidth, camera.position.y);
            level.changeRoom(Direction.RIGHT);
            camera.moveTo(camera.position.x + camera.viewportWidth, camera.position.y, TRANSITION_TIME, () -> {
                getPlayers().forEach(p -> p.setPosition(camera.getLeft() + p.getWidth() / 2f, p.getY()));
            });
        });
    }

    private void moveCameraLeft(Camera2D camera) {
        camera.moveTo(camera.position.x - camera.viewportWidth, camera.position.y, TRANSITION_TIME, () -> {
            camera.setPosition(camera.position.x + 2f * camera.viewportWidth, camera.position.y);
            level.changeRoom(Direction.LEFT);
            camera.moveTo(camera.position.x - camera.viewportWidth, camera.position.y, TRANSITION_TIME, () -> {
                getPlayers().forEach(p -> p.setPosition(camera.getRight() - p.getWidth() / 2f, p.getY()));
            });
        });
    }

    public ArrayList<Playable> getPlayers() {
        return entities.getPlayers();
    }

    public Level getLevel() {
        return level;
    }

    public Timer getTimer() {
        return timer;
    }

    @Override
    public void dispose() {
        super.dispose();
        entities.forEach(Actor::dispose);
    }
}
