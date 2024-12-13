package game.items;

import com.badlogic.gdx.graphics.Color;
import utilities.FilePaths;

public class Diamond extends Item {
    private static final int ARMOR = 3;

    public Diamond() {
        super(ItemQuality.Rare, "+" + ARMOR + " armor", "diamond.png", 2, 1);
    }

    @Override
    protected void applyEffect() {
        owner.setFrames(FilePaths.IMAGES + "characters/adel/adelDiamond.png", owner.getColumns(), owner.getRows(), owner.getFrameDuration());
        owner.addArmor(3);
    }

    @Override
    public Color getItemColor() {
        return new Color(0xadd8e6);
    }
}
