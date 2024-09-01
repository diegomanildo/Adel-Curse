package game;

import game.entities.GameEntity;
import game.entities.characters.Character;
import game.entities.characters.playables.Adel;
import game.entities.characters.playables.Playable;
import game.levels.Level;
import game.levels.Level1;
import game.map.Door;
import game.utilities.*;
import utilities.Render;
import utilities.SubScreen;
import utilities.Timer;

public final class Game extends SubScreen {
    public static Entities entities;

    private final Timer timer;
    private final Level1 level;
    private final Adel adel;
    private static final float TRANSITION_TIME = FADE_TIME / 3f;

    public Game() {
        super();
        timer = new Timer();
        entities = new Entities();

        level = new Level1();
        adel = new Adel();
        adel.setPosition(level.getInitX() - adel.getWidth() / 2f, level.getInitY() - adel.getHeight() / 2f);

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
        timer.start();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        correctPositions();
        checkDoors();
        if (!adel.collidesWith(level.getCamera().getHitbox())) {
            moveCamera();
        }
    }

    private void correctPositions() {
        Hitbox roomHitbox = level.getHitbox();
        for (Character c : entities.getCharacters()) {
            if (c == adel && Render.isDebugging()) {
                continue;
            }
            c.correctPosition(roomHitbox);
        }
    }

    private void checkDoors() {
        for (Playable player : entities.getPlayers()) {
            for (Door door : level.getDoors()) {
                if (player.getBounds().collidesWith(door.getHitbox())) {
                    GameScreen.chat.createTiny("Press " + Controls.getCharacter(GameAction.INTERACT));

                    if (!level.getCamera().isMoving() && Controls.isJustPressed(GameAction.INTERACT)) {
                        moveCamera(door.getDirection());
                    }
                } else {
                    GameScreen.chat.removeTiny();
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

    private void moveCamera() {
        Camera2D camera = level.getCamera();
        if (camera.isMoving())
            return;

        if (adel.getY() < camera.getBottom()) {
            moveCamera(Direction.DOWN);
        } else if (adel.getY() > camera.getTop()) {
            moveCamera(Direction.UP);
        }

        if (adel.getX() < camera.getLeft()) {
            moveCamera(Direction.LEFT);
        } else if (adel.getX() > camera.getRight()) {
            moveCamera(Direction.RIGHT);
        }
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
                adel.setPosition(adel.getX(), camera.getTop() - adel.getHeight() / 2f);
            });
        });
    }

    private void moveCameraUp(Camera2D camera) {
        camera.moveTo(camera.position.x, camera.position.y + camera.viewportHeight, TRANSITION_TIME, () -> {
            camera.setPosition(camera.position.x, camera.position.y - 2f * camera.viewportHeight);
            level.changeRoom(Direction.UP);
            camera.moveTo(camera.position.x, camera.position.y + camera.viewportHeight, TRANSITION_TIME, () -> {
                adel.setPosition(adel.getX(), camera.getBottom() + adel.getHeight() / 2f);
            });
        });
    }

    private void moveCameraRight(Camera2D camera) {
        camera.moveTo(camera.position.x + camera.viewportWidth, camera.position.y, TRANSITION_TIME, () -> {
            camera.setPosition(camera.position.x - 2f * camera.viewportWidth, camera.position.y);
            level.changeRoom(Direction.RIGHT);
            camera.moveTo(camera.position.x + camera.viewportWidth, camera.position.y, TRANSITION_TIME, () -> {
                adel.setPosition(camera.getLeft() + adel.getWidth() / 2f, adel.getY());
            });
        });
    }

    private void moveCameraLeft(Camera2D camera) {
        camera.moveTo(camera.position.x - camera.viewportWidth, camera.position.y, TRANSITION_TIME, () -> {
            camera.setPosition(camera.position.x + 2f * camera.viewportWidth, camera.position.y);
            level.changeRoom(Direction.LEFT);
            camera.moveTo(camera.position.x - camera.viewportWidth, camera.position.y, TRANSITION_TIME, () -> {
                adel.setPosition(camera.getRight() - adel.getWidth() / 2f, adel.getY());
            });
        });
    }

    public Playable getPlayer() {
        return adel;
    }

    public Level getLevel() {
        return level;
    }

    public Timer getTimer() {
        return timer;
    }
}
