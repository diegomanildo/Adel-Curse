package game.items;

import com.badlogic.gdx.graphics.Color;

public class Shell extends Item {
    private static final int ARMOR = 1;

    public Shell() {
        super(ItemQuality.Common, "+" + ARMOR + " armor", "shell.png", 2, 1);
    }

    @Override
    protected void applyEffect() {
        owner.addArmor(ARMOR);
    }

    @Override
    public Color getItemColor() {
        return Color.LIGHT_GRAY;
    }
}
