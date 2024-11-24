package game.net;

import game.utilities.Direction;

public class GameData {
    public static int clientNumber = -1;
    public static NetworkActionsListener networkListener = new NetworkActionsListener() {
        @Override
        public void moveEntity(int id, Direction direction) {

        }

        @Override
        public void changeRoom(Direction direction) {

        }
    };
}
