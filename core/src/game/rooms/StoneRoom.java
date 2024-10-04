package game.rooms;

import game.GameScreen;
import game.entities.characters.enemies.Skeleton;
import game.utilities.EntityClassList;

public abstract class StoneRoom extends EnemyRoom {
    private static final EntityClassList ENEMIES = new EntityClassList(
            Skeleton.class
    );

    public StoneRoom(String mapFile) {
        super(mapFile, ENEMIES, 3);
    }
}
