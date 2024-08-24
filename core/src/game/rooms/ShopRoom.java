package game.rooms;

import game.utilities.map.RoomKinds;
import utilities.io.Song;

public final class ShopRoom extends Room {
    public static Song song = new Song("Music", "game/music/ShopIntro.mp3", "game/music/Shop.mp3");

    public ShopRoom() {
        super("shop/shop.tmx", RoomKinds.SHOP);
//        initializeObjects();
    }

//    private void initializeObjects() {
//        Actor item1 = new Image(new Texture("items/bomb/bombExplotion.png"));
//        item1.setPosition(50, 100);
//        this.addActor(item1);
//
//        Actor item2 = new Image(new Texture("items/diamond/diamond.png"));
//        item2.setPosition(200, 150);
//        this.addActor(item2);
//
//        Actor item3 = new Image(new Texture("items/skeletonMask/skeletonMask.png"));
//        item3.setPosition(300, 200);
//        this.addActor(item3);
//    }

}
