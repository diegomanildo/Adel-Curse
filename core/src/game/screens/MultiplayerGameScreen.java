package game.screens;

import game.Game;
import game.entities.GameEntity;
import game.entities.characters.Character;
import game.entities.characters.playables.Adel;
import game.levels.Level1;
import game.map.RoomMap;
import game.net.Client;
import game.net.GameData;
import game.net.NetworkActionsListener;
import game.net.Server;
import game.rooms.Room;
import game.utilities.Direction;

import java.util.function.Consumer;

public final class MultiplayerGameScreen extends AbstractGameScreen implements NetworkActionsListener {
    public static final int PLAYERS = Server.MAX_CLIENTS;

    public static Client client;

    public MultiplayerGameScreen() {
        super();
        level = new Level1();
        stage.addActor(level);

        for (int i = 0; i < PLAYERS; i++) {
            Adel player = new Adel(i);
            player.setPosition(level.getInitX() - player.getWidth() / 2f, level.getInitY() - player.getHeight() / 2f);
            player.setId(-(i + 1));
            stage.addActor(player);
        }

        GameData.networkListener = this;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (!client.isConnected()) {
            Game.deathScreen.playerDead();
        }
    }

    // Helper method
    private void executeAtEntity(int entityId, Consumer<? super GameEntity> action) {
        getEntities().forEach(entity -> {
            if (entity.getId() == entityId) {
                action.accept(entity);
            }
        });
    }

    // Helper method
    private void executeAtCharacter(int entityId, Consumer<? super Character> action) {
        getEntities().getCharacters().forEach(entity -> {
            if (entity.getId() == entityId) {
                action.accept(entity);
            }
        });
    }

    @Override
    public void createEntity(GameEntity entity) {
        level.getMap().getCurrent().createEntity(entity);
    }

    @Override
    public void moveEntity(int entityId, float x, float y, Direction direction) {
        executeAtEntity(entityId, e -> {
            e.setPosition(x, y);
            e.setDirection(direction);
        });
    }

    @Override
    public void changeFrames(int entityId, String texturePath, int columns, int rows, float frameDuration) {
        executeAtEntity(entityId, c -> c.setFrames(texturePath, columns, rows, frameDuration));
    }

    @Override
    public void changeTexture(int entityId, String texturePath) {
        executeAtCharacter(entityId, c -> c.setBulletTexturePath(texturePath));
    }

    @Override
    public void changeSizeEntity(int entityId, float width, float height) {
        executeAtEntity(entityId, e -> e.setSize(width, height));
    }

    @Override
    protected void moveCamera(Direction direction) {
        super.moveCamera(direction);
        client.roomChanged(direction);
    }

    @Override
    public void createShoot(int entityId, Direction direction) {
        executeAtCharacter(entityId, c -> c.shoot(direction, true));
    }

    @Override
    public void endGame() {
        Game.exit();
    }

    @Override
    public void initializeLevel(Room[][] rooms) {
        RoomMap.map = rooms;
    }

    @Override
    public void updateHp(int entityId, int hp) {
        executeAtCharacter(entityId, c -> c.setHp(hp));
    }

    @Override
    public void updateMaxHp(int entityId, int maxHp) {
        executeAtCharacter(entityId, c -> c.setMaxHp(maxHp));
    }

    @Override
    public void updateDamage(int entityId, int damage) {
        executeAtCharacter(entityId, c -> c.setDamage(damage));
    }

    @Override
    public void updateArmor(int entityId, int armor) {
        executeAtCharacter(entityId, c -> c.setArmor(armor));
    }

    @Override
    public void changeRoom(Direction direction) {
        super.moveCamera(direction);
    }

    // Override for not pause/resume the game
    @Override
    public void pause() {}

    // Override for not pause/resume the game
    @Override
    public void resume() {}
}