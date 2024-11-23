package game.net;

import game.entities.GameEntity;

import java.util.ArrayList;

public interface NetworkActionsListener {
    void moveEntity(int id, float x, float y);

    void gameOver();

    ArrayList<GameEntity> getEntities();
}
