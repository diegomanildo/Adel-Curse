package game.enemies;

import game.characters.Character;

public abstract class Enemy extends Character {
    public Enemy(String texturePath, String bulletTexturePath) {
        super(texturePath, bulletTexturePath);
    }
}
