package game.utilities;

public enum Direction {
    NONE,
    DOWN,
    UP,
    RIGHT,
    LEFT,
    UP_RIGHT,
    UP_LEFT,
    DOWN_RIGHT,
    DOWN_LEFT;

    public static Direction convert(int index) {
        if (index < 0 || index >= values().length) {
            throw new IllegalArgumentException("Invalid index: " + index);
        }
        return values()[index];
    }
}
