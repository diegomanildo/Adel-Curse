package game.entities.items;

import game.entities.characters.playables.Playable;

public class Bomb extends Item {

    public Bomb() {
        super("imgs/items/bomb/bombExplotion.png", 2, 1);
    }

    @Override
    public void applyEffect(Playable character) {

    }

    @Override
    public ItemQuality getQuality() {
        return ItemQuality.Common;
    }

    @Override
    public String getDescription() {
        return "Lo obtiene el personaje y al presionar una tecla se libera explota a los 5 segundos";
    }
}
