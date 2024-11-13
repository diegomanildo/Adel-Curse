package game.entities.items;

public class UpCard extends Item {
    public UpCard() {
        super(ItemQuality.Rare, "", "upCard.png", 2, 1);
    }

    @Override
    protected void applyEffect() {
        owner.addHp(2);
    }
}
