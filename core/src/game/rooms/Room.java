package game.rooms;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import game.utilities.Camera2D;
import game.utilities.Drawable;
import utilities.Render;

public class Room implements Drawable {
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer renderer;
    private final Camera2D camera;

    private static final float OFFSET = 17f;

    public Room(String tmxFile, Camera2D camera) {
        this.camera = camera;
        this.map = new TmxMapLoader().load(tmxFile);
        this.renderer = new OrthogonalTiledMapRenderer(map);

        this.camera.setPosition(getWidth() / 2f, getHeight() / 2f);
        this.camera.setSize(getWidth() - OFFSET * 2f, getHeight() - OFFSET * 2f);
        this.camera.update();
    }

    @Override
    public void draw() {
        camera.update();
        renderer.setView(camera);
        renderer.render();
        if (Render.isDebugging()) {
            drawHitbox(camera.getLeft(), camera.getBottom(), camera.getX(), camera.getY(), getWidth() - OFFSET * 2f, getHeight() - OFFSET * 2f);
        }
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
    }

    public float getWidth() {
        return map.getProperties().get("width", Integer.class) * getTileWidth();
    }

    public float getHeight() {
        return map.getProperties().get("height", Integer.class) * getTileHeight();
    }

    public float getTileWidth() {
        return map.getProperties().get("tilewidth", Integer.class);
    }

    public float getTileHeight() {
        return map.getProperties().get("tileheight", Integer.class);
    }
}
