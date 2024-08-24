package game.rooms;

import game.map.RoomKinds;
import utilities.io.Song;

public final class BossRoom extends Room {
    public static Song song = new Song("Music", "songs/Death to Eva.mp3");

    public BossRoom() {
        super("boss/boss_1.tmx", RoomKinds.BOSS);
    }
}
