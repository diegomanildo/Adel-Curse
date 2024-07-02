package utilities;

import com.ac.Render;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Image extends Sprite {
    public Image(String filePath) {
        super(new Texture(filePath));
    }

    public void draw() {
        super.draw(Render.batch);
    }
}
