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
    private ArrayList<Item> itemList = new ArrayList<>();
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
        takeItems();
    }

    private void takeItems(){
        if (GameScreen.game.getPlayer().collidesWith(Room.LEFT.getHitbox())) {
            //System.out.println("add item:" + GameScreen.game.getPlayer().getItems().get(0).getName());
            GameScreen.game.getPlayer().addItem(itemLeft);
            removeActor(itemLeft);
        }
        if (GameScreen.game.getPlayer().collidesWith(Room.RIGHT.getHitbox())) {
            //System.out.println("add item:" + GameScreen.game.getPlayer().getItems().get(0).getName());
            GameScreen.game.getPlayer().addItem(itemRight);
            removeActor(itemRight);
        }
        if (GameScreen.game.getPlayer().collidesWith(Room.UP.getHitbox())) {
            GameScreen.game.getPlayer().addItem(itemUp);
            //System.out.println("add item:" + GameScreen.game.getPlayer().getItems().get(0).getName());
            removeActor(itemUp);
        }
        if (GameScreen.game.getPlayer().collidesWith(Room.DOWN.getHitbox())) {
            //System.out.println("add item:" + GameScreen.game.getPlayer().getItems().get(0).getName());
            GameScreen.game.getPlayer().addItem(itemDown);
            removeActor(itemDown);
        }
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
        Door[] doors = getDoors();
        for(int i = 0; i < doors.length; i++){
            Direction direction = doors[i].getDirection();

            switch(direction){
                case LEFT:{
                    if(!spawnLeft){
                        itemLeft = Item.ITEMS.getRandomItem();
                        itemLeft.setPosition(LEFT.getHitbox().x + 5, LEFT.getHitbox().y);
                        itemLeft.setSize(20f, 20f);
                        spawnLeft = true;
                    }
                    if(itemLeft != null){
                        createEntity(itemLeft);
                        itemList.add(itemLeft);
                    }
                    break;
                }
                case RIGHT:{
                    if(!spawnRight){
                        itemRight = Item.ITEMS.getRandomItem();
                        itemRight.setPosition(RIGHT.getHitbox().x + 13, RIGHT.getHitbox().y + 1);
                        itemRight.setSize(20f, 20f);
                        spawnRight = true;
                    }
                    if(itemRight != null){
                        createEntity(itemRight);
                        itemList.add(itemRight);
                    }
                    break;
                }
                case UP:{
                    if(!spawnUp){
                        itemUp = Item.ITEMS.getRandomItem();
                        itemUp.setPosition(UP.getHitbox().x, UP.getHitbox().y + 7);
                        itemUp.setSize(20f, 20f);
                        spawnUp = true;
                    }
                    if(itemUp != null){
                        createEntity(itemUp);
                        itemList.add(itemUp);
                    }
                    break;
                }
                case DOWN:
                    if(!spawnDown){
                        itemDown = Item.ITEMS.getRandomItem();
                        itemDown.setPosition(DOWN.getHitbox().x, DOWN.getHitbox().y + 3);
                        itemDown.setSize(20f, 20f);
                        spawnDown = true;
                    }
                    if(itemDown != null){
                        createEntity(itemDown);
                        itemList.add(itemDown);
                    }
                    break;
            }
        }



    }

    public void setSpawnEntities(boolean spawnEntities) {
        this.spawnEntities = spawnEntities;
    }
}
