package game.entities.characters.enemies.bosses;

import game.entities.characters.enemies.Enemy;

import java.util.ArrayList;

public abstract class Boss extends Enemy {
    private final ArrayList<Enemy> children = new ArrayList<>();

    public Boss(Stats stats, String texturePath, String bulletTexturePath) {
        super(stats, texturePath, bulletTexturePath);
    }

    public Boss(Stats stats, String texturePath, String bulletTexturePath, int columns, int rows) {
        super(stats, texturePath, bulletTexturePath, columns, rows);
    }

    @Override
    protected String getShootSoundPath() {
        return "game/bossShoot.mp3";
    }

    public void createChild(Enemy child) {
        child.setPosition(getX(), getY());
        children.add(child);
        getStage().addActor(child);
    }

    public ArrayList<Enemy> getChildren() {
        return children;
    }
}
