package game.rooms;

import game.entities.GameEntity;
import game.utilities.EntityClassList;

public abstract class EnemyRoom extends Room {
    private final EntityClassList entitiesClasses;

    protected EnemyRoom(String mapFile, EntityClassList entitiesClasses, int quantityOfEntities) {
        super(mapFile);
        this.entitiesClasses = entitiesClasses;
        generateEntities(quantityOfEntities);
    }

    private void generateEntities(int quantity) {
        while (quantity > 0) {
            generateEntity();
            quantity--;
        }
    }

    private void generateEntity() {
        float x = (float) (Math.random() * getWidth());
        float y = (float) (Math.random() * getHeight());

        GameEntity entity = getRandomEntityAt(x, y);

        createEntity(entity);
    }

    private GameEntity getRandomEntityAt(float x, float y) {
        GameEntity entity = entitiesClasses.getRandomEntity();
        entity.setPosition(x, y);
        return entity;
    }
}
