package game.rooms;

import com.badlogic.gdx.graphics.g2d.Batch;
import game.Game;
import game.entities.characters.playables.Playable;
import game.entities.items.Item;
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

        Item item1 = getRandomItem();
        item1.setPosition(144, 115);
        items.add(item1);

        Item item2 = getRandomItem();
        item2.setPosition(175f, 115);
        items.add(item2);

        Item item3 = getRandomItem();
        item3.setPosition(208, 115);
        items.add(item3);
    }

    public Item getRandomItem() {
        Item item;

        do {
            item = Item.ITEMS.getRandomItem();
        } while (items.contains(item));

        return item;
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
                if (item.collidesWith(player)) {
                    item.changeOwnerTo(player);
                    item.addToOwner();
                    item.remove();
                    items.remove(item);
                    Log.log(item.getClass().getSimpleName() + " added to " + item.getOwner().getClass().getSimpleName());
                }
            }
        }
    }
}
