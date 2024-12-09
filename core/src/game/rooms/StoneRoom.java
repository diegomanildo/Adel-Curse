package game.rooms;

import game.entities.characters.enemies.Skeleton;
import game.map.RoomMap;
import game.utilities.EntityClassList;
import utilities.Utils;

public class StoneRoom extends EnemyRoom {
    private static final EntityClassList ENEMIES = new EntityClassList(
            Skeleton.class
    );

    private static final int STYLES = 2;

    public StoneRoom() {
        super("stone/map_" + (RoomMap.isInitRoom ? 1 : (Utils.r.nextInt(STYLES) + 1)) + ".tmx", ENEMIES, 3);
    }
}
