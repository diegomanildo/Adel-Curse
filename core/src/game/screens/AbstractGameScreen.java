package game.screens;

import game.Game;
import game.entities.GameEntity;
import game.entities.characters.playables.Adel;
import game.entities.characters.playables.Playable;
import game.levels.Level;
import game.map.Door;
import game.net.GameData;
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

    protected void moveCamera(Direction direction) {
        Camera2D camera = level.getCamera();

        switch (direction) {
            case DOWN:
                camera.moveTo(camera.position.x, camera.position.y - camera.viewportHeight, TRANSITION_TIME, () -> {
                    camera.setPosition(camera.position.x, camera.position.y + 2f * camera.viewportHeight);
                    level.changeRoom(Direction.DOWN);
                    camera.moveTo(camera.position.x, camera.position.y - camera.viewportHeight, TRANSITION_TIME, () -> {
                        getPlayer().setPosition(getPlayer().getX(), camera.getTop() - getPlayer().getHeight() / 2f);
                    });
                });
                break;
            case UP:
                camera.moveTo(camera.position.x, camera.position.y + camera.viewportHeight, TRANSITION_TIME, () -> {
                    camera.setPosition(camera.position.x, camera.position.y - 2f * camera.viewportHeight);
                    level.changeRoom(Direction.UP);
                    camera.moveTo(camera.position.x, camera.position.y + camera.viewportHeight, TRANSITION_TIME, () -> {
                        getPlayer().setPosition(getPlayer().getX(), camera.getBottom() + getPlayer().getHeight() / 2f);
                    });
                });
                break;
            case RIGHT:
                camera.moveTo(camera.position.x + camera.viewportWidth, camera.position.y, TRANSITION_TIME, () -> {
                    camera.setPosition(camera.position.x - 2f * camera.viewportWidth, camera.position.y);
                    level.changeRoom(Direction.RIGHT);
                    camera.moveTo(camera.position.x + camera.viewportWidth, camera.position.y, TRANSITION_TIME, () -> {
                        getPlayer().setPosition(camera.getLeft() + getPlayer().getWidth() / 2f, getPlayer().getY());
                    });
                });
                break;
            case LEFT:
                camera.moveTo(camera.position.x - camera.viewportWidth, camera.position.y, TRANSITION_TIME, () -> {
                    camera.setPosition(camera.position.x + 2f * camera.viewportWidth, camera.position.y);
                    level.changeRoom(Direction.LEFT);
                    camera.moveTo(camera.position.x - camera.viewportWidth, camera.position.y, TRANSITION_TIME, () -> {
                        getPlayer().setPosition(camera.getRight() - getPlayer().getWidth() / 2f, getPlayer().getY());
                    });
                });
                break;
            default:
                throw new RuntimeException("Invalid direction: " + direction);
        }
    }

    public Playable getPlayer() {
        if (getPlayers().isEmpty()) {
            return new Adel();
        } else if (this instanceof OnePlayerGameScreen) {
            return getPlayers().get(0);
        } else {
            return getPlayers().get(GameData.clientNumber);
        }
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
