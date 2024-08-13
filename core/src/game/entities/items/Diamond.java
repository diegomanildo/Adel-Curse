package game.entities.items;

import game.entities.characters.playables.Playable;

public class Diamond extends Item {

    public Diamond() {
        super("imgs/items/diamond/diamond.png", 2, 1);
    }

    @Override
    public void applyEffect(Playable character) {
        character.setTextureRegion("imgs/characters/adelDiamond/adelDiamond.png", 2, 8); // texturePath esta mal
    }

    @Override
    public ItemQuality getQuality() {
        return ItemQuality.Epic;
    }

    @Override
    public String getDescription() {
        return "Protection";
    }
}
