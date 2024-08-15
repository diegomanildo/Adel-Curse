package utilities;

public class ShapeRenderer extends com.badlogic.gdx.graphics.glutils.ShapeRenderer {
    public void rect(float x, float y, float width, float height, float thickness) {
        if (thickness <= 0) {
            return;
        }

        ShapeType type = getCurrentType();
        set(ShapeType.Filled);

        rect(x, y, width, thickness);
        rect(x, y + height - thickness, width, thickness);
        rect(x, y + thickness, thickness, height - 2 * thickness);
        rect(x + width - thickness, y + thickness, thickness, height - 2 * thickness);

        set(type);
    }
}
