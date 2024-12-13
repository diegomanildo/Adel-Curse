package game.items;

public class Candy extends Item {
    private static final int HP = 1;

    public Candy() {
        super(ItemQuality.Common, "+" + HP + " HP", "candy.png", 2, 1);
    }

    @Override
    protected void applyEffect() {
        owner.addHp(HP);
    }
}
