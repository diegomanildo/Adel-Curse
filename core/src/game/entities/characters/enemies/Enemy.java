package game.entities.characters.enemies;

import game.entities.characters.Character;

public abstract class Enemy extends Character {

    public Enemy(String texturePath, String bulletTexturePath, int columns, int rows) {
        super(texturePath, bulletTexturePath, columns, rows);
    }

    public Enemy(String texturePath, String bulletTexturePath) {
        super(texturePath, bulletTexturePath);
    }

    @Override
    protected void update(float delta) {
    }
}
