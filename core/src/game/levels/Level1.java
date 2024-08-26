package game.levels;

import game.map.RoomsArray;
import game.rooms.BossRoom;
import game.rooms.ShopRoom;
import game.rooms.StoneRoom1;
import game.rooms.StoneRoom2;

public final class Level1 extends Level {
    private static final RoomsArray ROOMS = new RoomsArray(
            BossRoom.class,
            ShopRoom.class,
            StoneRoom1.class,
            StoneRoom2.class
    );

    public Level1() {
        super(15, 10, 10, ROOMS);
    }
}
