package game.rooms;

import game.entities.GameEntity;
import game.utilities.EntityClassList;
import game.GameScreen;

import java.util.Random;

public abstract class EnemyRoom extends Room {
    private final EntityClassList entitiesClasses;
    private static Random random = new Random();

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
       float x, y;
        do{
            x = random.nextFloat() * getWidth();
        }while(x == GameScreen.game.getPlayer().getX());

        do{
            y = random.nextFloat() * getHeight();
        }while(x == GameScreen.game.getPlayer().getY());

        GameEntity entity = getRandomEntityAt(x, y);

        createEntity(entity);
    }

    private GameEntity getRandomEntityAt(float x, float y) {
        GameEntity entity = entitiesClasses.getRandomEntity();
        entity.setPosition(x, y);
        return entity;
    }
}
