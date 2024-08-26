package game.rooms;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import game.map.RoomKinds;
import utilities.FilePaths;
import utilities.Group;

public class Room extends Group {
    private final TiledMap map;
    private RoomKinds roomKind;

    protected Room(String mapFile, RoomKinds roomKind) {
        map = new TmxMapLoader().load(FilePaths.ROOMS + mapFile);
        this.roomKind = roomKind;
    }

    private Room(Room other) {
        this.map = other.map;
        this.roomKind = other.roomKind;
    }

    public static Room copy(Room room) {
        return new Room(room);
    }

    protected Room(String mapFile) {
        this(mapFile, RoomKinds.OTHER);
    }

    public float getWidth() {
        return map.getProperties().get("width", Integer.class) * map.getProperties().get("tilewidth", Integer.class);
    }

    public float getHeight() {
        return map.getProperties().get("height", Integer.class) * map.getProperties().get("tileheight", Integer.class);
    }

    public TiledMap getMap() {
        return map;
    }

    public RoomKinds getKind() {
        return roomKind;
    }

    public void setKind(RoomKinds roomKind) {
        this.roomKind = roomKind;
    }
}