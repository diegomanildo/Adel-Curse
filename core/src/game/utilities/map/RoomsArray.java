package game.utilities.map;

import com.badlogic.gdx.utils.Array;
import game.rooms.Room;

public class RoomsArray extends Array<Class<? extends Room>> {
    @SafeVarargs
    public RoomsArray(Class<? extends Room> initRoom, Class<? extends Room> bossRoom, Class<? extends Room>... others) {
        addAll(initRoom, bossRoom);
        addAll(others);
    }

    public Class<? extends Room> getInitialRoom() {
        return get(0);
    }

    public Class<? extends Room> getBossRoom() {
        return get(1);
    }
}
