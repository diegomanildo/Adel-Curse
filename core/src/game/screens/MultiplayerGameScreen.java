package game.screens;

import game.entities.GameEntity;
import game.entities.characters.playables.Adel;
import game.levels.Level1;
import game.net.GameData;
import game.net.NetworkActionsListener;
import game.utilities.Entities;

import java.util.ArrayList;

public final class MultiplayerGameScreen extends AbstractGameScreen implements NetworkActionsListener {
    public MultiplayerGameScreen() {
        super();
        entities = new Entities();

        level = new Level1();
        Adel p1 = new Adel();
        p1.setPosition(level.getInitX() - p1.getWidth() / 2f, level.getInitY() - p1.getHeight() / 2f);

        Adel p2 = new Adel();
        p2.setPosition(level.getInitX() - p2.getWidth() / 2f, level.getInitY() - p2.getHeight() / 2f);

        stage.addActor(level);
        stage.addActor(p1);
        stage.addActor(p2);

        stage.getActors().forEach(actor -> {
            if (actor instanceof GameEntity) {
                entities.add((GameEntity) actor);
            }
        });

        GameData.networkListener = this;
    }

    @Override
    public void moveEntity(int id, float x, float y) {
        entities.getEntity(id).setPosition(x, y);
    }

    @Override
    public void gameOver() {

    }

    @Override
    public ArrayList<GameEntity> getEntities() {
        return entities;
    }
}