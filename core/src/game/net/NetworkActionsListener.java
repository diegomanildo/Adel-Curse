package game.net;

import game.entities.GameEntity;
import game.map.Door;
import game.rooms.Room;
import game.utilities.Direction;

import java.util.ArrayList;

public interface NetworkActionsListener {
    void createEntity(GameEntity entity);
    void moveEntity(int entityId, float x, float y, Direction direction);
    void removeEntity(int entityId);
    void revivePlayer(int entityId);
    void changeFrames(int entityId, String texturePath, int columns, int rows, float frameDuration);
    void changeTexture(int entityId, String texturePath);
    void changeSizeEntity(int entityId, float width, float height);
    void changeRoom(Direction direction);
    void createItems(int roomId, ArrayList<Door> doors);
    void createShoot(int entityId, Direction direction);
    void endGame();
    void initializeLevel(Room[][] rooms);
    void updateVelocity(int entityId, float velocity);
    void updateHp(int entityId, int hp);
    void updateMaxHp(int entityId, int maxHp);
    void updateDamage(int entityId, int damage);
    void updateArmor(int entityId, int armor);
}
