package game.net;

import game.entities.GameEntity;
import game.rooms.Room;
import game.utilities.Direction;

public interface NetworkActionsListener {
    void createEntity(GameEntity entity);
    void moveEntity(int entityId, float x, float y, Direction direction);
    void changeSizeEntity(int entityId, float width, float height);
    void changeRoom(Direction direction);
    void createShoot(int entityId, Direction direction);
    void endGame();
    void initializeLevel(Room[][] rooms);
    void updateHp(int entityId, int hp);
    void updateMaxHp(int entityId, int maxHp);
    void updateDamage(int entityId, int damage);
    void updateArmor(int entityId, int armor);
}
