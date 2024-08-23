package game.rooms;

import game.utilities.map.RoomKinds;

public final class BossRoom extends Room {
    public BossRoom() {
        super("boss/boss_1.tmx", RoomKinds.BOSS);
    }
}
