package game.screens;

import game.Game;
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

    @Override
    public void moveEntity(int id, float x, float y, Direction direction) {
        getEntities().getEntity(id).setPosition(x, y);
        getEntities().getEntity(id).setDirection(direction);
    }

    @Override
    protected void moveCamera(Direction direction) {
        super.moveCamera(direction);
        client.roomChanged(direction);
    }

    @Override
    public void createShoot(int id, Direction direction) {
        ((Character)getEntities().getEntity(id)).shoot(direction, true);
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
    public void removeEntity(int id) {
        getEntities().getEntity(id).remove();
        getEntities().remove(getEntities().getEntity(id));
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