package utilities;

public class ProgressBar extends com.badlogic.gdx.scenes.scene2d.ui.ProgressBar {
    public ProgressBar(float min, float max, float stepSize, boolean vertical) {
        super(min, max, stepSize, vertical, Render.skin);
    }
}
