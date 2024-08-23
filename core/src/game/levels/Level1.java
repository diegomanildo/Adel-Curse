package game.levels;

import game.rooms.BossRoom;
import game.rooms.ShopRoom;
import game.rooms.StoneRoom;
import game.utilities.map.RoomsArray;

public final class Level1 extends Level {
    private static final RoomsArray ROOMS = new RoomsArray(
            BossRoom.class,
            ShopRoom.class,
            StoneRoom.class
    );

    public Level1() {
        super(15, 10, 10, ROOMS);
    }
}
