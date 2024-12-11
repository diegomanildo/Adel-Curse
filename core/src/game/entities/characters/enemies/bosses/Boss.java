package game.entities.characters.enemies.bosses;

import game.entities.characters.enemies.Enemy;

public abstract class Boss extends Enemy {
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
}
