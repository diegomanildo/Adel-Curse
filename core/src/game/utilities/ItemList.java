package game.utilities;

import com.badlogic.gdx.utils.Array;
import game.items.Item;
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
            } while (Utils.r.nextInt(0, 100) <= item.getQuality().getPercentage());

            return item;
        } catch(Exception e) {
            throw new RuntimeException("Item is not valid (" + e.getClass() + ")");
        }
    }
}
