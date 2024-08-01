package utilities;

public class Label extends com.badlogic.gdx.scenes.scene2d.ui.Label {
    public Label(CharSequence text) {
        super(text, Render.skin);
    }

    public Label() {
        this("");
    }
}
