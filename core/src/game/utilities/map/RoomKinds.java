package game.utilities.map;

import com.badlogic.gdx.graphics.Color;

public enum RoomKinds {
    CURRENT(Color.GREEN),
    SHOP(Color.YELLOW),
    BOSS(Color.RED),
    OTHER(Color.GRAY);

    private final Color color;

    RoomKinds(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
};