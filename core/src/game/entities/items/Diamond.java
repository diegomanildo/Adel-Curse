package game.entities.items;

import utilities.FilePaths;

public class Diamond extends Item {
    public Diamond() {
        super(ItemQuality.Epic, "+armor", "diamond.png", 2, 1);
    }

    @Override
    protected void applyEffect() {
        owner.setFrames(FilePaths.IMAGES + "characters/adel/adelDiamond.png", owner.getColumns(), owner.getRows(), owner.getFrameDuration());
        owner.addArmor(3);
    }
}
