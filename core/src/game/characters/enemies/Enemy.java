package game.characters.enemies;

import game.characters.playable.Character;

public abstract class Enemy extends Character {
    public Enemy(String texturePath, String bulletTexturePath) {
        super(texturePath, bulletTexturePath);
    }
}
