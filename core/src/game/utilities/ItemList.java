package game.utilities;

import com.badlogic.gdx.utils.Array;
import game.Game;
import game.items.Crucifix;
import game.items.Item;
import game.screens.OnePlayerGameScreen;
import utilities.Utils;

public final class ItemList extends Array<Class<? extends Item>> {
    @SafeVarargs
    public ItemList(Class<? extends Item>... items) {
        addAll(items);
    }

    public Item getRandomItem() {
        try {
            Item item;

            do {
                item = super.random().getDeclaredConstructor().newInstance();
            } while (!canContinue(item));

            return item;
        } catch(Exception e) {
            throw new RuntimeException("Item is not valid (" + e.getClass() + ")");
        }
    }

    private boolean canContinue(Item item) {
        if (item instanceof Crucifix && Game.game instanceof OnePlayerGameScreen) { // You can not have a crucifix in one player
            return false;
        } else {
            return Utils.r.nextInt(0, 100) <= item.getQuality().getPercentage();
        }
    }
}
