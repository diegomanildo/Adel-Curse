package game.utilities;

import com.badlogic.gdx.utils.Array;
import game.entities.items.Item;

public final class ItemList extends Array<Class<? extends Item>> {
    @SafeVarargs
    public ItemList(Class<? extends Item>... items) {
        addAll(items);
    }
}
