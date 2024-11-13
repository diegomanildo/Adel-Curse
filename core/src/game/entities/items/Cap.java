package game.entities.items;

import utilities.FilePaths;

public class Cap extends Item {
    public Cap() {
        super(ItemQuality.Rare, "black bullets", "cap.png", 2, 1);
    }

    @Override
    protected void applyEffect() {
        owner.setBulletTexturePath("adel/blackBullet.png");
        owner.addDamage(1);
    }
}
