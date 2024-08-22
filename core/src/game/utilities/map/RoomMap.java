package game.utilities.map;

import com.badlogic.gdx.math.Vector2;
import game.rooms.Room;

import java.util.Random;

public class RoomMap {
    private static final Random RANDOM = new Random();

    private final Room[][] map;

    private final int rows;
    private final int columns;
    private final RoomsArray roomTypes;

    public Vector2 finalCreated;

    public RoomMap(int quantity, int rows, int columns, RoomsArray roomTypes) {
        this.rows = rows;
        this.columns = columns;
        this.roomTypes = roomTypes;

        map = new Room[rows][columns];
        generateMap(quantity);
    }

    private void generateMap(int quantity) {
        while (quantity > 0) {
            generateRoom();
            quantity--;
        }
    }

    private void generateRoom() {
        int row, column;

        do {
            row = RANDOM.nextInt(rows);
            column = RANDOM.nextInt(columns);
        } while (isOccupiedAt(row, column));

        Class<? extends Room> randomRoom = roomTypes.random();

        try {
            map[row][column] = randomRoom.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Room class \"" + randomRoom.getName() + "\" is not valid (" + e.getClass() + ")");
        }

        finalCreated = new Vector2(row, column);
    }

    // Returns if current position is a null Room
    public boolean isOccupiedAt(int row, int column) {
        return getRoom(row, column) != null;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public Room getRoom(int row, int column) {
        return map[row][column];
    }
}
