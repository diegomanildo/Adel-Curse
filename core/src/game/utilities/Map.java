package game.utilities;

import com.badlogic.gdx.math.Vector2;
import game.rooms.Room;
import utilities.Random;

public class Map {
    private final Room[][] map;
    private int x;
    private int y;
    public final Vector2 initPosition;

    public Map(int quantity, int rows, int cols, RoomsArray rooms) throws InstantiationException, IllegalAccessException {
        map = new Room[rows][cols];

        Random random = new Random();

        int x = rows / 2;
        int y = cols / 2;

        map[y][x] = rooms.get(0).newInstance();

        for (int i = 0; i < quantity; i++) {
            Direction direction;

            int auxX;
            int auxY;

            do {
                auxX = x;
                auxY = y;
                direction = random.nextDirection(Direction.DOWN, Direction.LEFT);

                switch (direction) {
                    case UP:
                        auxY--;
                        break;
                    case DOWN:
                        auxY++;
                        break;
                    case LEFT:
                        auxX++;
                        break;
                    case RIGHT:
                        auxX--;
                        break;
                }
            } while (!isValidPosition(auxX, auxY));

            x = auxX;
            y = auxY;

            map[y][x] = rooms.get(random.nextInt(rooms.size)).newInstance();
        }

        initPosition = new Vector2(map[y][x].getWidth() / 2f, map[y][x].getHeight() / 2f);
        this.x = x;
        this.y = y;
    }

    private boolean isValidPosition(int x, int y) {
        return x >= 0 && x < map.length &&
               y >= 0 && y < map[0].length &&
               get(y, x) == null;
    }


    public Room get(int row, int col) {
        if (row < 0 || row >= map.length || col < 0 || col >= map[0].length) {
            return null;
        }
        return map[row][col];
    }

    public void print() {
        for (int row = 0; row < map.length; row++) {
            for (int column = 0; column < map[row].length; column++) {
                if (map[row][column] != null) {
                    String className = map[row][column].getClass().getSimpleName().substring(0, 2);
                    System.out.print("| " + className + " |");
                } else {
                    System.out.print("| ++ |");
                }
            }
            System.out.println();
        }
    }

    public void up() {
        if (get(y - 1, x) != null) y--;
    }

    public void down() {
        if (get(y + 1, x) != null) y++;
    }

    public void left() {
        if (get(y, x - 1) != null) x--;
    }

    public void right() {
        if (get(y, x + 1) != null) x++;
    }

    public Room getCurrent() {
        return map[y][x];
    }

    public boolean isValid(Direction direction) {
        int x = this.x;
        int y = this.y;

        switch (direction) {
            case DOWN:
                y++;
                break;
            case UP:
                y--;
                break;
            case RIGHT:
                x++;
                break;
            case LEFT:
                x--;
                break;
            default:
                throw new RuntimeException("Invalid direction: " + direction);
        }

        return get(y, x) == null;
    }
}
