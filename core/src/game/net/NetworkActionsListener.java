package game.net;

import game.rooms.Room;
import game.utilities.Direction;

public interface NetworkActionsListener {
    void moveEntity(int id, float x, float y, Direction direction);
    void changeRoom(Direction direction);
    void createShoot(int id, Direction direction);
    void endGame();
    void initializeLevel(Room[][] rooms);
}
