package game.entities.characters.enemies;

import game.entities.characters.Character;

public abstract class Enemy extends Character {

    public Enemy(Stats stats, String texturePath, String bulletTexturePath, int columns, int rows) {
        super(stats, texturePath, bulletTexturePath, columns, rows);
    }

    public Enemy(Stats stats, String texturePath, String bulletTexturePath) {
        super(stats, texturePath, bulletTexturePath);
    }

    @Override
    protected void update(float delta) {
    }
}
