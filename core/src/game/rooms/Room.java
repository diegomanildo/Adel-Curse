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
    protected static final Door LEFT = new Door(Direction.LEFT, new Hitbox(20f, 110f, 38f, 20f));
    protected static final Door RIGHT = new Door(Direction.RIGHT, new Hitbox(310f, 110f, 38f, 20f));
    protected static final Door UP = new Door(Direction.UP, new Hitbox(174f, 190f, 20f, 30f));
    protected static final Door DOWN = new Door(Direction.DOWN, new Hitbox(174f, 20f, 20f, 30f));

    protected static final Door[] DOORS = new Door[] {
            LEFT,
            RIGHT,
            UP,
            DOWN
    };

    private final TiledMap map;
    private RoomKinds roomKind;
    protected final Entities entities;
    private final ArrayList<Door> doors;
    private boolean visited;
    private boolean isShowingDoors;

    protected Room(String mapFile, RoomKinds roomKind) {
        this.map = Render.assetManager.get(FilePaths.ROOMS + mapFile);
        this.roomKind = roomKind;
        this.entities = new Entities();
        this.doors = new ArrayList<>();
        this.visited = false;
    }

    private Room(Room other) {
        this.map = other.map;
        this.roomKind = other.roomKind;
        this.entities = other.entities;
        this.doors = other.doors;
        this.visited = other.visited;
        this.isShowingDoors = other.isShowingDoors;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (!canMove()) {
            hideDoors();
        } else {
            showDoors();
        }
    }

    public void hideDoors() {
        Array<MapLayer> doorsToHide = new Array<>();

        for (MapLayer layer : map.getLayers()) {
            if (layer.getName().contains("door")) {
                doorsToHide.add(layer); // Agregar la capa a la lista temporal
            }
        }

        // Modificar las capas después de la iteración
        for (MapLayer layer : doorsToHide) {
            layer.setVisible(false);
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

        // Ahora modifica las capas fuera del bucle
        for (MapLayer layer : layersToShow) {
            layer.setVisible(true);
        }

        isShowingDoors = true;
    }

    @Override
    public void show() {
        super.show();
        if (Game.game.getEntities().getEnemies().isEmpty() && !isShowingDoors) {
            showDoors();
        } else if (isShowingDoors) {
            hideDoors();
        }
    }

    public void createEntity(GameEntity e) {
        if (MultiplayerGameScreen.client != null && GameData.clientNumber == Server.OWNER) {
            MultiplayerGameScreen.client.createEntity(e);
        }

        if (Game.game instanceof OnePlayerGameScreen || GameData.clientNumber == Server.OWNER || (MultiplayerGameScreen.client != null && MultiplayerGameScreen.client.isSendingData())) {
            entities.add(e);
            getStage().addActor(e);
            Game.game.getEntities().add(e);
        }
    }

    @Override
    public boolean remove() {
        boolean b = super.remove();
        entities.forEach(e -> {
            e.remove();
            Game.game.getEntities().remove(e);
        });
        return b;
    }

    public static Room copy(Room room) {
        return new Room(room);
    }

    protected Room(String mapFile) {
        this(mapFile, RoomKinds.OTHER);
    }

    public Door[] getDoors() {
        return doors.toArray(new Door[0]);
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

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public void createDoors(Room leftRoom, Room rightRoom, Room upRoom, Room downRoom) {
        if (leftRoom != null) {
            addDoor(LEFT);
            leftRoom.addDoor(RIGHT);
        }

        if (rightRoom != null) {
            addDoor(RIGHT);
            rightRoom.addDoor(LEFT);
        }

        if (upRoom != null) {
            addDoor(UP);
            upRoom.addDoor(DOWN);
        }

        if (downRoom != null) {
            addDoor(DOWN);
            downRoom.addDoor(UP);
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