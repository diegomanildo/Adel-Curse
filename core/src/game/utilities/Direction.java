package game.utilities;

public enum Direction {
    NONE,
    LEFT,
    RIGHT,
    UP,
    DOWN,
    UP_RIGHT,
    UP_LEFT,
    DOWN_RIGHT,
    DOWN_LEFT;

    public static Direction parseDirection(String text) {
        for (Direction d : values()) {
            if (d.toString().equals(text)) {
                return d;
            }
        }

        throw new RuntimeException("Invalid direction: " + text);
    }
}
