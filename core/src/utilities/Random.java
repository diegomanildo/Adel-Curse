package utilities;

import game.utilities.Direction;

public final class Random extends java.util.Random {
    public <T> T next(T[] array) {
        return array[nextInt(array.length)];
    }

    public Direction nextDirection(Direction origin, Direction bound) {
        return Direction.convert(nextInt(origin.ordinal(), bound.ordinal()));
    }
}
