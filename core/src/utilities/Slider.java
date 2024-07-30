package utilities;

public class Slider extends com.badlogic.gdx.scenes.scene2d.ui.Slider {
    public Slider(float min, float max, float stepSize, boolean vertical) {
        super(min, max, stepSize, vertical, Render.skin);
    }
}
