package game.map;

import com.badlogic.gdx.graphics.Color;

public enum RoomKinds {
    CURRENT(new Color(0xadd8e6)),
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
}