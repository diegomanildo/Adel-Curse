package game.rooms;

import com.badlogic.gdx.graphics.g2d.Batch;
import game.Game;
import game.entities.characters.playables.Playable;
import game.entities.items.Candy;
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
        items = new ArrayList<>();
        firstTime = true;

        Candy candy = new Candy();
        candy.setPosition(200, 200);

        items.add(candy);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if (firstTime) {
            items.forEach(i -> {
                i.setZIndex(1);
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
