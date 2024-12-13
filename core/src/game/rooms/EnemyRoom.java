package game.rooms;

import game.Game;
import game.entities.GameEntity;
import game.items.Item;
import game.map.Door;
import game.net.GameData;
import game.net.Server;
import game.screens.MultiplayerGameScreen;
import game.utilities.EntityClassList;
import game.utilities.Hitbox;
import utilities.Utils;

import java.util.ArrayList;

public abstract class EnemyRoom extends Room {
    private static final float MIN_DISTANCE = 30f;

    private final EntityClassList entitiesClasses;
    private final int quantityOfEntities;
    private boolean spawnEntities = true;

    protected EnemyRoom(String mapFile, EntityClassList entitiesClasses, int quantityOfEntities) {
        super(mapFile);
        this.entitiesClasses = entitiesClasses;
        this.quantityOfEntities = quantityOfEntities;
    }

    @Override
    public void show() {
        super.show();
        if (spawnEntities && !isVisited()) {
            generateEntities(quantityOfEntities);
            createItems();
        }

        showItems();

        Game.game.onDoorsChanged = direction -> {
            for (Door door : getDoors()) {
                if (door.hasItem() && door.getDirection().equals(direction)) {
                    Item item = door.getItem();
                    Game.game.getPlayer().addItem(item);
                    removeActor(item);
                }
            }
        };
    }

    private void showItems() {
        for (Door door : getDoors()) {
            if (door.hasItem()) {
                Item item = door.getItem();
                createEntity(item);
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
        if (Game.game instanceof MultiplayerGameScreen && (MultiplayerGameScreen.client == null || GameData.clientNumber != Server.OWNER)) {
            return;
        }

        float playerX = Game.game.getPlayer().getX();
        float playerY = Game.game.getPlayer().getY();

        float x, y;
        Hitbox levelHitbox = Game.game.getLevel().getHitbox();

        do {
            x = Utils.r.nextFloat(levelHitbox.getLeft(), levelHitbox.getRight());
            y = Utils.r.nextFloat(levelHitbox.getBottom(), levelHitbox.getTop());
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

    private void createItems() {
        ArrayList<Item> items = new ArrayList<>();

        for (Door door : getDoors()) {
            if (!door.hasItem()) {
                Item item;
                do {
                    item = Item.ITEMS.getRandomItem();
                } while (items.contains(item));

                items.add(item);

                item.setSize(20f, 20f);
                item.setPosition(door.getHitbox().x, door.getHitbox().y);
                item.setRotation(door.getRotation());

                switch (door.getDirection()) {
                    case LEFT:
                        item.setPosition(left.getHitbox().x + item.getWidth() + 5f, left.getHitbox().y);
                        break;
                    case RIGHT:
                        item.setPosition(right.getHitbox().x + 13, right.getHitbox().y + item.getHeight());
                        break;
                    case UP:
                        item.setPosition(up.getHitbox().x, up.getHitbox().y + 5f);
                        break;
                    case DOWN:
                        item.setPosition(down.getHitbox().x + item.getHeight(), down.getHitbox().y + item.getWidth() + 5f);
                        break;
                    default:
                        throw new RuntimeException("Invalid direction: " + door.getDirection());
                }

                door.setItem(item);
            }
        }
    }

    public boolean getSpawnEntities() {
        return spawnEntities;
    }

    public void setSpawnEntities(boolean spawnEntities) {
        this.spawnEntities = spawnEntities;
    }
}