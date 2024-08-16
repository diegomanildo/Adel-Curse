package game.levels;

import game.rooms.BossRoom;
import game.rooms.StoneRoom;
import game.utilities.RoomsArray;

public final class Level1 extends Level {
    public Level1() {
        super(15, 10, 10, new RoomsArray(StoneRoom.class, BossRoom.class));
    }
}
