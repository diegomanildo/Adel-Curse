package game.entities.items;

public class Candy extends Item {
    private static final int HP_UP = 1;

    public Candy() {
        super(ItemQuality.Common, "+" + HP_UP + " HP!", "candy.png", 2, 1);
    }

    @Override
    protected void applyEffect() {
        owner.addHp(HP_UP);
    }
}
