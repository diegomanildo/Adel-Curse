package game.rooms;

import game.Game;
import game.entities.GameEntity;
import game.entities.characters.playables.Playable;
import game.entities.items.Item;
import game.map.Door;
import game.net.GameData;
import game.net.Server;
import game.screens.MultiplayerGameScreen;
import game.utilities.Direction;
import game.utilities.EntityClassList;
import utilities.Utils;

public abstract class EnemyRoom extends Room {
    private static final float MIN_DISTANCE = 30f;

    private final EntityClassList entitiesClasses;
    private final int quantityOfEntities;
    private boolean spawnEntities = true;
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

        if (!isVisited() && spawnEntities) {
            generateEntities(quantityOfEntities);
        }

        if (spawnEntities) {
            createItems();
        }

        Game.game.onDoorsChanged = direction -> {
            Playable player = Game.game.getPlayer();
            switch (direction) {
                case LEFT: {
                    player.addItem(itemLeft);
                    removeActor(itemLeft);
                    break;
                }
                case RIGHT: {
                    player.addItem(itemRight);
                    removeActor(itemRight);
                    break;
                }
                case UP: {
                    player.addItem(itemUp);
                    removeActor(itemUp);
                    break;
                }
                case DOWN: {
                    player.addItem(itemDown);
                    removeActor(itemDown);
                    break;
                }
                default:
                    throw new RuntimeException("Direction " + direction + " isn´t a valid direction");
            }
        };
    }

    private void generateEntities(int quantity) {
        while (quantity > 0) {
            generateEntity();
            quantity--;
        }
    }

    private void generateEntity() {
        if (Game.game instanceof MultiplayerGameScreen && (MultiplayerGameScreen.client == null || GameData.clientNumber != Server.OWNER)) {
            return;
        }

        float playerX = Game.game.getPlayer().getX();
        float playerY = Game.game.getPlayer().getY();

        float x, y;

        do {
            x = Utils.r.nextFloat() * getWidth();
            y = Utils.r.nextFloat() * getHeight();
        } while (distance(playerX, playerY, x, y) < MIN_DISTANCE);

        GameEntity e = entitiesClasses.getRandomEntity();
        e.setPosition(x, y);
        createEntity(e);
    }

    private static float distance(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    private void createItems() {
        Door[] doors = getDoors();
        for (Door door : doors) {
            Direction direction = door.getDirection();
            switch (direction) {
                case LEFT: {
                    if (!spawnLeft) {
                        itemLeft = Item.ITEMS.getRandomItem();
                        itemLeft.setPosition(LEFT.getHitbox().x + 5, LEFT.getHitbox().y);
                        itemLeft.setSize(20f, 20f);
                        spawnLeft = true;
                    }
                    if (itemLeft != null) {
                        createEntity(itemLeft);
                    }
                    break;
                }
                case RIGHT: {
                    if (!spawnRight) {
                        itemRight = Item.ITEMS.getRandomItem();
                        itemRight.setPosition(RIGHT.getHitbox().x + 13, RIGHT.getHitbox().y + 1);
                        itemRight.setSize(20f, 20f);
                        spawnRight = true;
                    }
                    if (itemRight != null) {
                        createEntity(itemRight);
                    }
                    break;
                }
                case UP: {
                    if (!spawnUp) {
                        itemUp = Item.ITEMS.getRandomItem();
                        itemUp.setPosition(UP.getHitbox().x, UP.getHitbox().y + 7);
                        itemUp.setSize(20f, 20f);
                        spawnUp = true;
                    }
                    if (itemUp != null) {
                        createEntity(itemUp);
                    }
                    break;
                }
                case DOWN:
                    if (!spawnDown) {
                        itemDown = Item.ITEMS.getRandomItem();
                        itemDown.setPosition(DOWN.getHitbox().x, DOWN.getHitbox().y + 3);
                        itemDown.setSize(20f, 20f);
                        spawnDown = true;
                    }
                    if (itemDown != null) {
                        createEntity(itemDown);
                    }
                    break;
            }
        }
    }

    public void setSpawnEntities(boolean spawnEntities) {
        this.spawnEntities = spawnEntities;
    }
}
