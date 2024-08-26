package game.rooms;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.Actor;
import game.entities.GameEntity;
import game.map.RoomKinds;
import game.utilities.Entities;
import utilities.FilePaths;
import utilities.Group;

public class Room extends Group {
    private final TiledMap map;
    private RoomKinds roomKind;
    private final Entities entities;

    protected Room(String mapFile, RoomKinds roomKind) {
        this.map = new TmxMapLoader().load(FilePaths.ROOMS + mapFile);
        this.roomKind = roomKind;
        this.entities = new Entities();
    }

    private Room(Room other) {
        this.map = other.map;
        this.roomKind = other.roomKind;
        this.entities = other.entities;
    }

    @Override
    public void show() {
        super.show();
        entities.forEach(e -> getStage().addActor(e));
    }

    public void createEntity(GameEntity e) {
        entities.add(e);
    }

    @Override
    public boolean remove() {
        boolean b = super.remove();
        entities.forEach(Actor::remove);
        return b;
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