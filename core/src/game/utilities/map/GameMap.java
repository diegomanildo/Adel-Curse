package game.utilities.map;

import com.badlogic.gdx.math.Vector2;
import game.rooms.Room;
import game.utilities.Direction;

public class GameMap extends RoomMap {
    private int currentRow;
    private int currentCol;

    public final Vector2 playerInitPosition;

    public GameMap(int quantity, int rows, int columns, RoomsArray rooms) {
        super(quantity, rows, columns, rooms);
        this.currentRow = (int) initRoom.x;
        this.currentCol = (int) initRoom.y;
        playerInitPosition = new Vector2(getCurrent().getWidth() / 2f, getCurrent().getHeight() / 2f);
    }

    public void changeRoom(Direction direction) {
        switch (direction) {
            case UP:
                currentRow++;
                break;
            case DOWN:
                currentRow--;
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

    @Override
    public Room getRoomAt(int row, int column) {
        Room room = super.getRoomAt(row, column);

        // if room is current set kind to Current
        if (room != null && room.equals(getCurrent())) {
            room.setKind(RoomKinds.CURRENT);
        }

        return room;
    }

    public Room getCurrent() {
        return super.getRoomAt(currentRow, currentCol);
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
