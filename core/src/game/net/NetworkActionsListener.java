package game.net;

import game.utilities.Direction;

public interface NetworkActionsListener {
    void moveEntity(int id, Direction direction);

    void changeRoom(Direction direction);
}
