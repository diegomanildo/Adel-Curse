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

    private final int itemsQuantity = 3;
    private boolean spawnItems = false;

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
        createItems();
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

    private void createItems(){
        if(!spawnItems){
            for(int i = 0; i < itemsQuantity ; i++){
                items.add(Item.ITEMS.getRandomItem());
            }
            itemsPosition(items);
        }
    }

    private void itemsPosition(ArrayList<Item> items){
        for(int i = 0; i < itemsQuantity; i++){
            if(i == 0){
                items.get(i).setPosition(142, 113);
                items.get(i).setSize(20f, 20f);
                createEntity(items.get(i));
            } else {
                items.get(i).setPosition(items.get(i - 1).getX() + 32, items.get(i - 1).getY());
                items.get(i).setSize(20f, 20f);
                createEntity(items.get(i));
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
