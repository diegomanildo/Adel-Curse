package game.levels;

import game.map.RoomsArray;
import game.rooms.BossRoom;
import game.rooms.ShopRoom;
import game.rooms.StoneRoom;
import utilities.audio.Song;

public final class Level1 extends Level {
    private static final RoomsArray ROOMS = new RoomsArray(
            BossRoom.class,
            ShopRoom.class,
            StoneRoom.class
    );

    public Level1() {
        super(new Song("Music", "game/music/UndeadIntro.mp3", "game/music/Undead.mp3"), 3, 10, 10, ROOMS);
    }
}
