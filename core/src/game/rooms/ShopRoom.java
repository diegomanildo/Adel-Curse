package game.rooms;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import game.Game;
import game.entities.ShopKeeper;
import game.entities.characters.playables.Playable;
import game.entities.items.Item;
import game.map.RoomKinds;
import utilities.io.Song;

import java.util.ArrayList;

public final class ShopRoom extends Room {
    public static Song song = new Song("Music", "game/music/ShopIntro.mp3", "game/music/Shop.mp3");
    private final ArrayList<Item> items;
    private final ShopKeeper shopKeeper;

    public ShopRoom() {
        super("shop/shop.tmx", RoomKinds.SHOP);
        items = new ArrayList<>();

        shopKeeper = new ShopKeeper();
        shopKeeper.setPosition(172f, 115f);
        createEntity(shopKeeper);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        ArrayList<Playable> players = Game.entities.getPlayers();

        for (Playable player : players) {
            if (player.collidesWith(shopKeeper.getBounds())) {
                Game.chat.createTiny("Press E");

                if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
                    // GameScreen.openShopScreen();
                }
            } else {
                Game.chat.remove();
            }
        }

//        for (int i = 0; i < items.size(); i++) {
//            Item item = items.get(i);
//            for (Playable player : players) {
//                // If item collides with the player, add item to him
//                if (item.collidesWith(player.getBounds())) {
//                    item.changeOwnerTo(player);
//                    item.addToOwner();
//                    item.remove();
//                    items.remove(item);
//                    Log.debug(item.getClass().getSimpleName() + " added to " + item.getOwner().getClass().getSimpleName());
//                }
//            }
//        }
    }

    @Override
    public boolean remove() {
        boolean b = super.remove();
        items.forEach(Actor::remove);
        return b;
    }
}
