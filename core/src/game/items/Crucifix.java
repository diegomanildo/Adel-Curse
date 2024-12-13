package game.items;

import com.badlogic.gdx.graphics.Color;

public class Crucifix extends Item {
    public Crucifix() {
        super(ItemQuality.Legendary, "Revive a random death player", "crucifix.png", 2, 1);
    }

    @Override
    protected void applyEffect() {

    }

    @Override
    public Color getItemColor() {
        return Color.WHITE;
    }
}
