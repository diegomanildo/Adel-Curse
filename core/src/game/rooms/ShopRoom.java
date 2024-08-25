package game.rooms;

import com.badlogic.gdx.graphics.g2d.Batch;
import game.Game;
import game.entities.characters.playables.Playable;
import game.entities.items.Item;
import game.entities.items.Shell;
import game.map.RoomKinds;
import utilities.Log;
import utilities.io.Song;

import java.util.ArrayList;

public final class ShopRoom extends Room {
    public static Song song = new Song("Music", "game/music/ShopIntro.mp3", "game/music/Shop.mp3");
    private final ArrayList<Item> items;
    private boolean firstTime;

    public ShopRoom() {
        super("shop/shop.tmx", RoomKinds.SHOP);
        firstTime = true;
        items = new ArrayList<>();

        float yPos = 115f;

        generateItem(144f, yPos);
        generateItem(175f, yPos);
        generateItem(208f, yPos);
    }

    private void generateItem(float x, float y) {
        Item item = new Shell();

//        do {
//            item = Item.ITEMS.getRandomItem();
//        } while (items.contains(item));

        item.setPosition(x, y);
        items.add(item);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if (firstTime) {
            items.forEach(i -> {
                getStage().addActor(i);
            });
            firstTime = false;
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        ArrayList<Playable> players = Game.entities.getPlayers();

        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            for (Playable player : players) {
                if (item.collidesWith(player.getBounds())) {
                    item.changeOwnerTo(player);
                    item.addToOwner();
                    item.remove();
                    items.remove(item);
                    Log.debug(item.getClass().getSimpleName() + " added to " + item.getOwner().getClass().getSimpleName());
                }
            }
        }
    }

    @Override
    public boolean remove() {
        for (Item item : items) {
            item.remove();
        }
        firstTime = true;
        return super.remove();
    }
}
