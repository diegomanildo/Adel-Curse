package game.utilities;

import com.badlogic.gdx.utils.Array;
import game.entities.items.Item;

public final class ItemList extends Array<Class<? extends Item>> {
    @SafeVarargs
    public ItemList(Class<? extends Item>... items) {
        addAll(items);
    }

    public Item getRandomItem() {
        try {
            return super.random().getDeclaredConstructor().newInstance();
        } catch(Exception e) {
            throw new RuntimeException("Item is not valid (" + e.getClass() + ")");
        }
    }
}
