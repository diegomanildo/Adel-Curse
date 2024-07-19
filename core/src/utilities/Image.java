package utilities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import gameUtilities.GameObject;

public class Image extends GameObject {
    private final Texture texture;

    public Image(String filePath) {
        texture = new Texture(filePath);
    }

    @Override
    public void draw(Batch batch) {
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    public void setFullScreen() {
        setSize(Render.screenSize.width, Render.screenSize.height);
    }
}
