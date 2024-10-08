package game.entities.items;

import utilities.Log;

public class RandomCard extends Item {
    public RandomCard() {
        super(ItemQuality.Rare, "???", "randomCard.png", 2, 1);
    }

    @Override
    protected void applyEffect() {
        Item randomItem;

        do {
            randomItem = ITEMS.getRandomItem();
        } while (randomItem instanceof RandomCard);

        randomItem.changeOwnerTo(owner);
        randomItem.applyEffect();

        Log.debug("Random item: " + randomItem.getClass().getSimpleName());
    }
}
