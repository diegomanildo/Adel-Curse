package game.rooms;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Array;
import game.Game;
import game.entities.GameEntity;
import game.map.Door;
import game.map.RoomKinds;
import game.net.GameData;
import game.net.Server;
import game.screens.MultiplayerGameScreen;
import game.screens.OnePlayerGameScreen;
import game.utilities.Direction;
import game.utilities.Entities;
import game.utilities.Hitbox;
import utilities.FilePaths;
import utilities.Group;
import utilities.Render;

import java.util.ArrayList;

public class Room extends Group {
    protected final Door left = new Door(Direction.LEFT, new Hitbox(20f, 110f, 38f, 20f));
    protected final Door right = new Door(Direction.RIGHT, new Hitbox(310f, 110f, 38f, 20f));
    protected final Door up = new Door(Direction.UP, new Hitbox(174f, 190f, 20f, 30f));
    protected final Door down = new Door(Direction.DOWN, new Hitbox(174f, 20f, 20f, 30f));

    private String mapFile;
    private TiledMap map;
    private RoomKinds roomKind;
    protected final Entities entities;
    private final ArrayList<Door> doors;
    private boolean visited;
    private boolean isShowingDoors;

    protected Room(String mapFile, RoomKinds roomKind) {
        this.mapFile = mapFile;
        this.map = Render.assetManager.get(FilePaths.ROOMS + mapFile);
        this.roomKind = roomKind;
        this.entities = new Entities();
        this.doors = new ArrayList<>();
        this.visited = false;
        this.isShowingDoors = false;
    }

    // COPY CONSTRUCTOR
    private Room(Room other) {
        this.mapFile = other.mapFile;
        this.map = other.map;
        this.roomKind = other.roomKind;
        this.entities = other.entities;
        this.doors = other.doors;
        this.visited = other.visited;
        this.isShowingDoors = other.isShowingDoors;
    }

    protected Room(String mapFile) {
        this(mapFile, RoomKinds.OTHER);
    }

    @Override
    public void show() {
        super.show();
        hideDoors();
        showDoors();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (!canMove()) {
            if (isShowingDoors) {
                hideDoors();
            }
        } else {
            if (!isShowingDoors) {
                hideDoors();
                showDoors();
            }
        }
    }

    public void hideDoors() {
        for (MapLayer layer : map.getLayers()) {
            if (layer.getName().contains("door")) {
                layer.setVisible(false);
            }
        }

        isShowingDoors = false;
    }

    public void showDoors() {
        Array<MapLayer> layersToShow = new Array<>();

        for (Door door : doors) {
            String name = "door_" + door.getDirection().name().toLowerCase();
            MapLayer layer = map.getLayers().get(name);
            if (layer != null) {
                layersToShow.add(layer);
            } else {
                throw new RuntimeException("Invalid door layer: " + name);
            }
        }

        for (MapLayer layer : layersToShow) {
            layer.setVisible(true);
        }

        isShowingDoors = true;
    }

    public void createEntity(GameEntity e) {
        if (MultiplayerGameScreen.client != null && GameData.clientNumber == Server.HOST) {
            MultiplayerGameScreen.client.createEntity(e);
        }

        if (Game.game instanceof OnePlayerGameScreen || GameData.clientNumber == Server.HOST || (MultiplayerGameScreen.client != null && MultiplayerGameScreen.client.isSendingData())) {
            showEntity(e);
        }
    }

    public void showEntity(GameEntity e) {
        entities.add(e);
        if (getStage() != null) {
            getStage().addActor(e);
        }
    }

    public void removeEntity(GameEntity e, boolean isSendingServer) {
        if (MultiplayerGameScreen.client != null && !isSendingServer) {
            MultiplayerGameScreen.client.removeEntity(e.getId());
        }

        entities.remove(e);
        if (getStage() != null) {
            e.remove();
        }
    }

    @Override
    public boolean remove() {
        entities.forEach(GameEntity::remove);
        return super.remove();
    }

    public static Room copy(Room room) {
        return new Room(room);
    }

    public ArrayList<Door> getDoors() {
        return doors;
    }

    @Override
    public float getWidth() {
        return map.getProperties().get("width", Integer.class) * map.getProperties().get("tilewidth", Integer.class);
    }

    @Override
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

    public Entities getEntities() {
        return entities;
    }

    public String getMapFile() {
        return mapFile;
    }

    public void setMapFile(String mapFile) {
        this.mapFile = mapFile;
        this.map = Render.assetManager.get(FilePaths.ROOMS + mapFile);
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public void createDoors(Room leftRoom, Room rightRoom, Room upRoom, Room downRoom) {
        if (leftRoom != null) {
            addDoor(left);
        }

        if (rightRoom != null) {
            addDoor(right);
        }

        if (upRoom != null) {
            addDoor(up);
        }

        if (downRoom != null) {
            addDoor(down);
        }

        showDoors();
    }

    public boolean isShowingDoors() {
        return isShowingDoors;
    }

    public boolean canMove() {
        return Game.game.getEntities().getEnemies().isEmpty();
    }

    private void addDoor(Door door) {
        if (!doors.contains(door)) {
            doors.add(door);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        map.dispose();
    }
}