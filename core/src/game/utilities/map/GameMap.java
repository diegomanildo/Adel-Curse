package game.utilities.map;

import com.badlogic.gdx.math.Vector2;
import game.rooms.Room;
import game.utilities.Direction;
import utilities.Log;

public class GameMap {
    private int currentRow;
    private int currentCol;

    private final RoomMap map;
    public final Vector2 playerInitPosition;

    public GameMap(int quantity, int rows, int columns, RoomsArray rooms) {
        map = new RoomMap(quantity, rows, columns, rooms);
        this.currentRow = (int) map.finalCreated.x;
        this.currentCol = (int) map.finalCreated.y;
        playerInitPosition = new Vector2(getCurrent().getWidth() / 2f, getCurrent().getHeight() / 2f);
    }

    public void print() {
        Log.log("Map:");
        for (int row = 0; row < getRows(); row++) {
            for (int column = 0; column < getColumns(); column++) {
                Room room = map.getRoom(row, column);
                Room current = getCurrent();

                if (room == null) {
                    System.out.print(".. ");
                } else if (room.equals(current)) {
                    System.out.print("PJ ");
                } else {
                    String initials = room.getClass().getSimpleName().substring(0, 2);
                    System.out.print(initials + " ");
                }
            }

            System.out.println();
        }
    }

    public void changeRoom(Direction direction) {
        switch (direction) {
            case UP:
                currentRow--;
                break;
            case DOWN:
                currentRow++;
                break;
            case RIGHT:
                currentCol++;
                break;
            case LEFT:
                currentCol--;
                break;
            default:
                throw new RuntimeException("Invalid direction: " + direction);
        }
    }

    public Room getCurrent() {
        return map.getRoom(currentRow, currentCol);
    }

    public int getRows() {
        return map.getRows();
    }

    public int getColumns() {
        return map.getColumns();
    }

    public int getCurrentCol() {
        return currentCol;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentCol(int currentCol) {
        this.currentCol = currentCol;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }
}
