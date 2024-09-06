package utilities;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class ImageButton extends com.badlogic.gdx.scenes.scene2d.ui.ImageButton {
    public ImageButton() {
        super(Render.skin);
    }

    public ImageButton(Drawable imageUp) {
        super(imageUp);
    }

    public ImageButton(Drawable imageUp, Drawable imageDown) {
        super(imageUp, imageDown);
    }

    public ImageButton(Drawable imageUp, Drawable imageDown, Drawable imageChecked) {
        super(imageUp, imageDown, imageChecked);
    }
}
