package game.entities.items;

import game.entities.characters.playables.Playable;

public class Diamond extends Item{
//

    public Diamond() {
        super("Adel-Curse/assets/imgs/items/diamond/diamond.png", 2, 1);
    }

    @Override
    public void applyEffect(Playable character) {
        character.setTextureRegion("adelDiamond/adelDiamond.png", 2, 8);
    }

    @Override
    public ItemQuality getQuality() {
        return ItemQuality.Epic;
    }

    @Override
    public String getDescription() {
        return "Protege una bala del enemigo";
    }
}
