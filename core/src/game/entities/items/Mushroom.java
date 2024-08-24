package game.entities.items;

public class Mushroom extends Item {
    public Mushroom() {
        super(ItemQuality.Rare, "+Bullet size", "mushroom.png", 2, 1);
    }

    @Override
    protected void applyEffect() {
        
    }
}
