package game.entities.items;

public class Bomb extends Item {
    public Bomb() {
        super(ItemQuality.Common, "Light the fuse and get ready for an explosive show.", "bomb.png", 2, 2);
    }

    @Override
    protected void applyEffect() {

    }
}
