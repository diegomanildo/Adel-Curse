package game.utilities;

public enum GameAction {
    UP,
    DOWN,
    LEFT,
    RIGHT,

    SHOOT_UP,
    SHOOT_DOWN,
    SHOOT_LEFT,
    SHOOT_RIGHT, INTERACT;

    @Override
    public String toString() {
        return super.toString().replace('_', ' ');
    }
}
