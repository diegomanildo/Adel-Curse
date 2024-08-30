package utilities;

import com.badlogic.gdx.graphics.Texture;

public class Image extends com.badlogic.gdx.scenes.scene2d.ui.Image {
    public Image(String filePath) {
        super(new Texture(filePath));
    }
}
