package game;

import game.entities.GameEntity;
import game.entities.characters.playables.Adel;
import game.entities.characters.playables.Playable;
import game.levels.Level;
import game.levels.Level1;
import game.utilities.Camera2D;
import game.utilities.Direction;
import game.utilities.Entities;
import game.utilities.Hitbox;
import utilities.SubScreen;
import utilities.Timer;

public final class Game extends SubScreen {
    public static Entities entities;

    private final Timer timer;
    private final Level1 level;
    private final Adel adel;

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
        if (!adel.collidesWith(level.getCamera().getHitbox())) {
            moveCamera();
        }
    }

    private void correctPositions() {
        Hitbox roomHitbox = level.getHitbox();

        float left = roomHitbox.x;
        float right = roomHitbox.x + roomHitbox.width;
        float bottom = roomHitbox.y;
        float top = roomHitbox.y + roomHitbox.height;

        entities.forEach(e -> {
            // Verifica y ajusta la posición en el eje X
            if (e.getX() < left) {
                e.setPosition(left, e.getY());
            } else if (e.getX() + e.getWidth() > right) {
                e.setPosition(right - e.getWidth(), e.getY());
            }

            // Verifica y ajusta la posición en el eje Y
            if (e.getY() < bottom) {
                e.setPosition(e.getX(), bottom);
            } else if (e.getY() + e.getHeight() > top) {
                e.setPosition(e.getX(), top - e.getHeight());
            }
        });
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
        float transitionTime = FADE_TIME / 3f;

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

    public Level getLevel() {
        return level;
    }

    public Timer getTimer() {
        return timer;
    }
}
