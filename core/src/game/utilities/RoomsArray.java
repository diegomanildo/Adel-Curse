package game.utilities;

import com.badlogic.gdx.utils.Array;
import game.rooms.Room;
import utilities.Random;

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

    public Class<? extends Room> randomRoom() {
        return get(new Random().nextInt(size));
    }
}
