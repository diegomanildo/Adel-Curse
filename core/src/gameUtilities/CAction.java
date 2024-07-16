package gameUtilities;

// Game Action
public enum CAction {
    UP,
    DOWN,
    LEFT,
    RIGHT,

    SHOOT_UP,
    SHOOT_DOWN,
    SHOOT_LEFT,
    SHOOT_RIGHT;

    @Override
    public String toString() {
        return super.toString().replace('_', ' ');
    }
}
