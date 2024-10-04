package game.rooms;

import game.GameScreen;
import game.entities.GameEntity;
import game.utilities.EntityClassList;

import java.util.Random;

public abstract class EnemyRoom extends Room {
    private final EntityClassList entitiesClasses;
    private static Random random = new Random();
    private final int quantityOfEntities;
    private boolean spawned = false;

    protected EnemyRoom(String mapFile, EntityClassList entitiesClasses, int quantityOfEntities) {
        super(mapFile);
        this.entitiesClasses = entitiesClasses;
        this.quantityOfEntities = quantityOfEntities;
    }

    @Override
    public void show() {
        super.show();
        if (!spawned) {
            generateEntities(quantityOfEntities);
            spawned = true;
        } else {
            for (GameEntity entity : entities) {
                entity.setVisible(true);
            }
        }
    }

    private void generateEntities(int quantity) {
        while (quantity > 0) {
            generateEntity();
            quantity--;
        }
    }

    private void generateEntity() {
        float x = 0, y = 0;
        int maxAttempts = 100;
        int attempts = 0;

        do {
            x = random.nextFloat() * getWidth();
            attempts++;
        } while (x == GameScreen.game.getPlayer().getX() && attempts < maxAttempts);

        attempts = 0;

        do {
            y = random.nextFloat() * getHeight();
            attempts++;
        } while (y == GameScreen.game.getPlayer().getY() && attempts < maxAttempts);

        GameEntity entity = getRandomEntityAt(x, y);
        createEntity(entity);
    }

    private GameEntity getRandomEntityAt(float x, float y) {
        GameEntity entity = entitiesClasses.getRandomEntity();
        entity.setPosition(x, y);
        createEntity(entity);
        getStage().addActor(entity); 
        return entity;
    }
}
