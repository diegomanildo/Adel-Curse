package game.rooms;

import game.entities.items.Candy;
import game.entities.items.Item;
import game.utilities.map.RoomKinds;
import utilities.Log;
import utilities.io.Song;

import java.util.ArrayList;

public final class ShopRoom extends Room {
    public static Song song = new Song("Music", "game/music/ShopIntro.mp3", "game/music/Shop.mp3");
    private final ArrayList<Item> items;

    public ShopRoom() {
        super("shop/shop.tmx", RoomKinds.SHOP);
        items = new ArrayList<>();

        Candy candy = new Candy();
        candy.setPosition(200, 200);

        items.add(candy);

        items.forEach(this::addActor);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        items.forEach(i -> {
            if (i.collidesWith(i.getOwner())) {
                Log.log(i.getClass().getSimpleName() + " added to " + i.getOwner().getClass().getSimpleName());
                i.addToOwner();
                i.remove();
            }
        });
    }
}
