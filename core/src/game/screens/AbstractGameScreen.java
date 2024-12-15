package game.screens;

import com.badlogic.gdx.utils.Array;
import game.Game;
import game.entities.GameEntity;
import game.entities.characters.Character;
import game.entities.characters.playables.Adel;
import game.entities.characters.playables.Playable;
import game.levels.Level;
import game.levels.Level1;
import game.map.Door;
import game.net.GameData;
import game.net.Server;
import game.utilities.*;
import utilities.Actor;
import utilities.SubScreen;
import utilities.Timer;

import java.util.ArrayList;
import java.util.function.Consumer;

public class AbstractGameScreen extends SubScreen {
    protected static final float TRANSITION_TIME = FADE_TIME / 3f;

    private final Timer timer;
    public final Array<Playable> allPlayers;
    private int coins;
    protected Level level;
    public Func onDoorsChanged;

    protected AbstractGameScreen(int quantityPlayers) {
        super();
        allPlayers = new Array<>();
        timer = new Timer();
        level = new Level1();

        stage.addActor(level);

        for (int i = 0; i < quantityPlayers; i++) {
            Adel player = new Adel(i);
            player.setPosition(level.getInitX() - player.getWidth() / 2f, level.getInitY() - player.getHeight() / 2f);
            player.setId(-(i + 1));
            stage.addActor(player);
            allPlayers.add(player);

            // To fix a HUD bug
            if (i != Server.OWNER) {
                player.damage(1);
                player.addHp(1);
            }
        }
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

    // Helper method
    protected void executeAtEntity(int entityId, Consumer<? super GameEntity> action) {
        getEntities().forEach(entity -> {
            if (entity.getId() == entityId) {
                action.accept(entity);
            }
        });
    }

    // Helper method
    protected void executeAtCharacter(int entityId, Consumer<? super Character> action) {
        getEntities().getCharacters().forEach(entity -> {
            if (entity.getId() == entityId) {
                action.accept(entity);
            }
        });
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

                if (!level.getCamera().isMoving() && Controls.isJustPressed(GameAction.INTERACT) && level.canMove()) {
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

    public void revivePlayer(int entityId) {
        for (Playable player : allPlayers) {
            if (player.getId() != entityId) {
                continue;
            }

            if (!player.isDeath()) {
                throw new RuntimeException("Player with id " + entityId + " is not death");
            }

            player.setPosition(level.getInitX() - player.getWidth() / 2f, level.getInitY() - player.getHeight() / 2f);
            stage.addActor(player);

            if (MultiplayerGameScreen.client != null && !MultiplayerGameScreen.client.isSendingData()) {
                MultiplayerGameScreen.client.revivePlayer(entityId);
            }

            player.addHp(player.getMaxHp() / 2); // Revive with half of the life
            break;
        }
    }

    public Playable getPlayer() {
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

    public int getCoins() {
        return coins;
    }

    public void addCoins(int quantity) {
        coins += quantity;
    }

    @Override
    public void dispose() {
        super.dispose();
        getEntities().forEach(Actor::dispose);
    }
}
