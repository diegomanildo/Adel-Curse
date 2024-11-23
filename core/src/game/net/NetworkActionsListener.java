package game.net;

import game.utilities.Direction;

public interface NetworkActionsListener {
    void moveEntity(int id, float x, float y);

    void changeRoom(Direction direction);
}
