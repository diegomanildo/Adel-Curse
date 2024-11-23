package game.net;

public class GameData {
    public static int clientNumber = -1;
    public static NetworkActionsListener networkListener = new NetworkActionsListener() {
        @Override
        public void moveEntity(int id, float x, float y) {

        }
    };
}
