package game.entities.items;

public class Mushroom extends Item {
    private static final float SIZE_MULT = 1.1f;
    public Mushroom() {
        super(ItemQuality.Rare, "+Bullet size", "mushroom.png", 2, 1);
    }

    @Override
    protected void applyEffect() {
        owner.addDamage(owner.getDamage());
        owner.setSize(owner.getWidth() * SIZE_MULT, owner.getHeight() * SIZE_MULT);
    }
}
