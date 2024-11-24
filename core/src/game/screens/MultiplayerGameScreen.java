package game.screens;

import game.Game;
import game.entities.GameEntity;
import game.entities.characters.playables.Adel;
import game.levels.Level1;
import game.net.GameData;
import game.net.NetworkActionsListener;
import game.net.threads.ClientThread;
import game.utilities.Direction;

public final class MultiplayerGameScreen extends AbstractGameScreen implements NetworkActionsListener {
    public static final int PLAYER1_ID = -1;
    public static final int PLAYER2_ID = -2;

    public static ClientThread client;

    public MultiplayerGameScreen() {
        super();

        level = new Level1();
        Adel p1 = new Adel();
        p1.setPosition(level.getInitX() - p1.getWidth() / 2f, level.getInitY() - p1.getHeight() / 2f);
        p1.setId(PLAYER1_ID);

        Adel p2 = new Adel();
        p2.setPosition(level.getInitX() - p2.getWidth() / 2f, level.getInitY() - p2.getHeight() / 2f);
        p2.setId(PLAYER2_ID);

        stage.addActor(level);
        stage.addActor(p1);
        stage.addActor(p2);

        GameData.networkListener = this;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (!client.isConnected()) {
            Game.deathScreen.playerDead();
        }

        stage.getActors().forEach(actor -> {
            if (actor instanceof GameEntity) {
                GameEntity e = (GameEntity)actor;
                client.updateEntityPosition(e.getId(), e.getDirection());
            }
        });
    }

    @Override
    public void moveEntity(int id, Direction direction) {
        getEntities().getEntity(id).move(direction);
    }

    @Override
    protected void moveCamera(Direction direction) {
        super.moveCamera(direction);
        client.roomChanged(direction);
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