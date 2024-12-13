package game.items;

import com.badlogic.gdx.graphics.Color;

public class Mushroom extends Item {
    private static final float SIZE_MULTIPLIER = 1.1f;
    private static final float MAX_SIZE = 40;

    public Mushroom() {
        super(ItemQuality.Epic, "+Bullet size", "mushroom.png", 2, 1);
    }

    @Override
    protected void applyEffect() {
        if (owner.getHeight() < MAX_SIZE) {
            owner.addDamage(owner.getDamage());
            owner.setSize(owner.getWidth() * SIZE_MULTIPLIER, owner.getHeight() * SIZE_MULTIPLIER);
        }
    }

    @Override
    public Color getItemColor() {
        return Color.RED;
    }
}
