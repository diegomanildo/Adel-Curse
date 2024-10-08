package game.entities.items;

import utilities.Log;

public class Shell extends Item {
    private static final int ARMOR = 2;

    public Shell() {
        super(ItemQuality.Rare, "+" + ARMOR + " armor", "shell.png", 2, 1);
    }

    @Override
    protected void applyEffect() {
        owner.addArmor(ARMOR);
        Log.log(String.valueOf(owner.getArmor()));
    }
}
