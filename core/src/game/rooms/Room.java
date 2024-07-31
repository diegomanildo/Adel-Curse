package game.rooms;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import utilities.Actor;
import utilities.FilePaths;
import utilities.Render;

public abstract class Room extends Actor {
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer renderer;
    private final OrthographicCamera camera;

    public static final float OFFSET = 17f;

    public Room(String tmxFile) {
        this.camera = Render.camera;
        this.map = new TmxMapLoader().load(FilePaths.ROOMS + tmxFile);
        this.renderer = new OrthogonalTiledMapRenderer(map);

        setWidth(map.getProperties().get("width", Integer.class) * getTileWidth());
        setHeight(map.getProperties().get("height", Integer.class) * getTileHeight());

        this.camera.viewportWidth = getWidth() - OFFSET * 2f;
        this.camera.viewportHeight = getHeight() - OFFSET * 2f;
        this.camera.update();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        camera.update();
        renderer.setView(camera);
        renderer.render();
    }

    private float getTileWidth() {
        return map.getProperties().get("tilewidth", Integer.class);
    }

    private float getTileHeight() {
        return map.getProperties().get("tileheight", Integer.class);
    }

    public void set(float x, float y) {
        setPosition(x, y);
        renderer.getBatch().begin();
        renderer.getBatch().getTransformMatrix().setToTranslation(x, y, 0f);
        renderer.getBatch().end();
    }
}
