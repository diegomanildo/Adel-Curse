package game.rooms;

import com.badlogic.gdx.scenes.scene2d.Actor;
import game.Game;
import game.GameScreen;
import game.entities.ShopKeeper;
import game.entities.characters.playables.Playable;
import game.entities.items.Item;
import game.map.RoomKinds;
import game.utilities.Controls;
import game.utilities.GameAction;
import utilities.audio.Song;

import java.util.ArrayList;

public final class ShopRoom extends Room {
    private static final String SHOP_KEY = "Shop";
    private static final String INTERACT_KEY = "Interact";

    public static Song shopSong = new Song("Music", "game/music/ShopIntro.mp3", "game/music/Shop.mp3");
    private final ArrayList<Item> items;
    private final ShopKeeper shopKeeper;

    public ShopRoom() {
        super("shop/shop.tmx", RoomKinds.SHOP);
        items = new ArrayList<>();

        shopKeeper = new ShopKeeper();
        shopKeeper.setPosition(172f, 132f);
    }

    @Override
    public void show() {
        super.show();
        createEntity(shopKeeper);
        GameScreen.chat.createBig(SHOP_KEY, "Hola! Bienvenido a la Tienda! Soy el vendedor. Aqui podras comprar items a cambio de monedas. No te pases de listo.");
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        ArrayList<Playable> players = Game.entities.getPlayers();

        for (Playable player : players) {
            if (player.collidesWith(shopKeeper.getBounds())) {
                GameScreen.chat.createTiny(INTERACT_KEY, "Press " + Controls.getCharacter(GameAction.INTERACT));

                if (Controls.isJustPressed(GameAction.INTERACT)) {
                    GameScreen.shopScreen.setShow(true);
                }
            } else {
                GameScreen.chat.removeChat(INTERACT_KEY);
            }
        }
    }

    @Override
    public boolean remove() {
        boolean b = super.remove();
        items.forEach(Actor::remove);
        return b;
    }
}
