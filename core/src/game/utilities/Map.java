package game.utilities;

import com.badlogic.gdx.math.Vector2;
import game.rooms.Room;

public class Map {
    private final Room[][] map;
    public final Vector2 playerInitPosition;
    private int row; // Current row
    private int col; // Current col

    public Map(int quantityOfRooms, int rows, int cols, RoomsArray rooms) throws InstantiationException, IllegalAccessException {
        map = new Room[rows][cols];
        row = 0;
        col = 0;
        map[row][col] = rooms.getInitialRoom().newInstance();

        playerInitPosition = new Vector2(getCurrent().getWidth() / 2f, getCurrent().getHeight() / 2f);

        generateRooms(quantityOfRooms - 1, rooms);
    }

    private void generateRooms(int quantityOfRooms, RoomsArray rooms) throws InstantiationException, IllegalAccessException {
        while (quantityOfRooms > 0) {
            int direction = (int) (Math.random() * 4);
            switch (direction) {
                case 0: // up
                    if (isValidPosition(row - 1, col)) {
                        row--;
                    }
                    break;
                case 1: // down
                    if (isValidPosition(row + 1, col)) {
                        row++;
                    }
                    break;
                case 2: // left
                    if (isValidPosition(row, col - 1)) {
                        col--;
                    }
                    break;
                case 3: // right
                    if (isValidPosition(row, col + 1)) {
                        col++;
                    }
                    break;
            }

            if (map[row][col] == null) {
                map[row][col] = rooms.randomRoom().newInstance();
                quantityOfRooms--;
            }
        }
    }

    private boolean isValidPosition(int row, int col) {
        return row >= 0 && col >= 0 && row < map.length && col < map[0].length;
    }

    public Room get(int row, int col) {
        return isValidPosition(row, col) ? map[row][col] : null;
    }

    public void print() {
        for (Room[] row : map) {
            for (Room room : row) {
                if (room != null) {
                    String roomName = room.getClass().getSimpleName().substring(0, 2);
                    System.out.print(roomName + " ");
                } else {
                    System.out.print("++ ");
                }
            }
            System.out.println();
        }
    }

    public void up() {
        if (get(row - 1, col) != null) row--;
    }

    public void down() {
        if (get(row + 1, col) != null) row++;
    }

    public void left() {
        if (get(row, col - 1) != null) col--;
    }

    public void right() {
        if (get(row, col + 1) != null) col++;
    }

    public Room getCurrent() {
        return map[row][col];
    }

    public int getCurrentRow() {
        return row;
    }

    public int getCurrentCol() {
        return col;
    }

    public Room[][] getMap() {
        return map;
    }
}
