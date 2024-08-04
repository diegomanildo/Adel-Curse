package game.rooms;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import game.utilities.Camera2D;
import utilities.Actor;
import utilities.FilePaths;

public abstract class Room extends Actor {
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer renderer;
    private final Camera2D camera;

    public static final float OFFSET = 17f;

    public Room(String tmxFile, Camera2D camera) {
        this.camera = camera;
        this.map = new TmxMapLoader().load(FilePaths.ROOMS + tmxFile);
        this.renderer = new OrthogonalTiledMapRenderer(map);

        setWidth(map.getProperties().get("width", Integer.class) * getTileWidth());
        setHeight(map.getProperties().get("height", Integer.class) * getTileHeight());

        this.camera.viewportWidth = getWidth();
        this.camera.viewportHeight = getHeight();
        this.camera.update();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        renderer.render();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        camera.update();
        renderer.setView(camera);
    }

    private float getTileWidth() {
        return map.getProperties().get("tilewidth", Integer.class);
    }

    private float getTileHeight() {
        return map.getProperties().get("tileheight", Integer.class);
    }
}
