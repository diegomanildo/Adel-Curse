package game.rooms;

import game.Game;
import game.entities.GameEntity;
import game.entities.characters.playables.Playable;
import game.entities.items.Item;
import game.map.Door;
import game.net.GameData;
import game.net.Server;
import game.screens.MultiplayerGameScreen;
import game.utilities.EntityClassList;
import utilities.Utils;

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

        if (!isVisited() && spawnEntities) {
            generateEntities(quantityOfEntities);
        }

        if (spawnEntities) {
            createItems();
        }

        Game.game.onDoorsChanged = direction -> {
            Playable player = Game.game.getPlayer();
            for (Door door : getDoors()) {
                player.addItem(door.getItem());
                removeActor(door.getItem());
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
        for (Door door : getDoors()) {
            Item item = Item.ITEMS.getRandomItem();
            item.setSize(20f, 20f);

            switch (door.getDirection()) {
                case LEFT:
                    item.setPosition(LEFT.getHitbox().x + 5, LEFT.getHitbox().y);
                    break;
                case RIGHT:
                    item.setPosition(RIGHT.getHitbox().x + 13, RIGHT.getHitbox().y + 1);
                    break;
                case UP:
                    item.setPosition(UP.getHitbox().x, UP.getHitbox().y + 7);
                    break;
                case DOWN:
                    item.setPosition(DOWN.getHitbox().x, DOWN.getHitbox().y + 3);
                    break;
            }

            createEntity(item);
            door.setItem(item);
        }
    }

    public void setSpawnEntities(boolean spawnEntities) {
        this.spawnEntities = spawnEntities;
    }
}
