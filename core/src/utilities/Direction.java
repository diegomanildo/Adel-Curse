package utilities;

import utilities.exceptions.DirectionNotValidException;

public enum Direction {
    None,
    Down,
    Up,
    Right,
    Left, UpRight, UpLeft, DownRight, DownLeft;

    public int toInt() {
        switch (this) {
            case None:
                return 0;
            case Down:
                return 1;
            case Up:
                return 2;
            case Right:
                return 3;
            case Left:
                return 4;
            default:
                throw new DirectionNotValidException("");
        }
    }
}
