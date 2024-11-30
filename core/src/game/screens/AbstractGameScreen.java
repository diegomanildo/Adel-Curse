package game.screens;

import com.badlogic.gdx.utils.Array;
import game.Game;
import game.entities.GameEntity;
import game.entities.characters.Character;
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
        ArrayList<Character> characters = getEntities().getCharacters();

        for (int i = 0; i < characters.size(); i++) {
            characters.get(i).correctPosition(roomHitbox);

            for (int j = i + 1; j < characters.size(); j++) {
                Character c1 = characters.get(i);
                Character c2 = characters.get(j);

                if (c1.getHitbox().collidesWith(c2.getHitbox())) {
                    resolveCollision(c1, c2);
                }
            }
        }
    }

    private void resolveCollision(Character c1, Character c2) {
        Hitbox hitbox1 = c1.getHitbox();
        Hitbox hitbox2 = c2.getHitbox();

        float centerX1 = hitbox1.x + hitbox1.width / 2;
        float centerY1 = hitbox1.y + hitbox1.height / 2;
        float centerX2 = hitbox2.x + hitbox2.width / 2;
        float centerY2 = hitbox2.y + hitbox2.height / 2;

        float overlapX = Math.min(hitbox1.getRight(), hitbox2.getRight()) - Math.max(hitbox1.getLeft(), hitbox2.getLeft());
        float overlapY = Math.min(hitbox1.getTop(), hitbox2.getTop()) - Math.max(hitbox1.getBottom(), hitbox2.getBottom());

        if (overlapX < overlapY) {
            if (centerX1 < centerX2) {
                c1.setPosition(c1.getX() - overlapX / 2, c1.getY());
                c2.setPosition(c2.getX() + overlapX / 2, c2.getY());
            } else {
                c1.setPosition(c1.getX() + overlapX / 2, c1.getY());
                c2.setPosition(c2.getX() - overlapX / 2, c2.getY());
            }
        } else {
            if (centerY1 < centerY2) {
                c1.setPosition(c1.getX(), c1.getY() - overlapY / 2);
                c2.setPosition(c2.getX(), c2.getY() + overlapY / 2);
            } else {
                c1.setPosition(c1.getX(), c1.getY() + overlapY / 2);
                c2.setPosition(c2.getX(), c2.getY() - overlapY / 2);
            }
        }
    }

    private void checkDoors() {
        level.getHitbox();
        for (Door door : level.getDoors()) {
            if (getPlayer().getBounds().collidesWith(door.getHitbox())) {
                Game.chat.createTiny(door.getDirection().name(), "Press " + Controls.getCharacter(GameAction.INTERACT));

                if (!level.getCamera().isMoving() && Controls.isJustPressed(GameAction.INTERACT) ) {
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

    @Override
    public void pause() {
        super.pause();
        getEntities().forEach(GameEntity::pause);
        timer.pause();
    }

    @Override
    public void resume() {
        super.resume();
        getEntities().forEach(GameEntity::resume);
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
                        getPlayers().forEach(p -> p.setPosition(p.getX(), camera.getTop() - p.getHeight() / 2f));
                    });
                });
                break;
            case UP:
                camera.moveTo(camera.position.x, camera.position.y + camera.viewportHeight, TRANSITION_TIME, () -> {
                    camera.setPosition(camera.position.x, camera.position.y - 2f * camera.viewportHeight);
                    level.changeRoom(Direction.UP);
                    camera.moveTo(camera.position.x, camera.position.y + camera.viewportHeight, TRANSITION_TIME, () -> {
                        getPlayers().forEach(p -> p.setPosition(p.getX(), camera.getBottom() + p.getHeight() / 2f));
                    });
                });
                break;
            case RIGHT:
                camera.moveTo(camera.position.x + camera.viewportWidth, camera.position.y, TRANSITION_TIME, () -> {
                    camera.setPosition(camera.position.x - 2f * camera.viewportWidth, camera.position.y);
                    level.changeRoom(Direction.RIGHT);
                    camera.moveTo(camera.position.x + camera.viewportWidth, camera.position.y, TRANSITION_TIME, () -> {
                        getPlayers().forEach(p -> p.setPosition(camera.getLeft() + p.getWidth() / 2f, p.getY()));
                    });
                });
                break;
            case LEFT:
                camera.moveTo(camera.position.x - camera.viewportWidth, camera.position.y, TRANSITION_TIME, () -> {
                    camera.setPosition(camera.position.x + 2f * camera.viewportWidth, camera.position.y);
                    level.changeRoom(Direction.LEFT);
                    camera.moveTo(camera.position.x - camera.viewportWidth, camera.position.y, TRANSITION_TIME, () -> {
                        getPlayers().forEach(p -> p.setPosition(camera.getRight() - p.getWidth() / 2f, p.getY()));
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
        }

        if (this instanceof OnePlayerGameScreen) {
            return getPlayers().get(0);
        } else {
            try {
                return getPlayers().get(GameData.clientNumber);
            } catch (IndexOutOfBoundsException e) {
                return new Adel();
            }
        }
    }

    public Entities getEntities() {
        Entities entities = new Entities();

        for (int i = 0; i < stage.getActors().size; i++) {
            com.badlogic.gdx.scenes.scene2d.Actor actor = stage.getActors().get(i);
            if (actor instanceof GameEntity) {
                entities.add((GameEntity) actor);
            }
        }

        return entities;
    }

    public Array<Playable> getPlayers() {
        return getEntities().getPlayers();
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
        getEntities().forEach(Actor::dispose);
    }
}
