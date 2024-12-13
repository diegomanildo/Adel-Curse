package game.items;

public class Cap extends Item {
    private static final int DAMAGE = 1;

    public Cap() {
        super(ItemQuality.Common,  "+" + DAMAGE + " damage and Black Bullets", "cap.png", 2, 1);
    }

    @Override
    protected void applyEffect() {
        owner.setBulletTexturePath("adel/blackBullet.png", true);
        owner.addDamage(DAMAGE);
    }
}
