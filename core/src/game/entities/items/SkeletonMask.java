package game.entities.items;

import game.entities.characters.playables.Playable;

public class SkeletonMask extends Item {
    public SkeletonMask() {
        super("imgs/items/skeletonMask/skeletonMask.png", 2, 1);
    }

    @Override
    public void applyEffect(Playable character) {
        character.itemList.add(new SkeletonMask());
        character.setDamage((character.getDamage() + 2));
    }

    @Override
    public ItemQuality getQuality() {
        return ItemQuality.Epic;
    }

    @Override
    public String getDescription() {
        return "aumenta +2 el daño";
    }
}
