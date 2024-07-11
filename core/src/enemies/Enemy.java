package enemies;

import characters.Character;

public abstract class Enemy extends Character {
    public Enemy(String texturePath, String bulletTexturePath, float x, float y) {
        super(texturePath, bulletTexturePath, x, y);
    }
}
