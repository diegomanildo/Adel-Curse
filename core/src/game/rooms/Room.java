package game.rooms;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import utilities.FilePaths;

public abstract class Room {
    private final TiledMap map;

    public Room(String tmxFile) {
        this.map = new TmxMapLoader().load(FilePaths.ROOMS + tmxFile);
    }
}
