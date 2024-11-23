package game.net;

import game.entities.GameEntity;

import java.util.ArrayList;

public class GameData {
    public static int clientNumber = -1;
    public static NetworkActionsListener networkListener = new NetworkActionsListener() {
        @Override
        public void moveEntity(int id, float x, float y) {

        }

        @Override
        public void gameOver() {

        }

        @Override
        public ArrayList<GameEntity> getEntities() {
            return null;
        }
    };
}
