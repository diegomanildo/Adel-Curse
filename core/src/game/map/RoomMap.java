package game.map;

import com.badlogic.gdx.math.Vector2;
import game.rooms.BossRoom;
import game.rooms.EnemyRoom;
import game.rooms.Room;
import utilities.Group;
import utilities.Utils;

import java.util.ArrayList;
import java.util.List;

public class RoomMap extends Group {
    public static Room[][] map;
    public static boolean isInitRoom;

    private final int rows;
    private final int columns;
    private final RoomsArray roomTypes;

    public Vector2 initRoom; // Initial room position
    private final List<Vector2> openPositions = new ArrayList<>();

    public RoomMap(int quantity, int rows, int columns, RoomsArray roomTypes) {
        this.rows = rows;
        this.columns = columns;
        this.roomTypes = roomTypes;

        map = new Room[rows][columns];

        generateMap(quantity);
    }

    private void generateMap(int quantity) {
        // Generate the initial room at the middle
        int initialRow = rows / 2;
        int initialColumn = columns / 2;
        initRoom = new Vector2(initialRow, initialColumn);

        createRoomAt(initialRow, initialColumn, roomTypes.getInitialRoom(), true);

        quantity--;

        while (quantity > 0 && !openPositions.isEmpty()) {
            Vector2 position = openPositions.remove(Utils.r.nextInt(openPositions.size()));
            createRoomAt((int) position.x, (int) position.y);
            quantity--;
        }

        placeBossRoom();
        placeShopRoom();

        MapHelper.createDoorsForMap(map);
    }

    private void createRoomAt(int row, int column, Class<? extends Room> roomClass, boolean initRoom) {
        if (roomClass == null) {
            roomClass = roomTypes.random();
        }

        try {
            isInitRoom = initRoom;
            Room room = roomClass.getDeclaredConstructor().newInstance();

            if (initRoom && room instanceof EnemyRoom) {
                ((EnemyRoom) room).setSpawnEntities(false);
            }

            map[row][column] = room;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        addAdjacentPositions(row, column);
    }

    private void createRoomAt(int row, int column) {
        createRoomAt(row, column, null, false);
    }

    private void addAdjacentPositions(int row, int column) {
        if (row > 0 && isFreeAt(row - 1, column)) {
            openPositions.add(new Vector2(row - 1, column));
        }

        if (row < rows - 1 && isFreeAt(row + 1, column)) {
            openPositions.add(new Vector2(row + 1, column));
        }

        if (column > 0 && isFreeAt(row, column - 1)) {
            openPositions.add(new Vector2(row, column - 1));
        }

        if (column < columns - 1 && isFreeAt(row, column + 1)) {
            openPositions.add(new Vector2(row, column + 1));
        }
    }

    private void placeBossRoom() {
        Vector2 farthestRoomPos = null;
        float maxDistance = 0;

        // Find the farthest room from the initial room
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                if (map[row][column] != null) {
                    float distance = initRoom.dst(row, column);
                    if (distance > maxDistance) {
                        maxDistance = distance;
                        farthestRoomPos = new Vector2(row, column);
                    }
                }
            }
        }

        if (farthestRoomPos != null) {
            createRoomAt((int) farthestRoomPos.x, (int) farthestRoomPos.y, roomTypes.getBossRoom(), false);
        }
    }

    private void placeShopRoom() {
        List<Vector2> possibleLocations = new ArrayList<>();

        // Collect all available spots where we could place the shop
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                if (map[row][column] != null && !(row == initRoom.x && column == initRoom.y) && !map[row][column].getClass().isAssignableFrom(BossRoom.class)) {
                    possibleLocations.add(new Vector2(row, column));
                }
            }
        }

        // Place the ShopRoom in a random location from the available spots
        if (!possibleLocations.isEmpty()) {
            Vector2 shopPosition = possibleLocations.get(Utils.r.nextInt(possibleLocations.size()));
            createRoomAt((int) shopPosition.x, (int) shopPosition.y, roomTypes.getShopRoom(), false);
        }
    }

    // Returns if the room is null
    public boolean isFreeAt(int row, int column) {
        return getRoomAt(row, column) == null;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public Room getRoomAt(int row, int column) {
        return map[row][column];
    }
}