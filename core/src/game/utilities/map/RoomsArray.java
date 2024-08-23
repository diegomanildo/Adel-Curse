package game.utilities.map;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import game.rooms.Room;

public class RoomsArray extends Array<Class<? extends Room>> {
    @SafeVarargs
    public RoomsArray(Class<? extends Room> bossRoom, Class<? extends Room> shopRoom, Class<? extends Room> initRoom, Class<? extends Room>... others) {
        addAll(bossRoom, shopRoom, initRoom);
        addAll(others);
    }

    public Class<? extends Room> getBossRoom() {
        return get(0);
    }

    public Class<? extends Room> getShopRoom() {
        return get(1);
    }

    public Class<? extends Room> getInitialRoom() {
        return get(2);
    }

    @Override
    public Class<? extends Room> random() {
        int r = MathUtils.random(2, size - 1);
        return get(r);
    }
}
