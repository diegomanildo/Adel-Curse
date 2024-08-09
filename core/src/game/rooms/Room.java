package game.rooms;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import utilities.FilePaths;

public abstract class Room {
    private final TiledMap map;

    protected Room(String mapFile) {
        map = new TmxMapLoader().load(FilePaths.ROOMS + mapFile);
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
}