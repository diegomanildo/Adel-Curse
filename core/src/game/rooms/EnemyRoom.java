package game.rooms;

import game.GameScreen;
import game.entities.GameEntity;
import game.utilities.EntityClassList;
import game.ChatScreen;

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
        while(!spawned){
            generateEntities(quantityOfEntities);
            spawned = true;
        }

    }

    private void generateEntities(int quantity) {
        while (quantity > 0) {
            generateEntity();
            quantity--;
        }
    }

    private void generateEntity() {
        ChatScreen.chat.createBig("Pene", "Texto");
        float x=0, y=0;
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
