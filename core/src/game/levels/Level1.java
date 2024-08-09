package game.levels;

import com.badlogic.gdx.utils.Array;
import game.rooms.Room;
import game.rooms.StoneRoom;

public final class Level1 extends Level {

    @Override
    public Array<Room> getRooms() {
        Array<Room> rooms = new Array<>();
        rooms.addAll(
                new StoneRoom()
        );

        return rooms;
    }
}
