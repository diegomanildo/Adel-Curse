package game.entities.characters.enemies;

import game.entities.characters.Character;

public abstract class Enemy extends Character {
    public Enemy(String texturePath, String bulletTexturePath) {
        super(texturePath, bulletTexturePath);
    }
}
