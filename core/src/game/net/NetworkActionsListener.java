package game.net;

import game.entities.GameEntity;
import game.items.Item;
import game.rooms.Room;
import game.utilities.Direction;

public interface NetworkActionsListener {
    void createEntity(GameEntity entity);
    void moveEntity(int entityId, float x, float y, Direction direction);
    void removeEntity(int entityId);
    void revivePlayer(int entityId);
    void changeFrames(int entityId, String texturePath, int columns, int rows, float frameDuration);
    void changeTexture(int entityId, String texturePath);
    void changeSizeEntity(int entityId, float width, float height);
    void createItem(Item item, Direction direction);
    void changeRoom(Direction direction);
    void createShoot(int entityId, Direction direction);
    void endGame();
    void initializeLevel(Room[][] rooms);
    void updateVelocity(int entityId, float velocity);
    void updateHp(int entityId, int hp);
    void updateMaxHp(int entityId, int maxHp);
    void updateDamage(int entityId, int damage);
    void updateArmor(int entityId, int armor);
}
