package game.entities.characters.enemies.bosses;

import com.badlogic.gdx.utils.Array;
import game.entities.characters.enemies.Enemy;

public class Boss extends Enemy {
    private final Array<Enemy> childrenEnemies;

    public Boss(Stats stats, String texturePath, String bulletTexturePath, int columns, int rows) {
        super(stats, texturePath, bulletTexturePath, columns, rows);
        childrenEnemies = new Array<>();
    }

    public void createChildrenEnemy(Enemy e) {
        getStage().addActor(e);
    }

    public Array<Enemy> getChildrenEnemies() {
        return childrenEnemies;
    }
}
