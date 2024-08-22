package game.utilities.map;

import com.badlogic.gdx.math.Vector2;
import game.rooms.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RoomMap {
    private static final Random RANDOM = new Random();

    private final Room[][] map;
    private final int rows;
    private final int columns;
    private final RoomsArray roomTypes;

    protected final Vector2 initRoom; // Initial room position
    private final List<Vector2> openPositions = new ArrayList<>();

    public RoomMap(int quantity, int rows, int columns, RoomsArray roomTypes) {
        this.rows = rows;
        this.columns = columns;
        this.roomTypes = roomTypes;

        map = new Room[rows][columns];

        // Generate the initial room at the middle
        int initialRow = rows / 2;
        int initialColumn = columns / 2;
        createRoomAt(initialRow, initialColumn);
        initRoom = new Vector2(initialRow, initialColumn);

        generateMap(quantity - 1); // Subtract 1 since the initial room is already created
    }

    private void generateMap(int quantity) {
        while (quantity > 0 && !openPositions.isEmpty()) {
            Vector2 position = openPositions.remove(RANDOM.nextInt(openPositions.size()));
            createRoomAt((int) position.x, (int) position.y);
            quantity--;
        }
    }

    private void createRoomAt(int row, int column) {
        // Select a random room type from the room types
        Class<? extends Room> randomRoom = roomTypes.random();

        try {
            map[row][column] = randomRoom.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Room class \"" + randomRoom.getName() + "\" is not valid (" + e.getClass() + ")");
        }

        // Add adjacent positions to openPositions for possible future room creation
        addAdjacentPositions(row, column);
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
