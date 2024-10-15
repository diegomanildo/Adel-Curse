package game.rooms;

import game.GameScreen;
import game.entities.GameEntity;
import game.entities.items.Item;
import game.map.Door;
import game.utilities.Direction;
import game.utilities.EntityClassList;

import java.util.ArrayList;
import java.util.Random;

public abstract class EnemyRoom extends Room {
    private static final float MIN_DISTANCE = 30f;
    private final EntityClassList entitiesClasses;
    private static final Random random = new Random();
    private final int quantityOfEntities;
    private boolean spawnEntities = true;

    private boolean spawnItems = false;
    private final ArrayList<Item> itemList = new ArrayList<>();
    private boolean spawnLeft = false;
    private boolean spawnRight = false;
    private boolean spawnUp = false;
    private boolean spawnDown = false;
    private Item itemLeft;
    private Item itemRight;
    private Item itemUp;
    private Item itemDown;

    protected EnemyRoom(String mapFile, EntityClassList entitiesClasses, int quantityOfEntities) {
        super(mapFile);
        this.entitiesClasses = entitiesClasses;
        this.quantityOfEntities = quantityOfEntities;
    }

    @Override
    public void show() {
        super.show();
        if (spawnEntities) {
            generateEntities(quantityOfEntities);
        }
        createItems();
    }

    private void generateEntities(int quantity) {
        while (quantity > 0) {
            generateEntity();
            quantity--;
        }
    }

    private void generateEntity() {
        float playerX = GameScreen.game.getPlayer().getX();
        float playerY = GameScreen.game.getPlayer().getY();

        float x, y;

        do {
            x = random.nextFloat() * getWidth();
            y = random.nextFloat() * getHeight();
        } while (distance(playerX, playerY, x, y) < MIN_DISTANCE);

        GameEntity e = getRandomEntityAt(x, y);
        createEntity(e);
    }

    private static float distance(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    private GameEntity getRandomEntityAt(float x, float y) {
        GameEntity entity = entitiesClasses.getRandomEntity();
        entity.setPosition(x, y);
        return entity;
    }

    public boolean getSpawnEntities() {
        return spawnEntities;
    }

    private void createItems() {
        if(!spawnLeft){
            itemLeft = Item.ITEMS.getRandomItem();
            itemLeft.setPosition(LEFT.getHitbox().x, LEFT.getHitbox().y);
            spawnLeft = true;
        }
        createEntity(itemLeft);

        if(!spawnRight){
            itemRight = Item.ITEMS.getRandomItem();
            itemRight.setPosition(RIGHT.getHitbox().x, RIGHT.getHitbox().y );
            spawnRight = true;
        }
        createEntity(itemRight);

        if(!spawnUp){
            itemUp = Item.ITEMS.getRandomItem();
            itemUp.setPosition(UP.getHitbox().x , UP.getHitbox().y);
            spawnUp = true;
        }
        createEntity(itemUp);

        if(!spawnDown){
            itemDown = Item.ITEMS.getRandomItem();
            itemDown.setPosition(DOWN.getHitbox().x, DOWN.getHitbox().y );
            spawnDown = true;
        }
        createEntity(itemDown);
    }

    public void setSpawnEntities(boolean spawnEntities) {
        this.spawnEntities = spawnEntities;
    }
}
