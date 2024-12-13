package game.items;

import com.badlogic.gdx.graphics.Color;

public class UpCard extends Item {
    private static final int HP = 3;

    public UpCard() {
        super(ItemQuality.Rare, "+" + HP + " HP", "upCard.png", 2, 1);
    }

    @Override
    protected void applyEffect() {
        owner.addHp(HP);
    }

    @Override
    public Color getItemColor() {
        return Color.BLUE;
    }
}
